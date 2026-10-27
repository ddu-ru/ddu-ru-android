package com.gildongmu.dduru.data.remote.websocket

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomEvent
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomListEvent
import com.gildongmu.dduru.data.remote.dto.websocket.SendMessageRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

@Singleton
class StompChatClient @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val okHttpClient: OkHttpClient,
    private val baseUrl: String
) {
    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
    private val wsUrl = baseUrl
        .replace("https://", "wss://")
        .replace("http://", "ws://")
        .trimEnd('/') + "/ws/chat"

    @Volatile
    private var roomWebSocket: WebSocket? = null

    fun connectAndSubscribeToRoom(roomId: Long): Flow<ChatRoomEvent> = subscribe(
        destination = "/topic/chat/rooms/$roomId",
        subscriptionId = "room-$roomId",
        isRoomSocket = true
    ) { body -> json.decodeFromString<ChatRoomEvent>(body) }

    fun subscribeToRoomList(): Flow<ChatRoomListEvent> = subscribe(
        destination = "/user/queue/chat-room-list",
        subscriptionId = "room-list",
        isRoomSocket = false
    ) { body -> json.decodeFromString<ChatRoomListEvent>(body) }

    fun sendMessage(roomId: Long, messageType: String, content: String): Boolean {
        val socket = roomWebSocket ?: return false
        val body = json.encodeToString(SendMessageRequest(messageType = messageType, content = content))
        val frame = buildString {
            append("SEND\n")
            append("destination:/app/chat/rooms/$roomId/messages\n")
            append("content-type:application/json\n")
            append("content-length:${body.toByteArray().size}\n")
            append("\n")
            append(body)
            append(NULL_CHAR)
        }
        return socket.send(frame)
    }

    private fun <T> subscribe(
        destination: String,
        subscriptionId: String,
        isRoomSocket: Boolean,
        decode: (String) -> T
    ): Flow<T> = callbackFlow {
        val token = tokenLocalDataSource.accessToken.first()
        if (token == null) {
            close(IllegalStateException("NO_ACCESS_TOKEN"))
            return@callbackFlow
        }

        val socket = okHttpClient.newWebSocket(
            Request.Builder().url(wsUrl).build(),
            object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    webSocket.send("CONNECT\naccept-version:1.1,1.2\nAuthorization:Bearer $token\n\n$NULL_CHAR")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    when (text.substringBefore('\n').trim()) {
                        "CONNECTED" -> {
                            if (isRoomSocket) roomWebSocket = webSocket
                            webSocket.send("SUBSCRIBE\nid:$subscriptionId\ndestination:$destination\n\n$NULL_CHAR")
                        }
                        "MESSAGE" -> extractBody(text)?.let { body ->
                            runCatching { decode(body) }.getOrNull()?.let { trySend(it) }
                        }
                        "ERROR" -> close(IllegalStateException("STOMP_ERROR"))
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    clearRoomSocket(webSocket)
                    close()
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    clearRoomSocket(webSocket)
                    close(t)
                }
            }
        )

        awaitClose {
            clearRoomSocket(socket)
            socket.send("UNSUBSCRIBE\nid:$subscriptionId\n\n$NULL_CHAR")
            socket.send("DISCONNECT\n\n$NULL_CHAR")
            socket.close(NORMAL_CLOSURE_CODE, null)
        }
    }

    private fun clearRoomSocket(webSocket: WebSocket) {
        if (roomWebSocket === webSocket) roomWebSocket = null
    }

    private fun extractBody(frame: String): String? {
        val bodyStart = frame.indexOf("\n\n").takeIf { it != -1 } ?: return null
        return frame.substring(bodyStart + 2).trimEnd('\u0000').ifBlank { null }
    }

    private companion object {
        const val NULL_CHAR = "\u0000"
        const val NORMAL_CLOSURE_CODE = 1000
    }
}

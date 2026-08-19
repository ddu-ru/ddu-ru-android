package com.gildongmu.dduru.data.remote.websocket

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomEvent
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomListEvent
import com.gildongmu.dduru.data.remote.dto.websocket.SendMessageRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CompletableDeferred
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

    private var roomWebSocket: WebSocket? = null

    fun connectAndSubscribeToRoom(roomId: Long): Flow<ChatRoomEvent> = callbackFlow {
        val token = tokenLocalDataSource.accessToken.first() ?: run {
            close()
            return@callbackFlow
        }
        val connected = CompletableDeferred<Unit>()
        val request = Request.Builder().url(wsUrl).build()
        roomWebSocket = okHttpClient.newWebSocket(
            request,
            object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    webSocket.send("CONNECT\naccept-version:1.1,1.2\nAuthorization:Bearer $token\n\n\u0000")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    when {
                        text.startsWith("CONNECTED") -> {
                            webSocket.send(
                                "SUBSCRIBE\nid:room-$roomId\ndestination:/topic/chat/rooms/$roomId\n\n\u0000"
                            )
                            connected.complete(Unit)
                        }
                        text.startsWith("MESSAGE") -> {
                            val body = extractBody(text) ?: return
                            runCatching { json.decodeFromString<ChatRoomEvent>(body) }
                                .getOrNull()?.let { trySend(it) }
                        }
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    roomWebSocket = null
                    close()
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    roomWebSocket = null
                    if (!connected.isCompleted) connected.completeExceptionally(t)
                    close(t)
                }
            }
        )
        connected.await()
        awaitClose {
            roomWebSocket?.close(1000, null)
            roomWebSocket = null
        }
    }

    suspend fun sendMessage(roomId: Long, content: String) {
        val body = json.encodeToString(SendMessageRequest(messageType = "TEXT", content = content))
        val frame = buildString {
            append("SEND\n")
            append("destination:/app/chat/rooms/$roomId/messages\n")
            append("content-type:application/json\n")
            append("content-length:${body.toByteArray().size}\n")
            append("\n")
            append(body)
            append("\u0000")
        }
        val result = roomWebSocket?.send(frame)
    }

    suspend fun ensureConnectedAndSend(roomId: Long, content: String) {
        if (roomWebSocket == null) {
            val token = tokenLocalDataSource.accessToken.first() ?: return
            val connected = CompletableDeferred<Unit>()
            val request = Request.Builder().url(wsUrl).build()
            roomWebSocket = okHttpClient.newWebSocket(
                request,
                object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        webSocket.send("CONNECT\naccept-version:1.1,1.2\nAuthorization:Bearer $token\n\n\u0000")
                    }
                    override fun onMessage(webSocket: WebSocket, text: String) {
                        if (text.startsWith("CONNECTED")) {
                            webSocket.send(
                                "SUBSCRIBE\nid:room-$roomId\ndestination:/topic/chat/rooms/$roomId\n\n\u0000"
                            )
                            connected.complete(Unit)
                        }
                    }
                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) { roomWebSocket = null }
                    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                        if (!connected.isCompleted) connected.completeExceptionally(t)
                        roomWebSocket = null
                    }
                }
            )
            connected.await()
        }
        sendMessage(roomId, content)
    }

    suspend fun disconnectRoom() {
        roomWebSocket?.send("DISCONNECT\n\n\u0000")
        roomWebSocket?.close(1000, null)
        roomWebSocket = null
    }

    fun subscribeToRoomList(): Flow<ChatRoomListEvent> = callbackFlow {
        val token = tokenLocalDataSource.accessToken.first() ?: run { close(); return@callbackFlow }
        val request = Request.Builder().url(wsUrl).build()
        val ws = okHttpClient.newWebSocket(
            request,
            object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    webSocket.send("CONNECT\naccept-version:1.1,1.2\nAuthorization:Bearer $token\n\n\u0000")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    when {
                        text.startsWith("CONNECTED") ->
                            webSocket.send("SUBSCRIBE\nid:room-list\ndestination:/user/queue/chat-room-list\n\n\u0000")
                        text.startsWith("MESSAGE") -> {
                            val body = extractBody(text) ?: return
                            runCatching { json.decodeFromString<ChatRoomListEvent>(body) }
                                .getOrNull()?.let { trySend(it) }
                        }
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) { close() }
                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) { close(t) }
            }
        )
        awaitClose { ws.close(1000, null) }
    }

    private fun extractBody(frame: String): String? {
        val bodyStart = frame.indexOf("\n\n").takeIf { it != -1 } ?: return null
        return frame.substring(bodyStart + 2).trimEnd('\u0000').ifBlank { null }
    }
}

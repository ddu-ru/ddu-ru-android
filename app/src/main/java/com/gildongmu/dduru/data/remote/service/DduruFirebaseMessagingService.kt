package com.gildongmu.dduru.data.remote.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.notification.DduruPushNotificationContract
import com.gildongmu.dduru.domain.repository.FcmTokenRepository
import com.gildongmu.dduru.presentation.feature.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DduruFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var fcmTokenRepository: FcmTokenRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch {
            fcmTokenRepository.registerToken(token)
                .onFailure { throwable ->
                    Timber.e(throwable, "Failed to register refreshed FCM token")
                }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title
            ?: message.data[KEY_TITLE]
            ?: return
        val body = message.notification?.body
            ?: message.data[KEY_BODY]
            ?: ""

        showNotification(
            title = title,
            body = body,
            resourceType = message.data[DduruPushNotificationContract.KEY_RESOURCE_TYPE],
            resourceId = message.data[DduruPushNotificationContract.KEY_RESOURCE_ID]
        )
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    private fun showNotification(
        title: String,
        body: String,
        resourceType: String?,
        resourceId: String?
    ) {
        if (!canPostNotification()) return

        ensureNotificationChannel()

        val notification = NotificationCompat.Builder(this, DduruPushNotificationContract.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alram)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(createContentIntent(resourceType, resourceId))
            .build()

        NotificationManagerCompat.from(this).notify(
            DduruPushNotificationContract.NOTIFICATION_ID,
            notification
        )
    }

    private fun createContentIntent(
        resourceType: String?,
        resourceId: String?
    ): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(DduruPushNotificationContract.KEY_RESOURCE_TYPE, resourceType)
            putExtra(DduruPushNotificationContract.KEY_RESOURCE_ID, resourceId)
        }

        return PendingIntent.getActivity(
            this,
            DduruPushNotificationContract.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun ensureNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            DduruPushNotificationContract.CHANNEL_ID,
            DduruPushNotificationContract.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun canPostNotification(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private companion object {
        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
    }
}

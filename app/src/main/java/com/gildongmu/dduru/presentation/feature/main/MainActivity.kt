package com.gildongmu.dduru.presentation.feature.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.repository.FcmTokenRepository
import com.gildongmu.dduru.presentation.navigation.RootNavRoute
import com.gildongmu.dduru.presentation.navigation.RootNavigation
import com.gildongmu.dduru.presentation.navigation.toPushNotificationRoute
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var fcmTokenRepository: FcmTokenRepository

    private var pendingNotificationRoute by mutableStateOf<RootNavRoute?>(null)

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Timber.d("Notification permission granted: %s", isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pendingNotificationRoute = intent.toPushNotificationRoute()
        requestNotificationPermissionIfNeeded()
        registerFcmToken()

        setContent {
            DduruTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNavigation(
                        pendingNotificationRoute = pendingNotificationRoute,
                        onNotificationRouteConsumed = {
                            pendingNotificationRoute = null
                        }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        pendingNotificationRoute = intent.toPushNotificationRoute()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val isGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun registerFcmToken() {
        lifecycleScope.launch {
            fcmTokenRepository.registerCurrentToken()
                .onFailure { throwable ->
                    Timber.e(throwable, "Failed to register FCM token")
                }
        }
    }
}

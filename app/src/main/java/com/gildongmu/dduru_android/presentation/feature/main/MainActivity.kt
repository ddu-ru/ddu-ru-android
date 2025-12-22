package com.gildongmu.dduru_android.presentation.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.data.local.TokenLocalDataSource
import com.gildongmu.dduru_android.presentation.feature.login.WelcomeScreen
import com.gildongmu.dduru_android.presentation.navigation.LoginRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tokenLocalDataSource: TokenLocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DduruTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // 초기 상태: Splash
                    var currentScreen by remember { mutableStateOf(Screen.Splash) }

                    val modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)

                    //자동 로그인 유지
                    LaunchedEffect(Unit) {
                        val accessToken = tokenLocalDataSource.accessToken.first()

                        if (!accessToken.isNullOrBlank()) {
                            // 토큰 있음 -> (기존 회원 취급) -> 바로 홈으로!
                            Toast.makeText(applicationContext, "자동 로그인 성공!", Toast.LENGTH_SHORT).show()
                            currentScreen = Screen.Home
                        } else {
                            // 토큰 없음 -> 로그인 화면으로
                            currentScreen = Screen.Login
                        }
                    }

                    // 화면 전환 로직
                    when (currentScreen) {
                        Screen.Splash -> {
                            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                                Text("DDURU 로딩 중...")
                            }
                        }

                        Screen.Login -> {
                            LoginRoute(
                                onLoginSuccess = { isNewUser ->
                                    if (isNewUser) {
                                        // 신규 회원 -> Welcome
                                        currentScreen = Screen.Welcome
                                    } else {
                                        // 기존 회원 ->Home
                                        currentScreen = Screen.Home
                                    }
                                },
                                onGuestClick = {
                                    currentScreen = Screen.Home
                                }
                            )
                        }

                        Screen.Welcome -> {
                            WelcomeScreen(
                                modifier = modifier,
                                onNextClick = {
                                    // Welcome에서 '네 좋아요!' 클릭 시 -> Onboarding
                                    currentScreen = Screen.Onboarding
                                }
                            )
                        }

                        Screen.Onboarding -> {
                            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                                androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("여기가 온보딩(생년월일 입력) 화면입니다!")
                                    Button(onClick = {
                                        currentScreen = Screen.Home
                                    }) {
                                        Text("입력 완료하고 홈으로 가기")
                                    }
                                }
                            }
                        }

                        Screen.Home -> {
                            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                                Text("여기가 메인 홈 화면입니다! 🏠")
                            }
                        }
                    }
                }
            }
        }
    }
}

// 화면 순서 정의
enum class Screen {
    Splash,
    Login,
    Welcome,
    Onboarding,
    Home
}

package com.gildongmu.dduru_android.presentation.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.navigation.LoginRoute
import com.gildongmu.dduru_android.presentation.feature.login.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DduruTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // 현재 보여줄 화면 상태
                    var currentScreen by remember { mutableStateOf(Screen.Login) }

                    // 신규 유저인지 여부를 저장하는 변수 (기본값 false)
                    var isNewUserCheck by remember { mutableStateOf(false) }

                    // Scaffold의 innerPadding을 적용하여 화면이 잘리지 않게 처리
                    val modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)

                    when (currentScreen) {
                        Screen.Login -> {
                            LoginRoute(
                                onLoginSuccess = { isNewUser ->
                                    // 1. 로그인 성공 시, 신규 유저 여부를 저장합니다.
                                    isNewUserCheck = isNewUser
                                    // 2. 기존/신규 상관없이 무조건 'Welcome' 화면으로 이동합니다.
                                    currentScreen = Screen.Welcome
                                },
                                onGuestClick = {
                                    Toast.makeText(applicationContext, "게스트 입장(홈으로)", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                        Screen.Welcome -> {
                            WelcomeScreen(
                                modifier = modifier,
                                onNextClick = {
                                    if (isNewUserCheck) {
                                        // 신규 유저 -> 생년월일 입력 화면으로
                                        Toast.makeText(applicationContext, "신규 회원입니다! 생년월일 화면으로 이동", Toast.LENGTH_SHORT).show()
                                        // currentScreen = Screen.Birthday (나중에 추가 시 주석 해제)
                                    } else {
                                        // 기존 유저 -> 메인 홈 화면으로
                                        Toast.makeText(applicationContext, "기존 회원입니다! 홈으로 이동", Toast.LENGTH_SHORT).show()
                                        // currentScreen = Screen.Home (나중에 추가 시 주석 해제)
                                    }
                                }

                            )
                        }
                    }
                }
            }
        }
    }
}

// 화면 순서 정의
enum class Screen {
    Login,
    Welcome,
    // Birthday,
    // Home
}

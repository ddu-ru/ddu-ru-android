package com.gildongmu.dduru.presentation.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.login.LoginViewModel
import com.gildongmu.dduru.presentation.feature.login.WelcomeScreen
import com.gildongmu.dduru.presentation.feature.register.survey.SurveyMainScreen
import com.gildongmu.dduru.presentation.navigation.LoginRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DduruTheme {
                val startScreen by viewModel.startScreen.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var currentScreen by rememberSaveable { mutableStateOf(Screen.Splash) }

                    val modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)

                    LaunchedEffect(startScreen) {
                        if (currentScreen == Screen.Splash && startScreen != Screen.Splash) {
                            currentScreen = startScreen
                            if (startScreen == Screen.Home) {
                                // TODO: 개발용 자동로그인 확인 토스트, 배포 전 제거 필요
                                Toast.makeText(
                                    applicationContext,
                                    "자동 로그인 성공",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    when (currentScreen) {
                        Screen.Splash -> {
                            Box(
                                modifier = modifier,
                                contentAlignment = Alignment.Center
                            ) {
                                Text("DDURU 로딩 중...")
                            }
                        }

                        Screen.Login -> {
                            LoginRoute(
                                onLoginSuccess = { isNewUser ->
                                    currentScreen = if (isNewUser) {
                                        Screen.Welcome
                                    } else {
                                        Screen.Home
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
                                    currentScreen = Screen.Onboarding
                                }
                            )
                        }

                        Screen.Onboarding -> {
                            SurveyMainScreen(
                                modifier = modifier,
                                onSurveyComplete = {
                                    currentScreen = Screen.Home
                                },
                                onBackPressed = {
                                    currentScreen = Screen.Home
                                }
                            )
                        }

                        Screen.Home -> {
                            Box(
                                modifier = modifier,
                                contentAlignment = Alignment.Center
                            ) {
                                Text("여기가 메인 홈 화면입니다! 🏠")
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Screen {
    Splash,
    Login,
    Welcome,
    Onboarding,
    Home
}

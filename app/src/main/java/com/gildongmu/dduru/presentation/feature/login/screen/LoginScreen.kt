package com.gildongmu.dduru.presentation.feature.login.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.LoginButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.login.component.PolicyBottomSheet
import com.gildongmu.dduru.presentation.feature.login.component.PolicyButton
import com.gildongmu.dduru.presentation.feature.login.effect.LoginSideEffect
import com.gildongmu.dduru.presentation.feature.login.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    onLoginSuccess: (Boolean) -> Unit,
    onGuestClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPolicySheetVisible by viewModel.isPolicySheetVisible.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { event ->
            when (event) {
                is LoginSideEffect.LoginSuccess -> {
                    onLoginSuccess(event.isNewUser)
                }
                is LoginSideEffect.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LoginScreen(
        isLoading = uiState.isLoading,
        onKakaoClick = { viewModel.startKakaoLogin() },
        onGoogleClick = { viewModel.startGoogleLogin(context) },
        onGuestClick = onGuestClick,
        onPolicyClick = viewModel::onPolicyClick,
        isPolicySheetVisible = isPolicySheetVisible,
        onDismissPolicySheet = viewModel::onDismissPolicySheet,
    )
}

@Composable
private fun LoginScreen(
    isLoading: Boolean,
    onKakaoClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onGuestClick: () -> Unit,
    onPolicyClick: () -> Unit,
    isPolicySheetVisible: Boolean,
    onDismissPolicySheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(152f))

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_welcome_shadow),
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .scale(scaleX = 1f, scaleY = 0.2f)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    DduruTheme.colors.black.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.img_penguin_ver1),
                    contentDescription = "뚜루 캐릭터",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }

            Spacer(modifier = Modifier.weight(103f))

            Column(
                modifier = Modifier.width(343.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginButton(
                    iconResId = R.drawable.img_kakao_icon,
                    text = "카카오로 시작하기",
                    containerColor = DduruTheme.colors.kakaoYellow,
                    contentColor = DduruTheme.colors.gray900,
                    onClick = onKakaoClick
                )

                LoginButton(
                    iconResId = R.drawable.img_google_icon,
                    text = "구글로 시작하기",
                    containerColor = DduruTheme.colors.gray0,
                    contentColor = DduruTheme.colors.gray900,
                    onClick = onGoogleClick,
                    hasBorder = true
                )
            }

            Spacer(modifier = Modifier.weight(25f))

            Text(
                text = "가입 없이 둘러볼래요",
                modifier = Modifier.clickable { onGuestClick() },
                style = DduruTheme.typography.Body3,
                color = DduruTheme.colors.gray500,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(46.dp))

            PolicyButton(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 60.dp),
                onClick = onPolicyClick
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = DduruTheme.colors.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        PolicyBottomSheet(
            visible = isPolicySheetVisible,
            onDismiss = onDismissPolicySheet
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    DduruTheme {
        LoginScreen(
            isLoading = false,
            onKakaoClick = { },
            onGoogleClick = { },
            onGuestClick = { },
            onPolicyClick = { },
            isPolicySheetVisible = false,
            onDismissPolicySheet = { }
        )
    }
}

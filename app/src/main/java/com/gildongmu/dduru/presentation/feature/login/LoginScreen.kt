package com.gildongmu.dduru.presentation.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.LoginButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.R

@Composable
fun LoginScreen(
    isLoading: Boolean,
    onKakaoClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onGuestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.Gray50),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(170.dp))

            // 1. 캐릭터
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .width(244.dp)
                    .height(267.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_welcome_shadow),
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(95.dp)
                        .scale(scaleX = 1f, scaleY = 0.2f)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    DduruTheme.colors.Black.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.img_penguin_1),
                    contentDescription = "뚜루 캐릭터",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .matchParentSize()
                )
            }

            Spacer(modifier = Modifier.height(95.dp))

            // 2. 버튼 영역
            Column(
                modifier = Modifier.width(343.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 카카오 로그인
                LoginButton(
                    iconResId = R.drawable.img_kakao_icon,
                    text = "카카오로 시작하기",
                    containerColor = DduruTheme.colors.KakaoYellow,
                    contentColor = DduruTheme.colors.Gray900,
                    onClick = onKakaoClick
                )

                // 구글 로그인
                LoginButton(
                    iconResId = R.drawable.img_google_icon,
                    text = "구글로 시작하기",
                    containerColor = DduruTheme.colors.Gray0,
                    contentColor = DduruTheme.colors.Gray900,
                    onClick = onGoogleClick,
                    hasBorder = true
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // 3. 가입 없이 둘러볼래요
            Text(
                text = "가입 없이 둘러볼래요",
                modifier = Modifier.clickable { onGuestClick() },
                style = DduruTheme.typography.LabelMedium,
                color = DduruTheme.colors.Gray500,
                textAlign = TextAlign.Center
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = DduruTheme.colors.Primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=375dp,height=812dp")
@Composable
private fun LoginScreenPreview() {
    DduruTheme {
        LoginScreen(
            isLoading = false,
            onKakaoClick = { },
            onGoogleClick = { },
            onGuestClick = { }
        )
    }
}

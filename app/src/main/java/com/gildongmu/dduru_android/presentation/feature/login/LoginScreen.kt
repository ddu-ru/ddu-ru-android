package com.gildongmu.dduru_android.presentation.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.component.LoginButton
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

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
            .background(DduruTheme.colors.Background),
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

            //1. 캐릭터
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_dduri_fly_2),
                    contentDescription = "뚜루 캐릭터",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(183.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.bg_welcome_shadow),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(46.dp)
                        .height(8.dp)
                        .alpha(0.5f)
                )
            }

            Spacer(modifier = Modifier.height(95.dp))

            //2. 버튼 영역
            Column(
                modifier = Modifier.width(343.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 카카오 로그인
                LoginButton(
                    iconResId = R.drawable.img_kakao_icon,
                    text = "카카오로 시작하기",
                    containerColor = DduruTheme.colors.KakaoBg,
                    contentColor = DduruTheme.colors.KakaoText,
                    onClick = onKakaoClick
                )

                // 구글 로그인
                LoginButton(
                    iconResId = R.drawable.img_google_icon,
                    text = "구글로 시작하기",
                    containerColor = DduruTheme.colors.GoogleBg,
                    contentColor = DduruTheme.colors.GoogleText,
                    onClick = onGoogleClick,
                    hasBorder = false
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            //3. 가입 없이 둘러볼래요
            Text(
                text = "가입 없이 둘러볼래요",
                modifier = Modifier.clickable { onGuestClick() },
                style = DduruTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = DduruTheme.colors.TextGuest
                ),
                textAlign = TextAlign.Center
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = DduruTheme.colors.MainColor,
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

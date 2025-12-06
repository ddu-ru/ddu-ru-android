package com.gildongmu.dduru_android.presentation.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.*

@Composable
fun LoginScreen(
    isLoading: Boolean,
    onKakaoClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    // Figma Shadow Colors
    val shadowColor = Color(0x14000000)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DduruTheme.colors.Background), // Gray50
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // --- 1. 캐릭터 영역 ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 캐릭터 이미지
                Image(
                    painter = painterResource(id = R.drawable.img_dduri_flying),
                    contentDescription = "뚜루 캐릭터",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(183.dp)
                )

                // 캐릭터 그림자 (Ellipse 14)
                Image(
                    painter = painterResource(id = R.drawable.bg_welcome_shadow),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(46.dp)
                        .height(8.dp)
                        .alpha(0.5f)
                        .blur(radius = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // --- 2. 버튼 영역 (요청하신 Figma 스펙 정확히 적용) ---
            Column(
                modifier = Modifier
                    .width(343.dp)
                    .height(116.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start
            ) {
                // [카카오 로그인 버튼]
                Image(
                    painter = painterResource(id = R.drawable.img_kakao_login),
                    contentDescription = "카카오로 시작하기",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(343.dp)
                        .height(56.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                            spotColor = shadowColor,
                            ambientColor = shadowColor
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onKakaoClick() }
                )

                // [구글 로그인 버튼]
                Image(
                    painter = painterResource(id = R.drawable.img_google_login),
                    contentDescription = "구글로 시작하기",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(343.dp)
                        .height(56.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                            spotColor = shadowColor,
                            ambientColor = shadowColor
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onGoogleClick() }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // --- 3. 가입 없이 둘러볼래요 ---
            Text(
                text = "가입 없이 둘러볼래요",
                modifier = Modifier.clickable { onGuestClick() },
                style = DduruTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Gray500
                ),
                textAlign = TextAlign.Center
            )
        }

        // 로딩 바
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
fun LoginScreenPreview() {
    DduruTheme {
        LoginScreen(
            isLoading = false,
            onKakaoClick = { },
            onGoogleClick = { },
            onGuestClick = { }
        )
    }
}

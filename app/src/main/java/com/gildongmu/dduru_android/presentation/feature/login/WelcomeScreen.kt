package com.gildongmu.dduru_android.presentation.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.Gray50)
            .padding(top = 100.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. 말풍선 영역
        Box(
            modifier = Modifier
                .width(340.dp)
                .wrapContentHeight()
        ) {
            //그림자
            Image(
                painter = painterResource(id = R.drawable.img_message),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize() // 부모 Box의 크기와 똑같이 맞춤
                    .offset(y = 9.dp) // 그림자 위치 (숫자가 클수록 아래로 내려옴)
                    .blur(radius = 17.dp) // 번짐 정도 (숫자가 클수록 부드럽게 퍼짐)
                    .alpha(0.3f), // 투명도 (0.1f = 연함, 1.0f = 완전 진함)
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black)
            )

            // Layer 2: 원본 이미지
            Image(
                painter = painterResource(id = R.drawable.img_message),
                contentDescription = "뚜르에 오신 것을 환영해요! ...",
                modifier = Modifier
                    .fillMaxWidth() // Box 너비(340dp)를 꽉 채움
                    .wrapContentHeight(), // 이미지 비율에 맞춰 높이 자동 설정 (핵심!)
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. 캐릭터 이미지
        Image(
            painter = painterResource(id = R.drawable.img_dduri_2),
            contentDescription = "뚜비 캐릭터",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(192.dp)
        )

        // 3. 캐릭터 그림자
        Image(
            painter = painterResource(id = R.drawable.bg_welcome_shadow),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(70.dp)
                .height(8.dp)
                .alpha(0.5f)
                .padding(1.dp)
        )

        Spacer(modifier = Modifier.height(83.dp))

        // 4. 버튼
        DduruButton(
            text = "네, 좋아요!",
            onClick = onNextClick,
            modifier = Modifier.width(343.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    DduruTheme {
        WelcomeScreen(onNextClick = {})
    }
}

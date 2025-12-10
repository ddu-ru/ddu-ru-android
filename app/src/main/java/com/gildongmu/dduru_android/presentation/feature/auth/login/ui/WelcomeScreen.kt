package com.gildongmu.dduru_android.presentation.feature.auth.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton // 방금 만든 버튼 컴포넌트 import
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.Background)
            .padding(top = 100.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. 말풍선 영역
        Image(
            painter = painterResource(id = R.drawable.img_message),
            contentDescription = "뚜르에 오신 것을 환영해요! 저는 뚜르의 마스코트 뚜비에요. 시작하기 전에 간단하게 서로 알아가 볼까요?",
            modifier = Modifier
                .width(340.dp)
                .wrapContentHeight(),
            contentScale = ContentScale.Fit
        )

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

        Spacer(modifier = Modifier.height(80.dp))

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
        WelcomeScreen(onNextClick = {}, onGuestClick = {})
    }
}

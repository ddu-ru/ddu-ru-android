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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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

        val bubbleShape = RoundedCornerShape(24.dp)
        // 1. 말풍선 영역
        Box(
            modifier = Modifier
                .width(340.dp)
                .wrapContentHeight()
        ) {
            // 그림자
            Image(
                painter = painterResource(id = R.drawable.img_message),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .offset(y = 9.dp)
                    .blur(radius = 17.dp)
                    .alpha(0.3f),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black)
            )
            // 원본 이미지
            Image(
                painter = painterResource(id = R.drawable.img_message),
                contentDescription = "뚜르에 오신 것을 환영해요! ...",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // 2. 캐릭터 이미지
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.width(180.dp)
        ) {
            // 그림자
            Box(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .width(100.dp)
                    .height(20.dp)
                    .scale(scaleX = 1f, scaleY = 0.3f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                DduruTheme.colors.Black.copy(alpha = 0.2f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Image(
                painter = painterResource(id = R.drawable.img_penguin_2),
                contentDescription = "뚜비 캐릭터",
                modifier = Modifier
                    .width(180.dp)
                    .height(218.dp),
                contentScale = ContentScale.FillBounds
            )
        }
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

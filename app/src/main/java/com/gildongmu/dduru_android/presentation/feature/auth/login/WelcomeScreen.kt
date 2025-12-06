package com.gildongmu.dduru_android.presentation.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme


val TriangleShape = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width / 2f, size.height)
    close()
}

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.Background)
            .padding(top = 100.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. 말풍선 영역
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // 1-1. 말풍선 몸통
            Box(
                modifier = Modifier
                    .width(325.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = Color.Black.copy(alpha = 0.4f),
                        ambientColor = Color.Black.copy(alpha = 0.4f)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("뚜르에 오신 것을 환영해요!\n저는 뚜르의 마스코트 ")
                        withStyle(style = SpanStyle(
                            color = DduruTheme.colors.MainColor,
                            fontWeight = FontWeight.Bold)
                        ) {
                            append("뚜비")
                        }
                        append("에요.\n\n시작하기 전에 간단하게 서로 알아가 볼까요?")
                    },
                    style = DduruTheme.typography.titleSmall,
                    color = DduruTheme.colors.TextMain,
                    textAlign = TextAlign.Center
                )
            }

            // 1-2. 말풍선 꼬리
            Image(
                painter = painterResource(id = R.drawable.ic_welcome_bubble_tail),
                contentDescription = null,
                modifier = Modifier
                    .width(20.dp)
                    .height(16.dp)
                    .offset(y = (-1).dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = TriangleShape,
                        spotColor = Color.Black.copy(alpha = 0.4f),
                        ambientColor = Color.Black.copy(alpha = 0.4f)
                    ),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. 캐릭터 이미지
        Image(
            painter = painterResource(id = R.drawable.img_welcome_dduri),
            contentDescription = "뚜비 캐릭터",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(192.dp)
        )

        //  3.  캐릭터 그림자
        Image(
            painter = painterResource(id = R.drawable.bg_welcome_shadow),
            contentDescription = "character shadow",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(70.dp)
                .height(8.dp)
                .alpha(0.5f)
                .blur(radius = 2.dp) // Android 12 이상에서만 동작
                .padding(1.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        // 4. 버튼
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .width(343.dp)
                .height(48.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(12.dp),
                    spotColor = DduruTheme.colors.Shadow,
                    ambientColor = DduruTheme.colors.Shadow
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.ButtonBorder,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = DduruTheme.colors.MainColor
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "네, 좋아요!",
                style = DduruTheme.typography.buttonText,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    DduruTheme {
        WelcomeScreen(onNextClick = {}, onGuestClick = {})
    }
}

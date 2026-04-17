package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun AiMatchingFinishedCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = DduruTheme.colors.gray0,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 119.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_home_ai_matching),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "오늘의 추천이 끝났어요!",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray900,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "하루 3번의 무료 추천을 모두 사용하셨습니다.\n내일 또 새로운 동행을 만나보세요!👋",
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.gray500,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF3F3F3F)
@Composable
private fun AiMatchingFinishedCardPreview() {
    DduruTheme {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            AiMatchingFinishedCard(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

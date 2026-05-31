package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeHashtag(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    backgroundColor: Color,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable (() -> Unit))? = null,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 99.dp))
            .background(color = backgroundColor)
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#$text",
                color = textColor,
                style = textStyle
            )

            trailingIcon?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }
    }
}

@Preview(name = "OnboardingHashtag - 설문조사 완료")
@Composable
private fun OnboardingHashtagPreview() {
    DduruTheme {
        HomeHashtag(
            text = "세심함",
            textColor = DduruTheme.colors.gray700,
            textStyle = DduruTheme.typography.Caption2,
            backgroundColor = DduruTheme.colors.gray300,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(name = "HomeHashtag - 실시간인기여행지")
@Composable
private fun HomeHashtagRealtimePreview() {
    DduruTheme {
        HomeHashtag(
            text = "힐링",
            textColor = DduruTheme.colors.gray700,
            textStyle = DduruTheme.typography.Micro2,
            backgroundColor = DduruTheme.colors.gray100,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(name = "HomeHashtag - 슈퍼호스트")
@Composable
private fun HomeHashtagDefaultPreview() {
    DduruTheme {
        HomeHashtag(
            text = "일출",
            textColor = DduruTheme.colors.gray500,
            textStyle = DduruTheme.typography.Caption1,
            backgroundColor = DduruTheme.colors.gray100,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(name = "HomeHashtag - AI추천여행지")
@Composable
private fun HomeHashtagAIPreview() {
    DduruTheme {
        HomeHashtag(
            text = "카페투어",
            textColor = DduruTheme.colors.gray300,
            textStyle = DduruTheme.typography.Caption2,
            backgroundColor = DduruTheme.colors.gray300.copy(alpha = 0.3f),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

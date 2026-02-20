package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeHashtag(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape=RoundedCornerShape(size=99.dp))
            .background(color = DduruTheme.colors.gray300.copy(alpha = 0.3f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "#$text",
            color = DduruTheme.colors.gray300,
            style = DduruTheme.typography.Caption2
        )
    }
}

@Preview(name = "HomeHashtag - Default")
@Composable
private fun HomeHashtagDefaultPreview() {
    HomeHashtag(text = "사진")
}

@Preview(name = "HomeHashtag - LongText")
@Composable
private fun HomeHashtagLongTextPreview() {
    HomeHashtag(text = "카페투어")
}

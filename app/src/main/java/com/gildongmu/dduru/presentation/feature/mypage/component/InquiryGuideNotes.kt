package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun InquiryGuideNotes(
    items: List<String>,
    modifier: Modifier = Modifier,
    title: String = "문의 처리 안내"
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Micro3,
            color = DduruTheme.colors.gray600
        )

        items.forEach { item ->
            Row {
                Text(
                    text = "•",
                    style = DduruTheme.typography.Micro3,
                    color = DduruTheme.colors.gray600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(15.dp)
                )
                Text(
                    text = item,
                    style = DduruTheme.typography.Micro3,
                    color = DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InquiryGuideNotesPreview() {
    DduruTheme {
        InquiryGuideNotes(
            items = listOf("처리 결과는 알림으로 안내드립니다", "처리까지 1-3일 소요될 수 있습니다"),
            modifier = Modifier.padding(16.dp)
        )
    }
}

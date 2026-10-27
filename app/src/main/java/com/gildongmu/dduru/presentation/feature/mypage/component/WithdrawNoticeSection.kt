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
fun WithdrawNoticeSection(
    title: String,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray868
        )

        Column {
            items.forEach { item ->
                Row {
                    Text(
                        text = "•",
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray868,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(21.dp)
                    )
                    Text(
                        text = item,
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray868
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawNoticeSectionPreview() {
    DduruTheme {
        WithdrawNoticeSection(
            title = "탈퇴 시 주의사항",
            items = listOf(
                "진행 중인 여행에서 자동으로 나가게 됩니다.",
                "작성한 모집글과 후기가 모두 삭제됩니다."
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

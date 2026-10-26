package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryStatus
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryUiModel

@Composable
fun InquiryListCard(
    inquiries: List<InquiryUiModel>,
    onInquiryClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = DduruTheme.colors.gray200, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 17.dp, vertical = 9.dp)
    ) {
        inquiries.forEachIndexed { index, inquiry ->
            InquiryItemRow(
                inquiry = inquiry,
                showDivider = index != inquiries.lastIndex,
                onClick = { onInquiryClick(inquiry.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InquiryListCardPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            InquiryListCard(
                inquiries = listOf(
                    InquiryUiModel(
                        id = 1,
                        category = "오류·버그",
                        title = "채팅방에서 사진을 보내면 앱이 종료되는 현상이 있어요.",
                        date = "2026.08.21",
                        status = InquiryStatus.WAITING
                    ),
                    InquiryUiModel(
                        id = 2,
                        category = "매칭·여행방",
                        title = "여행글을 3개 작성했는데 더 이상 작성이 안 돼요.",
                        date = "2026.08.17",
                        status = InquiryStatus.ANSWERED
                    )
                ),
                onInquiryClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

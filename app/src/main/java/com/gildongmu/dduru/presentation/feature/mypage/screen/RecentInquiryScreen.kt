package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.mypage.component.InquiryEmptyCard
import com.gildongmu.dduru.presentation.feature.mypage.component.InquiryListCard
import com.gildongmu.dduru.presentation.feature.mypage.component.PillSegmentedControl
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryStatus
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryUiModel

private val CustomerCenterSegments = listOf("FAQ", "최근 문의")
private const val RECENT_INQUIRY_SEGMENT_INDEX = 1

@Composable
fun RecentInquiryScreen(
    inquiries: List<InquiryUiModel>,
    onBackClick: () -> Unit,
    onSegmentClick: (Int) -> Unit,
    onInquiryClick: (Long) -> Unit,
    onNewInquiryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "고객센터",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            PillSegmentedControl(
                tabs = CustomerCenterSegments,
                selectedIndex = RECENT_INQUIRY_SEGMENT_INDEX,
                onTabClick = onSegmentClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "최근 문의",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray958
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (inquiries.isEmpty()) {
                InquiryEmptyCard()
            } else {
                InquiryListCard(
                    inquiries = inquiries,
                    onInquiryClick = onInquiryClick
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "답변이 등록되면 알림으로 알려드려요.",
                style = if (inquiries.isEmpty()) {
                    DduruTheme.typography.Caption2.copy(lineHeight = 19.sp)
                } else {
                    DduruTheme.typography.Body4
                },
                color = if (inquiries.isEmpty()) DduruTheme.colors.gray600 else DduruTheme.colors.gray500,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            DduruButton(
                text = "새 문의하기",
                onClick = onNewInquiryClick,
                enabledColors = DduruButtonEnabledColors(
                    backgroundColor = DduruTheme.colors.gray958,
                    textColor = DduruTheme.colors.gray0,
                    borderColor = DduruTheme.colors.gray0.copy(alpha = 0.1f)
                ),
                textStyle = DduruTheme.typography.Title2,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

private val PreviewInquiries = listOf(
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
)

@Preview(showBackground = true, heightDp = 812, name = "최근 문의 목록")
@Composable
private fun RecentInquiryScreenPreview() {
    DduruTheme {
        RecentInquiryScreen(
            inquiries = PreviewInquiries,
            onBackClick = {},
            onSegmentClick = {},
            onInquiryClick = {},
            onNewInquiryClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 812, name = "최근 문의 비어 있음")
@Composable
private fun RecentInquiryScreenEmptyPreview() {
    DduruTheme {
        RecentInquiryScreen(
            inquiries = emptyList(),
            onBackClick = {},
            onSegmentClick = {},
            onInquiryClick = {},
            onNewInquiryClick = {}
        )
    }
}

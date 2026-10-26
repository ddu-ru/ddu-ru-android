package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryStatus
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryUiModel

@Composable
fun InquiryItemRow(
    inquiry: InquiryUiModel,
    showDivider: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val captionStyle = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = inquiry.category,
                        style = captionStyle,
                        color = DduruTheme.colors.gray600
                    )

                    StatusTagXs(
                        text = inquiry.status.label,
                        backgroundColor = when (inquiry.status) {
                            InquiryStatus.WAITING -> DduruTheme.colors.secondary100
                            InquiryStatus.ANSWERED -> DduruTheme.colors.gray900
                        },
                        textColor = when (inquiry.status) {
                            InquiryStatus.WAITING -> DduruTheme.colors.primary
                            InquiryStatus.ANSWERED -> DduruTheme.colors.gray0
                        }
                    )
                }

                Text(
                    text = inquiry.title,
                    style = DduruTheme.typography.Body3,
                    color = DduruTheme.colors.gray958,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = inquiry.date,
                    style = captionStyle,
                    color = DduruTheme.colors.gray600
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(16.dp)
            )
        }

        if (showDivider) {
            HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray200)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InquiryItemRowPreview() {
    DduruTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            InquiryItemRow(
                inquiry = InquiryUiModel(
                    id = 1,
                    category = "오류·버그",
                    title = "채팅방에서 사진을 보내면 앱이 종료되는 현상이 있어요.",
                    date = "2026.08.21",
                    status = InquiryStatus.WAITING
                ),
                showDivider = true,
                onClick = {}
            )
            InquiryItemRow(
                inquiry = InquiryUiModel(
                    id = 2,
                    category = "매칭·여행방",
                    title = "여행글을 3개 작성했는데 더 이상 작성이 안 돼요.",
                    date = "2026.08.17",
                    status = InquiryStatus.ANSWERED
                ),
                showDivider = false,
                onClick = {}
            )
        }
    }
}

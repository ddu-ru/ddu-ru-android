package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun FaqItemCard(
    question: String,
    answer: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .border(width = 1.dp, color = DduruTheme.colors.gray200, shape = RoundedCornerShape(16.dp))
            .noRippleClickable(onClick = onClick)
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray868,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_down),
                contentDescription = if (isExpanded) "답변 접기" else "답변 펼치기",
                tint = DduruTheme.colors.gray700,
                modifier = Modifier
                    .size(12.dp)
                    .rotate(if (isExpanded) 180f else 0f)
            )
        }

        if (isExpanded) {
            Text(
                text = answer,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray500,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FaqItemCardPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FaqItemCard(
                    question = "Q. 동행 신청은 어떻게 하나요?",
                    answer = "홈이나 매칭 탭에서 원하는 여행을 선택하고 ‘동행 신청’을 눌러주세요.",
                    isExpanded = true,
                    onClick = {}
                )
                FaqItemCard(
                    question = "Q. 동행 신청은 어떻게 하나요?",
                    answer = "홈이나 매칭 탭에서 원하는 여행을 선택하고 ‘동행 신청’을 눌러주세요.",
                    isExpanded = false,
                    onClick = {}
                )
            }
        }
    }
}

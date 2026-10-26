package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.model.AiMatchingCardData

private const val DAILY_AI_MATCHING_LIMIT = 3
private const val AI_MATCHING_CARD_RATIO = 343f / 456f

@Composable
fun AiMatchingSection(
    matchUsedCount: Int,
    data: AiMatchingCardData?,
    onPass: () -> Unit,
    onAccept: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "✨ 뚜르가 추천하는 여행 메이트",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.gray900
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "회원님의 취향을 분석해 가장 잘 맞는 메이트를 찾아드려요!",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray500
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (data != null && matchUsedCount < DAILY_AI_MATCHING_LIMIT) {
            AiMatchingCard(
                data = data,
                onPass = onPass,
                onAccept = onAccept,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(AI_MATCHING_CARD_RATIO)
            )
        } else {
            AiMatchingFinishedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(AI_MATCHING_CARD_RATIO)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AiMatchingSectionPreview() {
    DduruTheme {
        AiMatchingSection(
            matchUsedCount = 1,
            data = AiMatchingCardData(
                postId = 1L,
                imageUrl = null,
                matchPercent = 92,
                title = "제주 서귀포 감성 여행",
                location = "제주 서귀포",
                dateRange = "2026.03.15 - 03.18",
                profileImageUrl = null,
                username = "username",
                age = 28,
                gender = "여성",
                currentMembers = 3,
                maxMembers = 4,
                description = "제주 서쪽 해안을 따라 느긋하게 드라이브하며 숨은 카페들을 찾아가는 여행입니다.",
                hashtags = listOf("카페투어", "사진")
            ),
            onPass = {},
            onAccept = {}
        )
    }
}

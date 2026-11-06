package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.DAILY_AI_MATCHING_LIMIT
import com.gildongmu.dduru.presentation.feature.home.model.AiMatchingCardData
import com.gildongmu.dduru.presentation.feature.home.state.HomeSurveyState

private const val AI_MATCHING_CARD_RATIO = 343f / 456f

@Composable
fun AiMatchingSection(
    matchUsedCount: Int,
    data: AiMatchingCardData?,
    surveyState: HomeSurveyState,
    onPass: () -> Unit,
    onAccept: () -> Unit,
    onSurveyClick: () -> Unit,
    onRetryClick: () -> Unit,
    isRetrying: Boolean = false,
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

        if (surveyState != HomeSurveyState.COMPLETED) {
            val isSurveyUnavailable = surveyState == HomeSurveyState.UNAVAILABLE ||
                surveyState == HomeSurveyState.UNKNOWN
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(AI_MATCHING_CARD_RATIO)
                    .background(DduruTheme.colors.gray0, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isSurveyUnavailable) {
                            "설문 상태를 불러오지 못했어요.\n다시 확인해 주세요."
                        } else {
                            "취향 설문을 완료하면\n나와 잘 맞는 여행 메이트를 만날 수 있어요!"
                        },
                        style = DduruTheme.typography.Body1,
                        color = DduruTheme.colors.gray700,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = if (isSurveyUnavailable) onRetryClick else onSurveyClick,
                        enabled = !isRetrying,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DduruTheme.colors.primary
                        )
                    ) {
                        Text(
                            text = when {
                                isRetrying -> "확인 중"
                                isSurveyUnavailable -> "다시 시도"
                                else -> "취향 설문 시작하기"
                            }
                        )
                    }
                }
            }
        } else if (data != null && matchUsedCount < DAILY_AI_MATCHING_LIMIT) {
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
            surveyState = HomeSurveyState.COMPLETED,
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
            onAccept = {},
            onSurveyClick = {},
            onRetryClick = {}
        )
    }
}

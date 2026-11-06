package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.mypage.component.InquiryEmptyCard
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryDetailUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryStatus

private val InquiryCardShape = RoundedCornerShape(16.dp)

@Composable
fun InquiryDetailScreen(
    inquiry: InquiryDetailUiModel,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onNewInquiryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAnswered = inquiry.status == InquiryStatus.ANSWERED

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "문의 내용",
            onBackClick = onBackClick,
            trailingContent = {
                InquiryEditButton(
                    enabled = !isAnswered,
                    onClick = onEditClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 40.dp)
        ) {
            InquiryQuestionCard(inquiry = inquiry)

            if (isAnswered) {
                InquiryAnswerCard(
                    answer = inquiry.answer.orEmpty(),
                    answerDate = inquiry.answerDate.orEmpty()
                )
                InquiryResolvedSection(onNewInquiryClick = onNewInquiryClick)
            } else {
                InquiryEmptyCard(message = "답변을 준비하고 있어요.\n문의 내용을 확인한 뒤 답변드릴게요.")
            }
        }
    }
}

@Composable
private fun InquiryEditButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.08f,
                shape = shape
            )
            .background(
                color = if (enabled) DduruTheme.colors.gray0.copy(alpha = 0.92f) else DduruTheme.colors.gray200,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = if (enabled) DduruTheme.colors.gray0.copy(alpha = 0.3f) else DduruTheme.colors.gray300,
                shape = shape
            )
            .then(if (enabled) Modifier.noRippleClickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "수정",
            style = DduruTheme.typography.Title2,
            color = if (enabled) DduruTheme.colors.black else DduruTheme.colors.gray400,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun InquiryQuestionCard(inquiry: InquiryDetailUiModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .inquiryCard()
            .padding(17.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = inquiry.category,
                    style = inquiryCaptionStyle(),
                    color = DduruTheme.colors.gray600
                )
                InquiryStatusTag(status = inquiry.status)
            }
            Text(
                text = inquiry.date,
                style = inquiryCaptionStyle(),
                color = DduruTheme.colors.gray600
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = inquiry.title,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray958
            )
            Text(
                text = inquiry.body,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray700
            )
        }
    }
}

@Composable
private fun InquiryStatusTag(status: InquiryStatus) {
    val isAnswered = status == InquiryStatus.ANSWERED

    Text(
        text = status.label,
        style = DduruTheme.typography.Micro2,
        color = if (isAnswered) DduruTheme.colors.gray0 else DduruTheme.colors.primary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(
                color = if (isAnswered) DduruTheme.colors.gray900 else DduruTheme.colors.secondary100,
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
private fun InquiryAnswerCard(
    answer: String,
    answerDate: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .inquiryCard()
            .padding(17.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_profile),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "고객센터 답변",
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray900
                )
                Text(
                    text = answerDate,
                    style = inquiryCaptionStyle(),
                    color = DduruTheme.colors.gray600
                )
            }
        }

        Text(
            text = answer,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray700
        )
    }
}

@Composable
private fun InquiryResolvedSection(onNewInquiryClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = "답변이 해결되었나요?",
            style = DduruTheme.typography.Body4,
            color = DduruTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        DduruButton(
            text = "새 문의하기",
            onClick = onNewInquiryClick,
            enabledColors = DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.gray0,
                textColor = DduruTheme.colors.gray900,
                borderColor = DduruTheme.colors.gray400
            ),
            textStyle = DduruTheme.typography.Title2,
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}

@Composable
private fun Modifier.inquiryCard(): Modifier = this
    .background(DduruTheme.colors.gray0, InquiryCardShape)
    .border(1.dp, DduruTheme.colors.gray200, InquiryCardShape)

@Composable
private fun inquiryCaptionStyle() = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp)

private val PreviewWaitingInquiry = InquiryDetailUiModel(
    category = "오류·버그",
    status = InquiryStatus.WAITING,
    date = "2026.08.21",
    title = "채팅방에서 사진을 보내면 앱이 종료되는 현상이 있어요.",
    body = "사진을 선택하고 전송을 누르면 앱이 꺼져요."
)

@Preview(showBackground = true, heightDp = 812, name = "답변 대기")
@Composable
private fun InquiryDetailWaitingPreview() {
    DduruTheme {
        InquiryDetailScreen(
            inquiry = PreviewWaitingInquiry,
            onBackClick = {},
            onEditClick = {},
            onNewInquiryClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 812, name = "답변 완료")
@Composable
private fun InquiryDetailAnsweredPreview() {
    DduruTheme {
        InquiryDetailScreen(
            inquiry = PreviewWaitingInquiry.copy(
                status = InquiryStatus.ANSWERED,
                answer = "안녕하세요, 고객센터입니다. 모집중 상태의 여행글은 1인당 최대 3개까지 작성할 수 있어요. " +
                    "기존 모집글을 마감하시면 새로운 글을 작성할 수 있습니다.",
                answerDate = "2026.08.21"
            ),
            onBackClick = {},
            onEditClick = {},
            onNewInquiryClick = {}
        )
    }
}

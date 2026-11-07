package com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostPostUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetMode
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetStep
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperhostApplyBottomSheet(
    uiState: SuperhostSheetUiState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(uiState.step) {
        if (uiState.step == SuperhostSheetStep.COMPLETE) {
            delay(1500L)
            sheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { if (uiState.step != SuperhostSheetStep.LOADING) onDismiss() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = DduruTheme.colors.gray0,
        scrimColor = DduruTheme.colors.black.copy(alpha = 0.5f),
        dragHandle = null
    ) {
        SuperhostApplySheetContent(
            uiState = uiState,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
internal fun SuperhostApplySheetSurface(
    uiState: SuperhostSheetUiState,
    modifier: Modifier = Modifier
) {
    SuperhostApplySheetContent(
        uiState = uiState,
        onDismiss = {},
        onConfirm = {},
        modifier = modifier.background(DduruTheme.colors.gray0, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    )
}

@Composable
private fun SuperhostApplySheetContent(
    uiState: SuperhostSheetUiState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isExtend = uiState.mode == SuperhostSheetMode.EXTEND

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 40.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    if (uiState.step == SuperhostSheetStep.COMPLETE) 8.dp else 4.dp
                )
            ) {
                Text(
                    text = if (isExtend) "슈퍼호스트 연장" else "슈퍼호스트 적용",
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray900
                )
                Text(
                    text = if (isExtend) {
                        "선택한 게시글에 슈퍼호스트를 연장할까요?"
                    } else {
                        "선택한 게시글에 슈퍼호스트를 적용할까요?"
                    },
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                SuperhostSheetPostSummary(post = uiState.post)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SuperhostSheetDetailRow(label = if (isExtend) "연장 기간" else "적용 기간", value = uiState.period)
                    SuperhostSheetDetailRow(label = "종료 예정", value = uiState.endDate)
                    SuperhostSheetTicketRow(ticketCount = uiState.ticketCount)
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info_circle),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray400,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = if (isExtend) {
                        "티켓 1장이 즉시 사용되며, 기존 기간 종료 후 연장됩니다."
                    } else {
                        "티켓 1장이 즉시 사용되며, 적용 후에는 취소할 수 없습니다"
                    },
                    style = DduruTheme.typography.Caption1.copy(lineHeight = 14.4.sp),
                    color = DduruTheme.colors.gray400,
                    textAlign = TextAlign.Center
                )
            }

            when (uiState.step) {
                SuperhostSheetStep.CONFIRM -> SuperhostSheetConfirmButtons(
                    onDismiss = onDismiss,
                    onConfirm = onConfirm
                )
                SuperhostSheetStep.LOADING -> SuperhostSheetLoadingButton()
                SuperhostSheetStep.COMPLETE -> SuperhostSheetCompleteIndicator()
            }
        }
    }
}

private val PreviewSheetState = SuperhostSheetUiState(
    post = SuperhostPostUiModel(
        id = 1,
        title = "야호!!! 이거보고 미국갔습니다",
        destination = "제주도 한라산",
        dateRange = "03.15 - 03.18",
        capacity = "3/4명",
        viewCount = "344",
        gender = MatchingGender.ANY,
        companionType = MatchingCompanionType.FULL
    ),
    mode = SuperhostSheetMode.APPLY,
    period = "3일 (72시간)",
    endDate = "2026년 10월 10일(금) 15:30",
    ticketCount = 3
)

@Preview(showBackground = true, name = "적용 확인")
@Composable
private fun SuperhostApplySheetConfirmPreview() {
    DduruTheme {
        SuperhostApplySheetContent(uiState = PreviewSheetState, onDismiss = {}, onConfirm = {})
    }
}

@Preview(showBackground = true, name = "적용 중")
@Composable
private fun SuperhostApplySheetLoadingPreview() {
    DduruTheme {
        SuperhostApplySheetContent(
            uiState = PreviewSheetState.copy(step = SuperhostSheetStep.LOADING),
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true, name = "적용 완료")
@Composable
private fun SuperhostApplySheetCompletePreview() {
    DduruTheme {
        SuperhostApplySheetContent(
            uiState = PreviewSheetState.copy(step = SuperhostSheetStep.COMPLETE),
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true, name = "연장 확인")
@Composable
private fun SuperhostExtendSheetPreview() {
    DduruTheme {
        SuperhostApplySheetContent(
            uiState = PreviewSheetState.copy(
                mode = SuperhostSheetMode.EXTEND,
                endDate = "2026년 10월 13일(화) 15:30",
                ticketCount = 2
            ),
            onDismiss = {},
            onConfirm = {}
        )
    }
}

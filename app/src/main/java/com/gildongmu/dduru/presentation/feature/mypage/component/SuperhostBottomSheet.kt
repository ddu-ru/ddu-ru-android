package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostPostUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetMode
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetStep
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetUiState

private val SheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
private val SheetButtonShape = RoundedCornerShape(16.dp)
private const val SPINNER_DURATION_MILLIS = 1000
private const val SHEET_SCRIM_ALPHA = 0.5f

private val InfoBulletItems = listOf(
    "티켓 1장으로 게시글 1개에 적용할 수 있어요",
    "슈퍼호스트는 적용한 시점부터 3일 동안 유지돼요",
    "한 번에 하나의 게시글에만 적용할 수 있어요",
    "적용 중인 게시글과 남은 기간은 작성글 관리에서 확인할 수 있어요"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperhostApplyBottomSheet(
    uiState: SuperhostSheetUiState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { if (uiState.step == SuperhostSheetStep.CONFIRM) onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = SheetShape,
        containerColor = DduruTheme.colors.gray0,
        scrimColor = DduruTheme.colors.black.copy(alpha = SHEET_SCRIM_ALPHA),
        dragHandle = null
    ) {
        SuperhostApplySheetContent(
            uiState = uiState,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperhostInfoBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = SheetShape,
        containerColor = DduruTheme.colors.gray0,
        scrimColor = DduruTheme.colors.black.copy(alpha = SHEET_SCRIM_ALPHA),
        dragHandle = null
    ) {
        SuperhostInfoSheetContent(onDismiss = onDismiss)
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
        modifier = modifier.background(DduruTheme.colors.gray0, SheetShape)
    )
}

@Composable
internal fun SuperhostInfoSheetSurface(modifier: Modifier = Modifier) {
    SuperhostInfoSheetContent(
        onDismiss = {},
        modifier = modifier.background(DduruTheme.colors.gray0, SheetShape)
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
                SheetPostSummary(post = uiState.post)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SheetDetailRow(label = if (isExtend) "연장 기간" else "적용 기간", value = uiState.period)
                    SheetDetailRow(label = "종료 예정", value = uiState.endDate)
                    SheetTicketRow(ticketCount = uiState.ticketCount)
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
                SuperhostSheetStep.CONFIRM -> SheetConfirmButtons(
                    onDismiss = onDismiss,
                    onConfirm = onConfirm
                )
                SuperhostSheetStep.LOADING -> SheetLoadingButton()
                SuperhostSheetStep.COMPLETE -> SheetCompleteIndicator()
            }
        }
    }
}

@Composable
private fun SheetPostSummary(post: SuperhostPostUiModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            PostTitleAndMeta(
                title = post.title,
                destination = post.destination,
                dateRange = post.dateRange,
                capacity = post.capacity
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                post.gender?.let { MyPostGenderTag(gender = it) }
                post.companionType?.let { MyPostCompanionTag(companionType = it) }
            }
        }

        PostThumbnail(thumbnailUrl = post.thumbnailUrl)
    }
}

@Composable
private fun SheetDetailRow(
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )
        Text(
            text = value,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )
    }
}

@Composable
private fun SheetTicketRow(ticketCount: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "보유 티켓",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SheetTicketCount(
                count = ticketCount,
                modifier = Modifier.alpha(0.6f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = DduruTheme.colors.black,
                modifier = Modifier
                    .size(12.dp)
                    .padding(horizontal = 1.dp)
                    .alpha(0.6f)
            )
            SheetTicketCount(count = (ticketCount - 1).coerceAtLeast(0))
        }
    }
}

@Composable
private fun SheetTicketCount(
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_superhost_ticket),
            contentDescription = null,
            tint = DduruTheme.colors.primary,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = "${count}장",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )
    }
}

@Composable
private fun SheetConfirmButtons(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        SheetOutlinedButton(
            text = "취소",
            onClick = onDismiss,
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(SheetButtonShape)
                .background(DduruTheme.colors.primary)
                .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), SheetButtonShape)
                .noRippleClickable(onClick = onConfirm)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_superhost_ticket),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "1장",
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray0
                )
            }
            Text(
                text = "사용",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Composable
private fun SheetOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .clip(SheetButtonShape)
            .background(DduruTheme.colors.gray0)
            .border(1.dp, DduruTheme.colors.gray200, SheetButtonShape)
            .noRippleClickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Title1,
            color = DduruTheme.colors.gray700
        )
    }
}

@Composable
private fun SheetLoadingButton() {
    val rotation by rememberInfiniteTransition(label = "superhostSpinner").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = SPINNER_DURATION_MILLIS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "superhostSpinnerRotation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(SheetButtonShape)
            .background(DduruTheme.colors.gray0.copy(alpha = 0.4f).compositeOver(DduruTheme.colors.primary))
            .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), SheetButtonShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle_notch),
            contentDescription = "처리 중",
            tint = DduruTheme.colors.gray0,
            modifier = Modifier
                .size(24.dp)
                .rotate(rotation)
        )
    }
}

@Composable
private fun SheetCompleteIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        modifier = Modifier.height(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null,
            tint = DduruTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "사용 완료",
            style = DduruTheme.typography.Caption1.copy(lineHeight = 14.4.sp),
            color = DduruTheme.colors.primary
        )
    }
}

@Composable
private fun SuperhostInfoSheetContent(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bodyStyle = DduruTheme.typography.Body2

    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 40.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .background(DduruTheme.colors.primary, CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_superhost),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "슈퍼호스트란?",
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray900
                )

                Text(
                    text = "내 모집글을 더 많은 동행에게 보여줄 수 있는 권한이에요",
                    style = bodyStyle,
                    color = DduruTheme.colors.gray600
                )

                Text(
                    text = buildAnnotatedString {
                        append("슈퍼호스트를 적용한 게시글은 ")
                        withStyle(SpanStyle(color = DduruTheme.colors.gray700)) {
                            append("일반 게시글보다 더 눈에 잘 띄는 위치에 우선 노출")
                        }
                        append("돼요. 동행을 찾는 사용자에게 게시글이 노출될 기회가 늘어나 더 빠르게 동행을 모집할 수 있어요")
                    },
                    style = bodyStyle,
                    color = DduruTheme.colors.gray600
                )

                Column {
                    InfoBulletItems.forEach { item ->
                        Row {
                            Text(
                                text = "•",
                                style = bodyStyle.copy(lineHeight = 25.2.sp),
                                color = DduruTheme.colors.gray600,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 7.dp)
                            )
                            Text(
                                text = item,
                                style = bodyStyle.copy(lineHeight = 25.2.sp),
                                color = DduruTheme.colors.gray600
                            )
                        }
                    }
                }
            }
        }

        SheetOutlinedButton(
            text = "확인",
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )
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

@Preview(showBackground = true, name = "슈퍼호스트 안내")
@Composable
private fun SuperhostInfoSheetPreview() {
    DduruTheme {
        SuperhostInfoSheetContent(onDismiss = {})
    }
}

package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.dduruFrostedBackground
import com.gildongmu.dduru.core.common.extension.dduruFrostedBorder
import com.gildongmu.dduru.core.common.extension.dduruFrostedSurface
import com.gildongmu.dduru.core.common.extension.rememberDduruHazeState
import com.gildongmu.dduru.core.common.extension.toDotDate
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import dev.chrisbanes.haze.HazeState

enum class MatchingPostRecruitStatus {
    OPEN,
    CLOSED,
    HIDDEN
}

enum class MatchingPostParticipationStatus {
    NONE,
    PENDING,
    APPROVED,
    REJECTED
}

@Composable
fun MatchingDetailBottomActionComponent(
    startDate: String,
    endDate: String,
    recruitCapacity: Int,
    recruitCount: Int,
    tripDurationText: String,
    status: MatchingPostRecruitStatus,
    isFull: Boolean,
    isOwner: Boolean,
    myParticipationStatus: MatchingPostParticipationStatus,
    onButtonClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    requestCount: Int? = null
) {
    val containerShape = RoundedCornerShape(24.dp)
    val remainingSlots = (recruitCapacity - recruitCount).coerceAtLeast(0)
    val buttonState = MatchingDetailBottomButtonState.from(
        isOwner = isOwner,
        status = status,
        isFull = isFull,
        myParticipationStatus = myParticipationStatus
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 20.dp,
                shape = containerShape,
                spotColor = DduruTheme.colors.black.copy(alpha = 0.08f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.08f)
            )
            .clip(containerShape)
            .dduruFrostedSurface(
                hazeState = hazeState,
                shape = containerShape
            )
            .dduruFrostedBackground()
            .dduruFrostedBorder(containerShape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${startDate.toDotDate()} ~ ${endDate.toDotDate()}",
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray600
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${remainingSlots}자리 남음 · $tripDurationText",
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray928
            )
        }

        DduruButton(
            text = buttonState.text,
            onClick = onButtonClick,
            enabled = buttonState.enabled,
            enabledColors = buttonState.enabledColors(),
            disabledColors = buttonState.disabledColors(),
            textStyle = DduruTheme.typography.Title1,
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
            trailingContent = if (
                buttonState == MatchingDetailBottomButtonState.Manage &&
                requestCount != null &&
                requestCount > 0
            ) {
                {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                color = DduruTheme.colors.validationRed,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = requestCount.toString(),
                            style = DduruTheme.typography.Micro,
                            color = DduruTheme.colors.gray0
                        )
                    }
                }
            } else {
                null
            }
        )
    }
}

private enum class MatchingDetailBottomButtonState(
    val text: String,
    val enabled: Boolean
) {
    Manage("신청 관리", true),
    Apply("동행 신청", true),
    Closed("모집 마감", false),
    Waiting("대기 중", true);

    @Composable
    fun enabledColors(): DduruButtonEnabledColors {
        return when (this) {
            Apply -> DduruButtonDefaults.filledEnabledColors

            Manage,
            Waiting -> DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.gray0,
                textColor = DduruTheme.colors.gray700,
                borderColor = DduruTheme.colors.gray200
            )

            Closed -> DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.gray200,
                textColor = DduruTheme.colors.gray400,
                borderColor = DduruTheme.colors.gray300
            )
        }
    }

    @Composable
    fun disabledColors(): DduruButtonDisabledColors {
        return DduruButtonDisabledColors(
            backgroundColor = DduruTheme.colors.gray200,
            textColor = DduruTheme.colors.gray400,
            borderColor = DduruTheme.colors.gray300
        )
    }

    companion object {
        fun from(
            isOwner: Boolean,
            status: MatchingPostRecruitStatus,
            isFull: Boolean,
            myParticipationStatus: MatchingPostParticipationStatus
        ): MatchingDetailBottomButtonState {
            return when {
                isOwner -> Manage
                status != MatchingPostRecruitStatus.OPEN || isFull -> Closed
                myParticipationStatus == MatchingPostParticipationStatus.PENDING -> Waiting
                myParticipationStatus == MatchingPostParticipationStatus.APPROVED -> Waiting
                else -> Apply
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailBottomActionOwnerPreview() {
    val hazeState = rememberDduruHazeState()

    DduruTheme {
        MatchingDetailBottomActionComponent(
            startDate = "2026-06-01",
            endDate = "2026-06-10",
            recruitCapacity = 3,
            recruitCount = 2,
            tripDurationText = "9박 10일",
            status = MatchingPostRecruitStatus.OPEN,
            isFull = false,
            isOwner = true,
            myParticipationStatus = MatchingPostParticipationStatus.NONE,
            requestCount = 3,
            onButtonClick = {},
            hazeState = hazeState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailBottomActionGuestPreview() {
    val hazeState = rememberDduruHazeState()

    DduruTheme {
        MatchingDetailBottomActionComponent(
            startDate = "2026-06-01",
            endDate = "2026-06-10",
            recruitCapacity = 3,
            recruitCount = 2,
            tripDurationText = "9박 10일",
            status = MatchingPostRecruitStatus.OPEN,
            isFull = false,
            isOwner = false,
            myParticipationStatus = MatchingPostParticipationStatus.NONE,
            onButtonClick = {},
            hazeState = hazeState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailBottomActionWaitingPreview() {
    val hazeState = rememberDduruHazeState()

    DduruTheme {
        MatchingDetailBottomActionComponent(
            startDate = "2026-06-01",
            endDate = "2026-06-10",
            recruitCapacity = 3,
            recruitCount = 2,
            tripDurationText = "9박 10일",
            status = MatchingPostRecruitStatus.OPEN,
            isFull = false,
            isOwner = false,
            myParticipationStatus = MatchingPostParticipationStatus.PENDING,
            onButtonClick = {},
            hazeState = hazeState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailBottomActionClosedPreview() {
    val hazeState = rememberDduruHazeState()

    DduruTheme {
        MatchingDetailBottomActionComponent(
            startDate = "2026-06-01",
            endDate = "2026-06-10",
            recruitCapacity = 3,
            recruitCount = 3,
            tripDurationText = "9박 10일",
            status = MatchingPostRecruitStatus.CLOSED,
            isFull = true,
            isOwner = false,
            myParticipationStatus = MatchingPostParticipationStatus.NONE,
            onButtonClick = {},
            hazeState = hazeState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

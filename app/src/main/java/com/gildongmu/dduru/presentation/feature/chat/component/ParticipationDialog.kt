package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class ParticipationDialogType {
    ACCEPT,
    REJECT,
    CONFIRMED
}

@Composable
fun ParticipationDialog(
    type: ParticipationDialogType,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val title = when (type) {
        ParticipationDialogType.ACCEPT -> "이 신청을 수락할까요?"
        ParticipationDialogType.REJECT -> "이 신청을 거절할까요?"
        ParticipationDialogType.CONFIRMED -> "동행 멤버가 확정되었어요 🎉"
    }
    val description = when (type) {
        ParticipationDialogType.ACCEPT -> "수락하면 신청자가 동행 멤버로 확정됩니다.\n수락 후에는 되돌릴 수 없습니다."
        ParticipationDialogType.REJECT -> "거절한 신청은 되돌릴 수 없습니다.\n신중하게 결정해 주세요"
        ParticipationDialogType.CONFIRMED -> "축하합니다!\n이제 채팅을 통해 여행을 준비해 보세요."
    }
    val dismissText = if (type == ParticipationDialogType.CONFIRMED) "닫기" else "취소"
    val confirmText = when (type) {
        ParticipationDialogType.ACCEPT -> "수락"
        ParticipationDialogType.REJECT -> "거절"
        ParticipationDialogType.CONFIRMED -> "채팅방으로 이동"
    }

    Dialog(onDismissRequest = onDismiss) {
        ParticipationDialogContent(
            title = title,
            description = description,
            dismissText = dismissText,
            confirmText = confirmText,
            type = type,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun ParticipationDialogContent(
    title: String,
    description: String,
    dismissText: String,
    confirmText: String,
    type: ParticipationDialogType,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val confirmBgColor = if (type == ParticipationDialogType.REJECT) {
        DduruTheme.colors.rejectConfirmBg
    } else {
        DduruTheme.colors.primary
    }
    val confirmTextColor = if (type == ParticipationDialogType.REJECT) {
        DduruTheme.colors.rejectRed
    } else {
        DduruTheme.colors.gray0
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DduruTheme.colors.black.copy(alpha = 0.18f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.18f)
            )
            .width(335.dp)
            .background(color = DduruTheme.colors.gray0, shape = RoundedCornerShape(16.dp))
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = title,
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray900
            )
            Text(
                text = description,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray600
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray200,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = DduruTheme.colors.gray0,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .noRippleClickable(onClick = onDismiss)
            ) {
                Text(
                    text = dismissText,
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray700
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = confirmBgColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .noRippleClickable(onClick = onConfirm)
            ) {
                Text(
                    text = confirmText,
                    style = DduruTheme.typography.Title1,
                    color = confirmTextColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ParticipationDialogAcceptPreview() {
    DduruTheme {
        ParticipationDialogContent(
            title = "이 신청을 수락할까요?",
            description = "수락하면 신청자가 동행 멤버로 확정됩니다.\n수락 후에는 되돌릴 수 없습니다.",
            dismissText = "취소",
            confirmText = "수락",
            type = ParticipationDialogType.ACCEPT,
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ParticipationDialogRejectPreview() {
    DduruTheme {
        ParticipationDialogContent(
            title = "이 신청을 거절할까요?",
            description = "거절한 신청은 되돌릴 수 없습니다.\n신중하게 결정해 주세요",
            dismissText = "취소",
            confirmText = "거절",
            type = ParticipationDialogType.REJECT,
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ParticipationDialogConfirmedPreview() {
    DduruTheme {
        ParticipationDialogContent(
            title = "동행 멤버가 확정되었어요 🎉",
            description = "축하합니다!\n이제 채팅을 통해 여행을 준비해 보세요.",
            dismissText = "닫기",
            confirmText = "채팅방으로 이동",
            type = ParticipationDialogType.CONFIRMED,
            onDismiss = {},
            onConfirm = {}
        )
    }
}

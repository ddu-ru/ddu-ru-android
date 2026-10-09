package com.gildongmu.dduru.presentation.feature.mypage.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ConfirmDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    dismissText: String = "취소",
    confirmText: String = "확인",
    confirmBackgroundColor: Color? = null,
    confirmTextColor: Color? = null
) {
    Dialog(onDismissRequest = onDismiss) {
        ConfirmDialogContent(
            title = title,
            description = description,
            dismissText = dismissText,
            confirmText = confirmText,
            confirmBackgroundColor = confirmBackgroundColor ?: DduruTheme.colors.primary,
            confirmTextColor = confirmTextColor ?: DduruTheme.colors.gray0,
            onDismiss = onDismiss,
            onConfirm = onConfirm,
            modifier = modifier
        )
    }
}

@Composable
private fun ConfirmDialogContent(
    title: String,
    description: String,
    dismissText: String,
    confirmText: String,
    confirmBackgroundColor: Color,
    confirmTextColor: Color,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .dduruDualShadow(
                firstAlpha = 0.18f,
                secondAlpha = 0.18f,
                shape = RoundedCornerShape(16.dp),
                firstElevation = 20.dp
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
                        color = confirmBackgroundColor,
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
private fun ConfirmDialogRejectPreview() {
    DduruTheme {
        ConfirmDialogContent(
            title = "사진을 삭제할까요?",
            description = "삭제한 사진은 복구할 수 없습니다.\n신중하게 결정해 주세요",
            dismissText = "취소",
            confirmText = "삭제",
            confirmBackgroundColor = DduruTheme.colors.rejectConfirmBg,
            confirmTextColor = DduruTheme.colors.rejectRed,
            onDismiss = {},
            onConfirm = {}
        )
    }
}

package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyAdminKickConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(355f / 202f)
                .clip(RoundedCornerShape(16.dp))
                .background(DduruTheme.colors.gray0)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "정말로 강퇴하시겠어요?",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray900,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "해당 멤버는 이 여행과 채팅방에서 제외됩니다.\n이 작업은 되돌릴 수 없습니다.",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray700
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "취소",
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    enabledColors = DduruButtonDefaults.whiteEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors
                )

                DduruButton(
                    text = "강퇴",
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f),
                    enabledColors = DduruButtonEnabledColors(
                        backgroundColor = DduruTheme.colors.rejectConfirmBg,
                        textColor = DduruTheme.colors.journeyKickText,
                        borderColor = DduruTheme.colors.rejectConfirmBg
                    ),
                    disabledColors = DduruButtonDefaults.whiteDisabledColors
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyAdminKickConfirmDialogPreview() {
    DduruTheme {
        JourneyAdminKickConfirmDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}

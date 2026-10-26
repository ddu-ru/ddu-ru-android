package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.mypage.component.AgreementCheckRow
import com.gildongmu.dduru.presentation.feature.mypage.component.ConfirmDialog
import com.gildongmu.dduru.presentation.feature.mypage.component.WithdrawNoticeSection
import com.gildongmu.dduru.presentation.feature.mypage.component.WithdrawWarningBox

private val CautionItems = listOf(
    "진행 중인 여행에서 자동으로 나가게 됩니다.",
    "작성한 모집글과 후기가 모두 삭제됩니다.",
    "동일 계정 재가입은 30일 후에 가능합니다.",
    "탈퇴 후에는 데이터 복구가 불가합니다."
)

private val DataDeletionItems = listOf(
    "프로필 정보, 여행 이력, 채팅 내용이 즉시 삭제됩니다.",
    "관련 법령에 따라 일부 정보(결제·분쟁 이력 등)는 최대 5년간 보관될 수 있습니다."
)

@Composable
fun WithdrawScreen(
    isAgreed: Boolean,
    onAgreedChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    modifier: Modifier = Modifier,
    showConfirmDialog: Boolean = false,
    onConfirmDialogDismiss: () -> Unit = {},
    onWithdrawConfirm: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "회원탈퇴",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            Column(verticalArrangement = Arrangement.spacedBy(64.dp)) {
                WithdrawWarningBox(
                    title = "탈퇴 전 꼭 확인해주세요",
                    description = "탈퇴 시 계정 및 모든 데이터가 삭제되며, 복구할 수 없습니다.\n탈퇴 전 아래 내용을 꼭 확인해주세요."
                )

                WithdrawNoticeSection(
                    title = "탈퇴 시 주의사항",
                    items = CautionItems
                )

                WithdrawNoticeSection(
                    title = "데이터 삭제 안내",
                    items = DataDeletionItems
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 25.dp)
        ) {
            AgreementCheckRow(
                checked = isAgreed,
                text = "위 내용을 모두 확인했으며, 회원탈퇴에 동의합니다.",
                onCheckedChange = onAgreedChange
            )

            Spacer(modifier = Modifier.height(40.dp))

            DduruButton(
                text = "회원 탈퇴하기",
                onClick = onWithdrawClick,
                enabled = isAgreed,
                enabledColors = DduruButtonEnabledColors(
                    backgroundColor = DduruTheme.colors.gray958,
                    textColor = DduruTheme.colors.gray0,
                    borderColor = DduruTheme.colors.gray0.copy(alpha = 0.1f)
                ),
                disabledColors = DduruButtonDisabledColors(
                    backgroundColor = DduruTheme.colors.gray200,
                    textColor = DduruTheme.colors.gray400,
                    borderColor = DduruTheme.colors.gray300
                ),
                textStyle = DduruTheme.typography.H1,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }

    if (showConfirmDialog) {
        ConfirmDialog(
            title = "정말로 탈퇴하시겠어요?",
            description = "이 선택은 되돌릴 수 없습니다.\n신중하게 결정해 주세요",
            onDismiss = onConfirmDialogDismiss,
            onConfirm = onWithdrawConfirm,
            dismissText = "취소",
            confirmText = "탈퇴",
            confirmBackgroundColor = DduruTheme.colors.rejectConfirmBg,
            confirmTextColor = DduruTheme.colors.validationRed
        )
    }
}

@Composable
private fun WithdrawScreenPreviewHost(
    initialAgreed: Boolean,
    showConfirmDialog: Boolean = false
) {
    var isAgreed by remember { mutableStateOf(initialAgreed) }

    WithdrawScreen(
        isAgreed = isAgreed,
        onAgreedChange = { isAgreed = it },
        onBackClick = {},
        onWithdrawClick = {},
        showConfirmDialog = showConfirmDialog
    )
}

@Preview(showBackground = true, heightDp = 844, name = "동의 전")
@Composable
private fun WithdrawScreenPreview() {
    DduruTheme { WithdrawScreenPreviewHost(initialAgreed = false) }
}

@Preview(showBackground = true, heightDp = 844, name = "동의 후")
@Composable
private fun WithdrawScreenAgreedPreview() {
    DduruTheme { WithdrawScreenPreviewHost(initialAgreed = true) }
}

@Preview(showBackground = true, heightDp = 844, name = "탈퇴 확인 박스")
@Composable
private fun WithdrawScreenConfirmDialogPreview() {
    DduruTheme { WithdrawScreenPreviewHost(initialAgreed = true, showConfirmDialog = true) }
}

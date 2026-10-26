package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.mypage.component.ConfirmDialog
import com.gildongmu.dduru.presentation.feature.mypage.component.SettingsMenuItem
import com.gildongmu.dduru.presentation.feature.mypage.component.SettingsMenuSection

@Composable
fun AccountSettingsScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    modifier: Modifier = Modifier,
    showLogoutDialog: Boolean = false,
    onLogoutDialogDismiss: () -> Unit = {},
    onLogoutConfirm: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "계정관리",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsMenuSection(
            items = listOf(
                SettingsMenuItem(title = "로그아웃", onClick = onLogoutClick),
                SettingsMenuItem(title = "회원탈퇴", onClick = onWithdrawClick)
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

    if (showLogoutDialog) {
        ConfirmDialog(
            title = "로그아웃 하시겠습니까?",
            description = "나중에 다시 돌아올 수 있어요.\n정말로 로그아웃 하시겠습니까?",
            onDismiss = onLogoutDialogDismiss,
            onConfirm = onLogoutConfirm,
            dismissText = "취소",
            confirmText = "로그아웃",
            confirmBackgroundColor = DduruTheme.colors.gray900,
            confirmTextColor = DduruTheme.colors.gray0
        )
    }
}

@Preview(showBackground = true, heightDp = 844, name = "계정관리")
@Composable
private fun AccountSettingsScreenPreview() {
    DduruTheme {
        AccountSettingsScreen(
            onBackClick = {},
            onLogoutClick = {},
            onWithdrawClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, name = "로그아웃 확인")
@Composable
private fun AccountSettingsScreenLogoutDialogPreview() {
    DduruTheme {
        AccountSettingsScreen(
            onBackClick = {},
            onLogoutClick = {},
            onWithdrawClick = {},
            showLogoutDialog = true
        )
    }
}

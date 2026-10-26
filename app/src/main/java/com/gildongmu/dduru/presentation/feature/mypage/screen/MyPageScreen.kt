package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.component.ActionMenuItem
import com.gildongmu.dduru.presentation.feature.mypage.component.ActionMenuSection
import com.gildongmu.dduru.presentation.feature.mypage.component.MyPageHeader
import com.gildongmu.dduru.presentation.feature.mypage.component.SettingsMenuItem
import com.gildongmu.dduru.presentation.feature.mypage.component.SettingsMenuSection

@Composable
fun MyPageRoute(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { MyPageHeader(onNotificationClick, onMenuClick) }
        item {
            ActionMenuSection(
                items = listOf(
                    ActionMenuItem(R.drawable.ic_heart_rounded, "찜한 여행", {}),
                    ActionMenuItem(R.drawable.ic_pencil, "작성글 관리", {}),
                    ActionMenuItem(R.drawable.ic_prohibit, "차단 목록", {})
                )
            )
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                SettingsMenuSection(
                    items = listOf(SettingsMenuItem("고객센터", {})),
                    label = "고객 지원"
                )
                SettingsMenuSection(
                    items = listOf(SettingsMenuItem("계정 관리", {})),
                    label = "계정"
                )
            }
        }
    }
}

package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.mypage.component.BlockedUserItem
import com.gildongmu.dduru.presentation.feature.mypage.model.BlockedUserUiModel

@Composable
fun BlockedListScreen(
    users: List<BlockedUserUiModel>,
    onBackClick: () -> Unit,
    onProfileClick: (Long) -> Unit,
    onUnblockClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "차단 목록",
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp)
        ) {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                BlockedUserItem(
                    user = user,
                    onProfileClick = { onProfileClick(user.id) },
                    onUnblockClick = { onUnblockClick(user.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 844, name = "차단 목록")
@Composable
private fun BlockedListScreenPreview() {
    DduruTheme {
        BlockedListScreen(
            users = List(3) { index ->
                BlockedUserUiModel(
                    id = index.toLong(),
                    username = "username",
                    introduction = "음식 관심 많아요"
                )
            },
            onBackClick = {},
            onProfileClick = {},
            onUnblockClick = {}
        )
    }
}

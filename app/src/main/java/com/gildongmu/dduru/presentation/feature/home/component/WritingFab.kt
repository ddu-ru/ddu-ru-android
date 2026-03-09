package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun WritingFab(
    onCreateTravelRoom: () -> Unit,
    onWriteCommunity: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    if (isExpanded) {
        WritingExpandedMenu(
            onDismiss = { isExpanded = false },
            onCreateTravelRoom = onCreateTravelRoom,
            onWriteCommunity = onWriteCommunity,
            modifier = modifier
        )
    } else {
        WritingButton(
            onClick = { isExpanded = true },
            modifier = modifier
        )
    }
}

@Preview(name = "WritingFab - 기본 버튼 상태", showBackground = true)
@Composable
private fun WritingFabCollapsedPreview() {
    DduruTheme {
        WritingFab(
            onCreateTravelRoom = {},
            onWriteCommunity = {}
        )
    }
}

@Preview(name = "WritingFab - 확장 메뉴 상태", showBackground = true)
@Composable
private fun WritingFabExpandedPreview() {
    DduruTheme {
        WritingExpandedMenu(
            onDismiss = {},
            onCreateTravelRoom = {},
            onWriteCommunity = {}
        )
    }
}

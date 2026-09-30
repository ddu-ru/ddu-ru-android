package com.gildongmu.dduru.presentation.feature.poll.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.poll.component.JourneyPollActionButton
import com.gildongmu.dduru.presentation.feature.poll.component.JourneyPollTopBar
import com.gildongmu.dduru.presentation.feature.poll.component.PollCreatorInfo
import com.gildongmu.dduru.presentation.feature.poll.component.PollDatePicker
import com.gildongmu.dduru.presentation.feature.poll.component.PollVoteList
import com.gildongmu.dduru.presentation.feature.poll.state.JourneyPollOption
import com.gildongmu.dduru.presentation.feature.poll.state.JourneyPollUiState
import com.gildongmu.dduru.presentation.feature.poll.viewmodel.JourneyPollViewModel

@Composable
fun JourneyPollRoute(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyPollViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    JourneyPollScreen(
        uiState = uiState,
        onCloseClick = onCloseClick,
        onOptionClick = viewModel::onOptionClick,
        onVoteClick = viewModel::onVoteClick,
        onRevoteClick = viewModel::onRevoteClick,
        modifier = modifier
    )
}

@Composable
fun JourneyPollScreen(
    uiState: JourneyPollUiState,
    onCloseClick: () -> Unit,
    onOptionClick: (Int) -> Unit,
    onVoteClick: () -> Unit,
    onRevoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        JourneyPollTopBar(
            onCloseClick = onCloseClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        PollCreatorInfo(
            avatarUrl = uiState.avatarUrl,
            username = uiState.username,
            createdAt = uiState.createdAt,
            readCount = uiState.readCount,
            remainingTime = uiState.remainingTime
        )

        Spacer(modifier = Modifier.height(20.dp))

        PollVoteList(
            title = uiState.title,
            items = uiState.options.map { it.label },
            selectedIndex = uiState.selectedIndex,
            voteCounts = uiState.voteCounts,
            onItemClick = onOptionClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        JourneyPollActionButton(
            hasVoted = uiState.hasVoted,
            enabled = uiState.canVote,
            onVoteClick = onVoteClick,
            onRevoteClick = onRevoteClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        PollDatePicker(
            dateText = uiState.endDate,
            timeText = "${uiState.endTime}까지",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(name = "작성 후", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun JourneyPollScreenAfterCreatePreview() = DduruTheme {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    JourneyPollScreen(
        uiState = JourneyPollUiState(
            username = "길동무",
            createdAt = "3월 26일 13:09",
            readCount = 6,
            remainingTime = "투표 종료 5시간 전",
            title = "저녁 메뉴는 무엇으로 할까요?",
            options = listOf(
                JourneyPollOption(id = 1, label = "한식"),
                JourneyPollOption(id = 2, label = "양식"),
                JourneyPollOption(id = 3, label = "일식")
            ),
            endDate = "2026. 4. 14 (화)",
            endTime = "03:15",
            selectedIndex = selectedIndex
        ),
        onCloseClick = {},
        onOptionClick = { selectedIndex = it },
        onVoteClick = {},
        onRevoteClick = {}
    )
}

@Preview(name = "투표 후", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun JourneyPollScreenCompletedPreview() = DduruTheme {
    JourneyPollScreen(
        uiState = JourneyPollUiState(
            username = "길동무",
            createdAt = "3월 26일 13:09",
            readCount = 6,
            remainingTime = "투표 종료 5시간 전",
            title = "저녁 메뉴는 무엇으로 할까요?",
            options = listOf(
                JourneyPollOption(id = 1, label = "한식"),
                JourneyPollOption(id = 2, label = "양식"),
                JourneyPollOption(id = 3, label = "일식")
            ),
            selectedIndex = 1,
            voteCounts = listOf(3, 6, 2),
            endDate = "2026. 4. 14 (화)",
            endTime = "03:15"
        ),
        onCloseClick = {},
        onOptionClick = {},
        onVoteClick = {},
        onRevoteClick = {}
    )
}

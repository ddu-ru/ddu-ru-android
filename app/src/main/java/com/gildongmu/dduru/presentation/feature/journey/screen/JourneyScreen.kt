package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyCard
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyEmptyCard
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyNewsSection
import com.gildongmu.dduru.presentation.feature.journey.compoenet.PastJourneyCard
import com.gildongmu.dduru.presentation.feature.journey.compoenet.TodoItem
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyUiState
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyViewModel

@Composable
fun JourneyRoute(
    modifier: Modifier = Modifier,
    viewModel: JourneyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        UiState.Empty -> Unit
        UiState.Loading -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "나의 여정을 불러오지 못했어요.")
        is UiState.Success -> {
            JourneyScreen(
                uiState = state.data,
                onTodoCheckedChange = viewModel::onTodoCheckedChange,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun JourneyScreen(
    uiState: JourneyUiState,
    onTodoCheckedChange: (Long, Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            Text(
                text = "나의 여정",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray900
            )
        }

        item {
            JourneySection(title = "참여 중인 여정") {
                uiState.ongoingJourney?.let { journey ->
                    JourneyCard(
                        imageUrl = journey.imageUrl,
                        dDay = journey.dDay,
                        title = journey.title,
                        period = journey.period,
                        memberCount = journey.memberCount,
                        remainingTodoCount = journey.remainingTodoCount,
                        newNewsCount = journey.newNewsCount,
                        onJourneyClick = {}
                    )
                } ?: JourneyEmptyCard(
                    onExploreClick = {},
                    onCreateJourneyClick = {}
                )
            }
        }

        if (uiState.ongoingJourney != null && uiState.todos.isNotEmpty()) {
            item {
                JourneySection(title = "나의 할 일") {
                    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                        uiState.todos.forEach { todo ->
                            TodoItem(
                                title = todo.title,
                                caption = todo.caption,
                                assignees = todo.assignees,
                                isCompleted = todo.isCompleted,
                                onCheckedChange = { isCompleted ->
                                    onTodoCheckedChange(todo.id, isCompleted)
                                }
                            )
                        }
                    }
                }
            }
        }

        if (uiState.news.isNotEmpty()) {
            item {
                JourneySection(title = "새 소식") {
                    JourneyNewsSection(
                        news = uiState.news,
                        onNewsClick = {}
                    )
                }
            }
        }

        if (uiState.pastJourneys.isNotEmpty()) {
            item {
                JourneySection(title = "지난 여정") {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.pastJourneys, key = { it.id }) { journey ->
                            PastJourneyCard(
                                id = journey.id,
                                title = journey.title,
                                location = journey.location,
                                date = journey.date,
                                imageUrl = journey.imageUrl,
                                memberImageUrls = journey.memberImageUrls,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun JourneySection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.H2,
            color = DduruTheme.colors.gray900
        )

        Spacer(modifier = Modifier.height(16.dp))

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyScreenPreview() {
    DduruTheme {
        JourneyScreen(uiState = JourneyUiState())
    }
}

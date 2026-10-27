package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyCard
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyEmptyCard
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyNewsSection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneySchedule
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyTopBar
import com.gildongmu.dduru.presentation.feature.journey.component.PastJourneyCard
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyUiState
import com.gildongmu.dduru.presentation.feature.journey.state.journeyPreviewUiState
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyViewModel

@Composable
fun JourneyRoute(
    onNotificationClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onExploreClick: () -> Unit = {},
    onCreateJourneyClick: () -> Unit = {},
    onJourneyClick: (Long) -> Unit = {},
    onTodoAllClick: () -> Unit = {},
    onScheduleAllClick: () -> Unit = {},
    onNewsAllClick: () -> Unit = {},
    onNewsClick: (Long) -> Unit = {},
    onPastJourneyAllClick: () -> Unit = {},
    onPastJourneyClick: (Long) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: JourneyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refreshMyJourneys()
    }

    when (val state = uiState) {
        UiState.Empty -> Unit
        UiState.Loading -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "나의 여정을 불러오지 못했어요.")
        is UiState.Success -> {
            JourneyScreen(
                uiState = state.data,
                onTodoCheckedChange = viewModel::onTodoCheckedChange,
                onScheduleDayClick = viewModel::onScheduleDayClick,
                onNotificationClick = onNotificationClick,
                onMoreClick = onMoreClick,
                onExploreClick = onExploreClick,
                onCreateJourneyClick = onCreateJourneyClick,
                onJourneyClick = onJourneyClick,
                onTodoAllClick = onTodoAllClick,
                onScheduleAllClick = onScheduleAllClick,
                onNewsAllClick = onNewsAllClick,
                onNewsClick = onNewsClick,
                onPastJourneyAllClick = onPastJourneyAllClick,
                onPastJourneyClick = onPastJourneyClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun JourneyScreen(
    uiState: JourneyUiState,
    onTodoCheckedChange: (Long, Boolean) -> Unit,
    onScheduleDayClick: (Int) -> Unit,
    onNotificationClick: () -> Unit,
    onMoreClick: () -> Unit,
    onExploreClick: () -> Unit,
    onCreateJourneyClick: () -> Unit,
    onJourneyClick: (Long) -> Unit,
    onTodoAllClick: () -> Unit,
    onScheduleAllClick: () -> Unit,
    onNewsAllClick: () -> Unit,
    onNewsClick: (Long) -> Unit,
    onPastJourneyAllClick: () -> Unit,
    onPastJourneyClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.background(DduruTheme.colors.gray50),
        containerColor = DduruTheme.colors.gray50,
        topBar = {
            JourneyTopBar(
                onNotificationClick = onNotificationClick,
                onMoreClick = onMoreClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 104.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                JourneySection(
                    title = "참여 중인 여정",
                    count = uiState.ongoingJourneys.size,
                    countColor = DduruTheme.colors.gray600
                ) {
                    if (uiState.ongoingJourneys.isEmpty()) {
                        JourneyEmptyCard(
                            onExploreClick = onExploreClick,
                            onCreateJourneyClick = onCreateJourneyClick
                        )
                    } else {
                        val pagerState = rememberPagerState(
                            pageCount = { uiState.ongoingJourneys.size }
                        )

                        HorizontalPager(
                            state = pagerState,
                            contentPadding = PaddingValues(end = 32.dp),
                            pageSpacing = 8.dp,
                            key = { page -> uiState.ongoingJourneys[page].id }
                        ) { page ->
                            val journey = uiState.ongoingJourneys[page]

                            JourneyCard(
                                imageUrl = journey.imageUrl,
                                dDay = journey.dDay,
                                title = journey.title,
                                period = journey.period,
                                memberCount = journey.memberCount,
                                remainingTodoCount = journey.remainingTodoCount,
                                newNewsCount = journey.newNewsCount,
                                onJourneyClick = { onJourneyClick(journey.id) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // TODO API 연동 전까지 나의 여정 메인의 할 일 섹션은 노출하지 않습니다.

            if (uiState.ongoingJourneys.isNotEmpty() && uiState.scheduleDays.isNotEmpty()) {
                item {
                    JourneySection(
                        title = "다가오는 일정",
                        onAllClick = onScheduleAllClick
                    ) {
                        JourneySchedule(
                            days = uiState.scheduleDays,
                            selectedDay = uiState.selectedScheduleDay,
                            dDayText = uiState.scheduleDDayText,
                            schedules = uiState.schedules,
                            onDayClick = onScheduleDayClick
                        )
                    }
                }
            }

            if (uiState.news.isNotEmpty()) {
                item {
                    JourneySection(
                        title = "새 소식",
                        count = uiState.news.size,
                        countColor = DduruTheme.colors.primary,
                        onAllClick = onNewsAllClick
                    ) {
                        JourneyNewsSection(
                            news = uiState.news.take(MAX_VISIBLE_NEWS_COUNT),
                            onNewsClick = onNewsClick
                        )
                    }
                }
            }

            if (uiState.pastJourneys.isNotEmpty()) {
                item {
                    JourneySection(
                        title = "지난 여정 기록",
                        count = uiState.pastJourneys.size,
                        countColor = DduruTheme.colors.gray600,
                        onAllClick = onPastJourneyAllClick
                    ) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(uiState.pastJourneys, key = { it.id }) { journey ->
                                PastJourneyCard(
                                    id = journey.id,
                                    title = journey.title,
                                    location = journey.location,
                                    date = journey.date,
                                    imageUrl = journey.imageUrl,
                                    memberImageUrls = journey.memberImageUrls,
                                    onClick = onPastJourneyClick
                                )
                            }
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
    count: Int? = null,
    countColor: Color = DduruTheme.colors.primary,
    onAllClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = DduruTheme.typography.H2,
                    color = DduruTheme.colors.gray928
                )

                count?.let {
                    Text(
                        text = "${it}개",
                        style = DduruTheme.typography.Title3,
                        color = countColor
                    )
                }
            }

            onAllClick?.let { onClick ->
                Row(
                    modifier = Modifier.noRippleClickable(onClick = onClick),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "전체보기",
                        style = DduruTheme.typography.Body3,
                        color = DduruTheme.colors.gray600
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_right),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray600,
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        content()
    }
}

private const val MAX_VISIBLE_NEWS_COUNT = 3

@Preview(showBackground = true)
@Composable
private fun JourneyScreenPreview() {
    DduruTheme {
        JourneyScreen(
            uiState = journeyPreviewUiState,
            onTodoCheckedChange = { _, _ -> },
            onScheduleDayClick = {},
            onNotificationClick = {},
            onMoreClick = {},
            onExploreClick = {},
            onCreateJourneyClick = {},
            onJourneyClick = {},
            onTodoAllClick = {},
            onScheduleAllClick = {},
            onNewsAllClick = {},
            onNewsClick = {},
            onPastJourneyAllClick = {},
            onPastJourneyClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyEmptyScreenPreview() {
    DduruTheme {
        JourneyScreen(
            uiState = JourneyUiState(),
            onTodoCheckedChange = { _, _ -> },
            onScheduleDayClick = {},
            onNotificationClick = {},
            onMoreClick = {},
            onExploreClick = {},
            onCreateJourneyClick = {},
            onJourneyClick = {},
            onTodoAllClick = {},
            onScheduleAllClick = {},
            onNewsAllClick = {},
            onNewsClick = {},
            onPastJourneyAllClick = {},
            onPastJourneyClick = {}
        )
    }
}

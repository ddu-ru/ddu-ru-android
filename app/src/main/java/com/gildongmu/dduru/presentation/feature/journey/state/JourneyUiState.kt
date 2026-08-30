package com.gildongmu.dduru.presentation.feature.journey.state

import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyNewsItemUiState

data class JourneyUiState(
    val ongoingJourney: OngoingJourneyUiState? = fakeOngoingJourney,
    val todos: List<JourneyTodoUiState> = fakeTodos,
    val news: List<JourneyNewsItemUiState> = fakeNews,
    val pastJourneys: List<PastJourneyUiState> = fakePastJourneys
)

data class OngoingJourneyUiState(
    val id: Long,
    val imageUrl: String?,
    val dDay: String,
    val title: String,
    val period: String,
    val memberCount: Int,
    val remainingTodoCount: Int,
    val newNewsCount: Int
)

data class JourneyTodoUiState(
    val id: Long,
    val title: String,
    val caption: String?,
    val assignees: List<String>,
    val isCompleted: Boolean
)

data class PastJourneyUiState(
    val id: Int,
    val title: String,
    val location: String,
    val date: String,
    val imageUrl: String?,
    val memberImageUrls: List<String>
)

private val fakeOngoingJourney = OngoingJourneyUiState(
    id = 1L,
    imageUrl = "https://images.unsplash.com/photo-1528164344705-47542687000d",
    dDay = "D-23",
    title = "후쿠오카 미식 여행",
    period = "2026. 08. 27 - 2026. 08. 30",
    memberCount = 4,
    remainingTodoCount = 6,
    newNewsCount = 2
)

private val fakeTodos = listOf(
    JourneyTodoUiState(
        id = 1L,
        title = "숙소 예약하기",
        caption = "8월 20일까지",
        assignees = listOf("길동", "둘리"),
        isCompleted = false
    ),
    JourneyTodoUiState(
        id = 2L,
        title = "항공권 예매하기",
        caption = "8월 20일까지",
        assignees = listOf("길동"),
        isCompleted = true
    )
)

private val fakeNews = listOf(
    JourneyNewsItemUiState(
        id = 1L,
        title = "여행 공지가 수정됐어요",
        journeyTitle = "후쿠오카 미식 여행",
        timeText = "5분 전",
        isUnread = true
    ),
    JourneyNewsItemUiState(
        id = 2L,
        title = "일정이 변경되었어요",
        journeyTitle = "후쿠오카 미식 여행",
        timeText = "1시간 전",
        isUnread = true
    )
)

private val fakePastJourneys = listOf(
    PastJourneyUiState(
        id = 1,
        title = "제주도 힐링 여행",
        location = "제주도",
        date = "06. 12 - 06. 15",
        imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e",
        memberImageUrls = listOf(
            "https://i.pravatar.cc/96?img=12",
            "https://i.pravatar.cc/96?img=32",
            "https://i.pravatar.cc/96?img=47"
        )
    ),
    PastJourneyUiState(
        id = 2,
        title = "강릉 바다 여행",
        location = "강릉",
        date = "04. 20 - 04. 22",
        imageUrl = "https://images.unsplash.com/photo-1500534623283-312aade485b7",
        memberImageUrls = listOf(
            "https://i.pravatar.cc/96?img=20",
            "https://i.pravatar.cc/96?img=30"
        )
    )
)

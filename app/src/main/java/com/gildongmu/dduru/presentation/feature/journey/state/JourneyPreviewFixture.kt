package com.gildongmu.dduru.presentation.feature.journey.state

import com.gildongmu.dduru.presentation.feature.journey.component.JourneyNewsItemUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleDayUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleItemUiState

internal val journeyPreviewUiState = JourneyUiState(
    ongoingJourneys = listOf(
        OngoingJourneyUiState(
            id = 1L,
            imageUrl = "https://images.unsplash.com/photo-1528164344705-47542687000d",
            dDay = "D-23",
            title = "후쿠오카 미식 여행",
            period = "2026. 08. 27 - 2026. 08. 30",
            memberCount = 4,
            remainingTodoCount = 6,
            newNewsCount = 2
        ),
        OngoingJourneyUiState(
            id = 2L,
            imageUrl = "https://images.unsplash.com/photo-1500534623283-312aade485b7",
            dDay = "D-31",
            title = "강릉 바다 여행",
            period = "2026. 09. 04 - 2026. 09. 06",
            memberCount = 3,
            remainingTodoCount = 2,
            newNewsCount = 1
        )
    ),
    todos = listOf(
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
    ),
    scheduleDays = listOf(
        JourneyScheduleDayUiState(day = 1, dateText = "08. 27"),
        JourneyScheduleDayUiState(day = 2, dateText = "08. 28"),
        JourneyScheduleDayUiState(day = 3, dateText = "08. 29"),
        JourneyScheduleDayUiState(day = 4, dateText = "08. 30")
    ),
    selectedScheduleDay = 1,
    scheduleDDayText = "D-23",
    schedules = listOf(
        JourneyScheduleItemUiState(
            startTime = "10:00",
            endTime = "12:30",
            title = "인천국제공항 출발",
            location = "인천국제공항 제1여객터미널",
            memo = "출발 2시간 전까지 도착"
        ),
        JourneyScheduleItemUiState(
            startTime = "13:50",
            endTime = "15:30",
            title = "후쿠오카 공항 도착",
            location = "후쿠오카 공항"
        )
    ),
    news = listOf(
        JourneyNewsItemUiState(1L, "여행 공지가 수정됐어요", "후쿠오카 미식 여행", "5분 전", true),
        JourneyNewsItemUiState(2L, "일정이 변경되었어요", "후쿠오카 미식 여행", "1시간 전", true),
        JourneyNewsItemUiState(3L, "새로운 댓글이 달렸어요", "후쿠오카 미식 여행", "어제"),
        JourneyNewsItemUiState(4L, "투표가 시작됐어요", "강릉 바다 여행", "어제")
    ),
    pastJourneys = listOf(
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
        ),
        PastJourneyUiState(
            id = 3,
            title = "부산 야경 여행",
            location = "부산",
            date = "02. 14 - 02. 16",
            imageUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee",
            memberImageUrls = listOf(
                "https://i.pravatar.cc/96?img=14",
                "https://i.pravatar.cc/96?img=24",
                "https://i.pravatar.cc/96?img=34"
            )
        )
    )
)

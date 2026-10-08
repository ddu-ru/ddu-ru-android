package com.gildongmu.dduru.presentation.feature.poll.state

data class JourneyPollUiState(
    val username: String = "",
    val avatarUrl: String? = null,
    val createdAt: String = "",
    val readCount: Int = 0,
    val remainingTime: String = "",
    val title: String = "",
    val options: List<JourneyPollOption> = emptyList(),
    val selectedIndex: Int? = null,
    val voteCounts: List<Int>? = null,
    val endDate: String = "",
    val endTime: String = ""
) {
    val hasVoted: Boolean
        get() = voteCounts != null

    val canVote: Boolean
        get() = selectedIndex != null && !hasVoted
}

data class JourneyPollOption(
    val id: Long,
    val label: String
)

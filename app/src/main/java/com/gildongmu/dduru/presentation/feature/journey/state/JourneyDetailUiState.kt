package com.gildongmu.dduru.presentation.feature.journey.state

import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyDetailFilterType
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyMemberUiState

data class JourneyDetailUiState(
    val imageUrl: String? = null,
    val dDay: String = "",
    val title: String = "",
    val period: String = "",
    val members: List<JourneyMemberUiState> = emptyList(),
    val posts: List<JourneyDetailPostUiState> = emptyList(),
    val votes: List<JourneyDetailVoteUiState> = emptyList(),
    val selectedFilter: JourneyDetailFilterType = JourneyDetailFilterType.FIXED
)

data class JourneyDetailPostUiState(
    val id: Long,
    val category: JourneyDetailFilterType,
    val profileImageUrl: String? = null,
    val username: String,
    val createdAt: String,
    val title: String,
    val imageUrls: List<String> = emptyList(),
    val commentCount: Int = 0,
    val isPinned: Boolean = false
)

data class JourneyDetailVoteUiState(
    val id: Long,
    val title: String,
    val participantCount: Int,
    val isInProgress: Boolean,
    val endDate: String
)

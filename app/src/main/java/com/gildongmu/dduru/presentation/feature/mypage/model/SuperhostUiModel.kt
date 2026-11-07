package com.gildongmu.dduru.presentation.feature.mypage.model

import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender

enum class SuperhostSheetMode {
    APPLY,
    EXTEND
}

enum class SuperhostSheetStep {
    CONFIRM,
    LOADING,
    COMPLETE
}

data class SuperhostPostUiModel(
    val id: Long,
    val title: String,
    val destination: String,
    val dateRange: String,
    val capacity: String,
    val viewCount: String,
    val thumbnailUrl: String? = null,
    val remainingTime: String? = null,
    val gender: MatchingGender? = null,
    val companionType: MatchingCompanionType? = null
)

data class SuperhostSheetUiState(
    val post: SuperhostPostUiModel,
    val mode: SuperhostSheetMode,
    val step: SuperhostSheetStep = SuperhostSheetStep.CONFIRM,
    val period: String,
    val endDate: String,
    val ticketCount: Int
)

data class SuperhostUiState(
    val ticketCount: Int = 0,
    val appliedPosts: List<SuperhostPostUiModel> = emptyList(),
    val unappliedPosts: List<SuperhostPostUiModel> = emptyList(),
    val sheet: SuperhostSheetUiState? = null
)

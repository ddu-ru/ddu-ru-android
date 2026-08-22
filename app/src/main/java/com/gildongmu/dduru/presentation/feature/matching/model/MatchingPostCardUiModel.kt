package com.gildongmu.dduru.presentation.feature.matching.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class MatchingPostCardUiModel(
    val id: Long,
    val title: String,
    val authorName: String,
    val location: String,
    val dateRange: String,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val gender: MatchingGender,
    val companionType: MatchingCompanionType,
    val isSuperHost: Boolean = false,
    val isLiked: Boolean = false,
    @DrawableRes val thumbnailImageRes: Int,
    val thumbnailImageUrl: String? = null
)

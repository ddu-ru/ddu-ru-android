package com.gildongmu.dduru.presentation.feature.home.model

import kotlinx.collections.immutable.ImmutableList

data class HomePopularDestinationUiModel(
    val id: Long,
    val rank: Int,
    val destinationName: String,
    val waitingMateCount: Int,
    val whyHotText: String,
    val hashtags: ImmutableList<String>,
    val imageUrl: String
)

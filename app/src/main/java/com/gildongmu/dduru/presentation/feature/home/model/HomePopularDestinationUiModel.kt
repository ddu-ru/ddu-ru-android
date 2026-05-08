package com.gildongmu.dduru.presentation.feature.home.model

data class HomePopularDestinationUiModel(
    val id: Long,
    val rank: Int,
    val destinationName: String,
    val waitingMateCount: Int,
    val whyHotText: String,
    val hashtags: List<String>,
    val imageUrl: String
)

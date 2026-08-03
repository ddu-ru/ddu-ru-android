package com.gildongmu.dduru.presentation.feature.home.model

data class TravelCompanionUiModel(
    val id: Long,
    val title: String,
    val location: String,
    val dateText: String,
    val currentMemberCount: Int,
    val totalMemberCount: Int,
    val imageRes: Int,
    val imageUrl: String? = null
)

package com.gildongmu.dduru.presentation.feature.home.model

data class AiMatchingCardData(
    val imageUrl: String? = null,
    val matchPercent: Int,
    val title: String,
    val location: String,
    val dateRange: String,
    val profileImageUrl: String? = null,
    val username: String,
    val age: Int,
    val gender: String,
    val currentMembers: Int,
    val maxMembers: Int,
    val description: String,
    val hashtags: List<String>
)

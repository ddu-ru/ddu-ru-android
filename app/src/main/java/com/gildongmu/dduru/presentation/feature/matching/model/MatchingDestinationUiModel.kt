package com.gildongmu.dduru.presentation.feature.matching.model

import androidx.compose.runtime.Immutable

@Immutable
data class MatchingDestinationUiModel(
    val id: Long,
    val countryCode: String,
    val countryName: String,
    val city: String,
    val image: String?
)

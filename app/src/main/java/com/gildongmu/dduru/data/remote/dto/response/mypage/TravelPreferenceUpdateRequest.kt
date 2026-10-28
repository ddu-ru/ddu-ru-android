package com.gildongmu.dduru.data.remote.dto.request.mypage

import kotlinx.serialization.Serializable

@Serializable
data class TravelPreferenceUpdateRequest(
    val destinationPreferences: List<DestinationPreferenceRequest>,
    val availableDates: List<AvailableDateRequest>
)

@Serializable
data class DestinationPreferenceRequest(
    val type: String,
    val countryCode: String? = null,
    val destinationId: Long? = null
)

@Serializable
data class AvailableDateRequest(
    val startDate: String,
    val endDate: String
)

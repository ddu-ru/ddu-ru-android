package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class TravelPreferenceResponse(
    val destinationPreferences: List<DestinationPreferenceResponse> = emptyList(),
    val availableDates: List<AvailableDateResponse> = emptyList()
)

@Serializable
data class DestinationPreferenceResponse(
    val type: String,
    val countryCode: String? = null,
    val countryName: String? = null,
    val destinationId: Long? = null,
    val city: String? = null,
    val preferenceRank: Int = 0
)

@Serializable
data class AvailableDateResponse(
    val startDate: String,
    val endDate: String
)

@Serializable
data class DestinationPreferenceSearchResponse(
    val type: String,
    val countryCode: String? = null,
    val countryName: String? = null,
    val destinationId: Long? = null,
    val city: String? = null
)

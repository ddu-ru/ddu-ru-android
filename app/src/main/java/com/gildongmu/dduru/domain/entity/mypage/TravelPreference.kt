package com.gildongmu.dduru.domain.entity.mypage

import java.time.LocalDate

data class TravelPreference(
    val destinations: List<DestinationPreference>,
    val availableDates: List<AvailableDate>
)

data class DestinationPreference(
    val type: DestinationPreferenceType,
    val countryCode: String,
    val countryName: String,
    val destinationId: Long? = null,
    val city: String? = null
)

enum class DestinationPreferenceType { COUNTRY, CITY }

data class AvailableDate(
    val startDate: LocalDate,
    val endDate: LocalDate
)

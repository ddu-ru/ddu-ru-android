package com.gildongmu.dduru.presentation.feature.mypage.model

import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreferenceType
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState

private const val COUNTRY_LABEL = "국가 전체"

fun DestinationPreference.toPreferredDestination() = PreferredDestination(
    name = city ?: countryName,
    region = if (type == DestinationPreferenceType.CITY) countryName else COUNTRY_LABEL
)

fun DestinationPreference.toLocationItem() = LocationSearchItemUiState(
    id = destinationId ?: 0L,
    countryCode = countryCode,
    countryName = if (type == DestinationPreferenceType.CITY) countryName else COUNTRY_LABEL,
    city = city ?: countryName
)

fun LocationSearchItemUiState.toDestinationPreference() = if (id == 0L) {
    DestinationPreference(
        type = DestinationPreferenceType.COUNTRY,
        countryCode = countryCode,
        countryName = city
    )
} else {
    DestinationPreference(
        type = DestinationPreferenceType.CITY,
        countryCode = countryCode,
        countryName = countryName,
        destinationId = id,
        city = city
    )
}

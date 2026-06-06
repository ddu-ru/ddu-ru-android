package com.gildongmu.dduru.presentation.feature.matching.createPost.screen

import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState

object MatchingCreatePostMockData {
    val popularLocations = listOf(
        LocationSearchItemUiState(countryName = "일본", city = "도쿄"),
        LocationSearchItemUiState(countryName = "대한민국", city = "제주도"),
        LocationSearchItemUiState(countryName = "태국", city = "방콕"),
        LocationSearchItemUiState(countryName = "일본", city = "오사카"),
        LocationSearchItemUiState(countryName = "베트남", city = "다낭")
    )

    val popularTags = listOf(
        "맛집",
        "힐링여행",
        "액티비티",
        "사진스팟",
        "호캉스",
        "야경",
        "이러쿵저러쿵",
        "어쩌구저쩌구",
        "임시데이터임"
    )

    val locationUiState = LocationSearchUiState(
        popularLocations = popularLocations,
        searchedLocations = popularLocations
    )

    val screenUiState = MatchingCreatePostUiState(
        currentStep = MatchingCreatePostStep.LOCATION,
        locationSearchUiState = locationUiState,
        thumbnailAndTagUiState = ThumbnailAndTagUiState(
            regionName = "도쿄",
            countryName = "일본"
        ),
        popularTags = popularTags
    )
}

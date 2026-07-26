package com.gildongmu.dduru.presentation.feature.matching.createPost.screen

import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.CompanionType
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingEditPostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentGenderOption
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import java.time.LocalDate

object MatchingCreatePostMockData {
    val popularLocations = listOf(
        LocationSearchItemUiState(countryName = "일본", city = "도쿄"),
        LocationSearchItemUiState(countryName = "대한민국", city = "제주도"),
        LocationSearchItemUiState(countryName = "태국", city = "방콕"),
        LocationSearchItemUiState(countryName = "일본", city = "오사카"),
        LocationSearchItemUiState(countryName = "베트남", city = "다낭"),
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

object MatchingEditPostMockData {
    val screenUiState = MatchingEditPostUiState(
        dateSectionUiState = DateSectionUiState(
            selectedStartDate = LocalDate.of(2026, 8, 3),
            selectedEndDate = LocalDate.of(2026, 8, 6)
        ),
        recruitmentSectionUiState = RecruitmentScreenUiState(
            selectedCompanionType = CompanionType.FULL
        ),
        ageSectionUiState = AgeSectionUiState(
            selectedGender = RecruitmentGenderOption.ANY,
            isAgeIrrelevant = true
        ),
        thumbnailAndTagUiState = ThumbnailAndTagUiState(
            regionName = "후쿠오카",
            countryName = "일본",
            addedTags = listOf("맛집", "야경")
        ),
        popularTags = MatchingCreatePostMockData.popularTags
    )

    val enabledNextButtonUiState = screenUiState.copy(
        postWritingUiState = PostWritingUiState(
            title = "후쿠오카 여행방",
            content = "맛집과 관광지를 함께 둘러볼 여행 메이트를 찾고 있어요."
        )
    )
}

package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class LocationSearchItemUiState(
    val countryName: String,
    val city: String
)

data class LocationSearchUiState(
    val searchQuery: String = "",
    val selectedLocation: LocationSearchItemUiState? = null,
    val popularLocations: List<LocationSearchItemUiState> = emptyList(),
    val searchedLocations: List<LocationSearchItemUiState> = emptyList()
) {
    val isSearching: Boolean
        get() = searchQuery.isNotBlank()

    val isNextEnabled: Boolean
        get() = selectedLocation != null

    val visibleLocations: List<LocationSearchItemUiState>
        get() = if (isSearching) searchedLocations else popularLocations

    val sectionTitle: String
        get() = if (isSearching) "검색 결과" else "인기 여행지"
}

package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class LocationSearchItemUiState(
    val id: Long = 0L,
    val countryCode: String = "",
    val countryName: String,
    val city: String,
    val image: String? = null
)

data class LocationSearchUiState(
    val searchQuery: String = "",
    val selectedLocation: LocationSearchItemUiState? = null,
    val popularLocations: List<LocationSearchItemUiState> = emptyList(),
    val searchedLocations: List<LocationSearchItemUiState> = emptyList(),
    val popularLocationsErrorMessage: String? = null,
    val isSearchLoading: Boolean = false,
    val hasSearchResult: Boolean = false
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

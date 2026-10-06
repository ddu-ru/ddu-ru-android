package com.gildongmu.dduru.presentation.feature.mypage.uistate

import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState

const val MAX_PREFERRED_DESTINATION_COUNT = 3

data class TravelDestinationSelectUiState(
    val searchQuery: String = "",
    val savedLocations: List<LocationSearchItemUiState> = emptyList(),
    val selectedLocations: List<LocationSearchItemUiState> = emptyList(),
    val popularLocations: List<LocationSearchItemUiState> = emptyList(),
    val searchedLocations: List<LocationSearchItemUiState> = emptyList(),
    val hasSearchResult: Boolean = false
) {
    val isSearching: Boolean
        get() = searchQuery.isNotBlank()

    val isMaxSelected: Boolean
        get() = selectedLocations.size >= MAX_PREFERRED_DESTINATION_COUNT

    // 이전에 저장한 여행지 + 이번에 고른 여행지. 선택된 항목이 위, 선택 해제된 항목이 아래
    val pinnedLocations: List<LocationSearchItemUiState>
        get() = selectedLocations +
            (savedLocations + selectedLocations).distinct().filterNot { it in selectedLocations }

    fun selectionOrderOf(location: LocationSearchItemUiState): Int? {
        val index = selectedLocations.indexOf(location)
        return if (index == -1) null else index + 1
    }

    fun toggle(location: LocationSearchItemUiState): TravelDestinationSelectUiState {
        val isSelected = selectedLocations.contains(location)
        val updated = when {
            isSelected -> selectedLocations - location
            isMaxSelected -> selectedLocations
            else -> selectedLocations + location
        }
        return copy(selectedLocations = updated)
    }

    fun reset(): TravelDestinationSelectUiState =
        copy(selectedLocations = emptyList(), savedLocations = emptyList())
}

package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyDetailFilterType
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailPostUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class JourneyDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(JourneyDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun onFilterClick(filter: JourneyDetailFilterType) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedFilter = filter,
                filteredPosts = currentState.posts.filterBy(filter)
            )
        }
    }

    private fun List<JourneyDetailPostUiState>.filterBy(
        filter: JourneyDetailFilterType
    ) = when (filter) {
        JourneyDetailFilterType.FIXED -> filter { it.isPinned }
        JourneyDetailFilterType.VOTE -> emptyList()
        else -> filter { it.category == filter }
    }
}

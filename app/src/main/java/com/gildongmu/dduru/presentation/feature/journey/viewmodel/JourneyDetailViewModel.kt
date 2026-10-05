package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyDetailFilterType
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
            currentState.copy(selectedFilter = filter)
        }
    }
}

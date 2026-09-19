package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class JourneyAdminViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyAdminUiState())
    val uiState: StateFlow<JourneyAdminUiState> = _uiState.asStateFlow()
}

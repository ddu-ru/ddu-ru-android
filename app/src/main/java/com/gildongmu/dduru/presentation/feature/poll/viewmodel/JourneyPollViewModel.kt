package com.gildongmu.dduru.presentation.feature.poll.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.poll.state.JourneyPollUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class JourneyPollViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyPollUiState())
    val uiState = _uiState.asStateFlow()

    fun onOptionClick(index: Int) {
        if (_uiState.value.hasVoted) return

        _uiState.update {
            it.copy(
                selectedIndex = index
            )
        }
    }

    fun onVoteClick() {
        val state = _uiState.value
        val selectedIndex = state.selectedIndex ?: return

        val counts = List(state.options.size) { index ->
            if (index == selectedIndex) 1 else 0
        }

        _uiState.update {
            it.copy(
                voteCounts = counts
            )
        }
    }

    fun onRevoteClick() {
        _uiState.update {
            it.copy(
                selectedIndex = null,
                voteCounts = null
            )
        }
    }
}

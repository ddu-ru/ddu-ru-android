package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleCategory
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleComposeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class JourneyScheduleComposeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyScheduleComposeUiState())
    val uiState: StateFlow<JourneyScheduleComposeUiState> = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun onLocationChange(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun onDayClick(day: Int) {
        _uiState.update { it.copy(selectedDay = day) }
    }

    fun onStartTimeChange(time: String?) {
        _uiState.update { it.copy(startTime = time) }
    }

    fun onAddEndTimeClick() {
        _uiState.update { it.copy(isEndTimeAdded = true) }
    }

    fun onEndTimeChange(time: String?) {
        _uiState.update { it.copy(endTime = time) }
    }

    fun onMemoOptionClick() {
        _uiState.update { it.copy(isMemoAdded = true) }
    }

    fun onMemoChange(memo: String) {
        _uiState.update { it.copy(memo = memo) }
    }

    fun onRemoveMemoClick() {
        _uiState.update {
            it.copy(
                isMemoAdded = false,
                memo = ""
            )
        }
    }

    fun onCategoryOptionClick() {
        _uiState.update { it.copy(isCategoryAdded = true) }
    }

    fun onCategoryClick(category: JourneyScheduleCategory) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    fun onRemoveCategoryClick() {
        _uiState.update {
            it.copy(
                isCategoryAdded = false,
                selectedCategory = null
            )
        }
    }

    fun onSubmitClick() {
    }
}

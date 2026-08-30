package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class JourneyViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<JourneyUiState>>(UiState.Success(JourneyUiState()))
    val uiState: StateFlow<UiState<JourneyUiState>> = _uiState.asStateFlow()

    fun onTodoCheckedChange(todoId: Long, isCompleted: Boolean) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(
                currentState.data.copy(
                    todos = currentState.data.todos.map { todo ->
                        if (todo.id == todoId) todo.copy(isCompleted = isCompleted) else todo
                    }
                )
            )
        }
    }

    fun onScheduleDayClick(day: Int) {
        val currentState = _uiState.value

        if (currentState is UiState.Success && currentState.data.scheduleDays.any { it.day == day }) {
            _uiState.value = UiState.Success(
                currentState.data.copy(selectedScheduleDay = day)
            )
        }
    }
}

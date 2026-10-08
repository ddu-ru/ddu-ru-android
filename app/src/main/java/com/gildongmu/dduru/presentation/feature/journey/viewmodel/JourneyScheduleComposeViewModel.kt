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
        _uiState.update { state ->
            state.copy(
                startTime = time,
                timeErrorMessage = null,
                endTime = state.endTime?.takeUnless { endTime ->
                    time != null && endTime.toMinutes() <= time.toMinutes()
                }
            )
        }
    }

    fun onAddEndTimeClick() {
        _uiState.update { it.copy(isEndTimeAdded = true) }
    }

    fun onEndTimeChange(time: String?) {
        _uiState.update { state ->
            val isValid = time == null || state.startTime == null || time.toMinutes() > state.startTime.toMinutes()
            state.copy(
                endTime = if (isValid) time else state.endTime,
                timeErrorMessage = if (isValid) null else END_TIME_VALIDATION_MESSAGE
            )
        }
    }

    fun onTimeValidationFailed(message: String) {
        _uiState.update { it.copy(timeErrorMessage = message) }
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

    private fun String.toMinutes(): Int {
        val match = KOREAN_TIME_REGEX.matchEntire(this) ?: return Int.MAX_VALUE
        val hour = match.groupValues[2].toInt()
        val minute = match.groupValues[3].toInt()
        val hour24 = when (match.groupValues[1]) {
            "오전" -> if (hour == 12) 0 else hour
            else -> if (hour == 12) 12 else hour + 12
        }
        return hour24 * MINUTES_PER_HOUR + minute
    }

    private companion object {
        const val MINUTES_PER_HOUR = 60
        const val END_TIME_VALIDATION_MESSAGE = "종료 시간은 시작 시간 이후로 설정해 주세요."
        val KOREAN_TIME_REGEX = Regex("(오전|오후) (\\d{1,2}):(\\d{2})")
    }
}

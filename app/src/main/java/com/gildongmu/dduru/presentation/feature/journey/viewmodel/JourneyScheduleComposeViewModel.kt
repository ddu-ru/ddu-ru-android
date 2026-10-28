package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.domain.journey.entity.JourneyScheduleCommand
import com.gildongmu.dduru.domain.journey.repository.JourneyRepository
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleCategory
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleComposeUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleDay
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyScheduleComposeViewModel @Inject constructor(
    private val journeyRepository: JourneyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyScheduleComposeUiState())
    val uiState: StateFlow<JourneyScheduleComposeUiState> = _uiState.asStateFlow()

    fun loadJourneyDays(journeyId: Long) {
        if (_uiState.value.days.isNotEmpty()) return
        viewModelScope.launch {
            journeyRepository.getJourneyDetail(journeyId).onSuccess { journey ->
                val startDate = LocalDate.parse(journey.startDate)
                val endDate = LocalDate.parse(journey.endDate)
                val formatter = DateTimeFormatter.ofPattern("MM.dd")
                val days = generateSequence(startDate) { date ->
                    date.plusDays(1).takeIf { it <= endDate }
                }.mapIndexed { index, date -> JourneyScheduleDay(index + 1, date.format(formatter)) }.toList()
                _uiState.update { it.copy(days = days.toImmutableList()) }
            }
        }
    }

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

    fun onSubmitClick(journeyId: Long, onSuccess: () -> Unit) {
        val state = _uiState.value
        if (!state.canSubmit || state.isSubmitting) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            journeyRepository.createJourneySchedule(
                journeyId = journeyId,
                command = JourneyScheduleCommand(
                    title = state.title.trim(),
                    category = state.selectedCategory?.toApiValue(),
                    dayOffset = state.selectedDay!! - 1,
                    startTime = state.startTime?.toApiTime(),
                    endTime = state.endTime?.toApiTime(),
                    placeName = state.location.trim().ifBlank { null },
                    memo = state.memo.trim().ifBlank { null }
                )
            ).onSuccess {
                _uiState.update { it.copy(isSubmitting = false) }
                onSuccess()
            }.onFailure {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }

    private fun JourneyScheduleCategory.toApiValue() = when (this) {
        JourneyScheduleCategory.FOOD -> "MEAL"
        JourneyScheduleCategory.CAFE -> "CAFE"
        JourneyScheduleCategory.TOUR -> "SIGHTSEEING"
        JourneyScheduleCategory.ACTIVITY -> "ACTIVITY"
        JourneyScheduleCategory.SHOPPING -> "SHOPPING"
        JourneyScheduleCategory.REST -> "REST"
    }

    private fun String.toApiTime(): String = KOREAN_TIME_REGEX.matchEntire(this)?.let { match ->
        val hour = match.groupValues[2].toInt()
        val minute = match.groupValues[3]
        val hour24 = when (match.groupValues[1]) {
            "오전" -> if (hour == 12) 0 else hour
            else -> if (hour == 12) 12 else hour + 12
        }
        "%02d:%s".format(hour24, minute)
    } ?: this

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

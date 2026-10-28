package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.AvailableDate
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.TravelPreference
import com.gildongmu.dduru.domain.repository.MyPageRepository
import com.gildongmu.dduru.presentation.feature.mypage.model.MAX_PREFERRED_DATE_COUNT
import com.gildongmu.dduru.presentation.feature.mypage.model.PreferredDateRange
import com.gildongmu.dduru.presentation.feature.mypage.model.TravelPreferenceDraft
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileEditPreferencesUiState(
    val destinations: List<DestinationPreference> = emptyList(),
    val dateRanges: List<PreferredDateRange> = emptyList(),
    val initialDestinations: List<DestinationPreference> = emptyList(),
    val initialDateRanges: List<PreferredDateRange> = emptyList(),
    val isSaving: Boolean = false
) {
    val isChanged: Boolean
        get() = destinations != initialDestinations || dateRanges != initialDateRanges

    val isSaveEnabled: Boolean
        get() = isChanged && !isSaving
}

sealed interface ProfileEditPreferencesSideEffect {
    data object NavigateBack : ProfileEditPreferencesSideEffect
    data object NavigateToDestinationSelect : ProfileEditPreferencesSideEffect
    data class ShowMessage(val message: String) : ProfileEditPreferencesSideEffect
}

@HiltViewModel
class ProfileEditPreferencesViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val travelPreferenceDraft: TravelPreferenceDraft
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProfileEditPreferencesUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileEditPreferencesUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileEditPreferencesSideEffect>()
    val sideEffect: SharedFlow<ProfileEditPreferencesSideEffect> = _sideEffect.asSharedFlow()

    init {
        loadPreferences()
        viewModelScope.launch {
            travelPreferenceDraft.destinations.collect { destinations ->
                updateData { it.copy(destinations = destinations) }
            }
        }
    }

    fun loadPreferences() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            myPageRepository.getTravelPreference()
                .onSuccess { preference ->
                    val dateRanges = preference.availableDates.map {
                        PreferredDateRange(startDate = it.startDate, endDate = it.endDate)
                    }
                    travelPreferenceDraft.updateDestinations(preference.destinations)
                    _uiState.value = UiState.Success(
                        ProfileEditPreferencesUiState(
                            destinations = preference.destinations,
                            dateRanges = dateRanges,
                            initialDestinations = preference.destinations,
                            initialDateRanges = dateRanges
                        )
                    )
                }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onBackClick() {
        emit(ProfileEditPreferencesSideEffect.NavigateBack)
    }

    fun onDestinationAddClick() {
        emit(ProfileEditPreferencesSideEffect.NavigateToDestinationSelect)
    }

    fun onDateAddClick() {
        updateData {
            if (it.dateRanges.size >= MAX_PREFERRED_DATE_COUNT) {
                it
            } else {
                it.copy(
                    dateRanges = it.dateRanges + PreferredDateRange()
                )
            }
        }
    }

    fun onDateRemoveClick(index: Int) {
        updateData { state ->
            state.copy(dateRanges = state.dateRanges.filterIndexed { i, _ -> i != index })
        }
    }

    fun onDateSelected(index: Int, date: LocalDate) {
        updateData { state ->
            state.copy(
                dateRanges = state.dateRanges.mapIndexed { i, range ->
                    if (i == index) range.withSelectedDate(date) else range
                }
            )
        }
    }

    fun onConfirmClick() {
        val state = (_uiState.value as? UiState.Success)?.data ?: return
        if (!state.isSaveEnabled) return
        if (state.dateRanges.any { it.startDate != null && !it.isComplete }) {
            emit(ProfileEditPreferencesSideEffect.ShowMessage("여행 가능 날짜의 종료일을 선택해 주세요."))
            return
        }

        viewModelScope.launch {
            updateData { it.copy(isSaving = true) }
            myPageRepository.updateTravelPreference(
                TravelPreference(
                    destinations = state.destinations,
                    availableDates = state.dateRanges
                        .filter { it.isComplete }
                        .map { AvailableDate(it.startDate!!, it.endDate!!) }
                )
            ).onSuccess {
                updateData {
                    it.copy(
                        isSaving = false,
                        initialDestinations = it.destinations,
                        initialDateRanges = it.dateRanges
                    )
                }
                _sideEffect.emit(ProfileEditPreferencesSideEffect.ShowMessage("저장했어요."))
                _sideEffect.emit(ProfileEditPreferencesSideEffect.NavigateBack)
            }.onFailure {
                updateData { it.copy(isSaving = false) }
                _sideEffect.emit(ProfileEditPreferencesSideEffect.ShowMessage("저장에 실패했어요. 다시 시도해 주세요."))
            }
        }
    }

    private fun updateData(transform: (ProfileEditPreferencesUiState) -> ProfileEditPreferencesUiState) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(transform(data))
        }
    }

    private fun emit(effect: ProfileEditPreferencesSideEffect) {
        viewModelScope.launch { _sideEffect.emit(effect) }
    }
}

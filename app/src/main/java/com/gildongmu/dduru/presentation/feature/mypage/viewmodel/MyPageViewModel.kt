package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.SurveyResult
import com.gildongmu.dduru.domain.entity.mypage.AvailableDate
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.repository.MyPageRepository
import com.gildongmu.dduru.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MyPageUiState(
    val nickname: String = "",
    val bio: String? = null,
    val profileImageUrl: String? = null,
    val destinations: List<DestinationPreference> = emptyList(),
    val dateRanges: List<AvailableDate> = emptyList(),
    val travelStyle: SurveyResult? = null
)

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val surveyRepository: SurveyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MyPageUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MyPageUiState>> = _uiState.asStateFlow()

    fun loadMyPage() {
        viewModelScope.launch {
            if (_uiState.value !is UiState.Success) _uiState.value = UiState.Loading

            val profileResult = async { myPageRepository.getMyProfile() }
            val preferenceResult = async { myPageRepository.getTravelPreference() }
            val surveyResult = async { surveyRepository.getMySurveyResult() }

            val profile = profileResult.await().getOrElse {
                if (_uiState.value !is UiState.Success) _uiState.value = UiState.Failure
                return@launch
            }
            val preference = preferenceResult.await().getOrNull()

            _uiState.value = UiState.Success(
                MyPageUiState(
                    nickname = profile.nickname,
                    bio = profile.bio,
                    profileImageUrl = profile.profileImageUrl,
                    destinations = preference?.destinations.orEmpty(),
                    dateRanges = preference?.availableDates.orEmpty(),
                    travelStyle = surveyResult.await().getOrNull()
                )
            )
        }
    }
}

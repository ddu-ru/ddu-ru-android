package com.gildongmu.dduru.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.presentation.feature.home.effect.HomeSideEffect
import com.gildongmu.dduru.presentation.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeUiState>>(UiState.Success(HomeUiState.fake))
    val uiState: StateFlow<UiState<HomeUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffect.asSharedFlow()

    fun onNotificationClick() {
        emitSideEffect(HomeSideEffect.NavigateToNotification)
    }

    fun onMenuClick() {
        emitSideEffect(HomeSideEffect.NavigateToMenu)
    }

    fun onUpcomingTripTaskClick() {
        emitSideEffect(HomeSideEffect.NavigateToUpcomingTripTask)
    }

    fun onRefreshPopularDestinationClick() {
        updateHomeUiState {
            copy(popularDestinationUpdatedText = "방금 전")
        }
    }

    fun onPopularDestinationClick(id: Long) {
        emitSideEffect(HomeSideEffect.NavigateToDestination(id))
    }

    fun onAiMatchingPassClick() {
        updateAiMatchingUsedCount()
    }

    fun onAiMatchingAcceptClick() {
        updateAiMatchingUsedCount()
    }

    fun onSuperHostMoreClick() = Unit

    fun onSuperHostClick(id: Long) {
        emitSideEffect(HomeSideEffect.NavigateToSuperHost(id))
    }

    fun onSuperHostLikeClick(id: Long) {
        updateHomeUiState {
            copy(
                superHosts = superHosts.map { item ->
                    if (item.id == id) item.copy(isLiked = !item.isLiked) else item
                }
            )
        }
    }

    fun onCompanionMoreClick() = Unit

    fun onCompanionClick(id: Long) {
        emitSideEffect(HomeSideEffect.NavigateToCompanion(id))
    }

    fun onCreateTravelRoomClick() {
        emitSideEffect(HomeSideEffect.NavigateToCreateTravelRoom)
    }

    fun onWriteCommunityClick() {
        emitSideEffect(HomeSideEffect.NavigateToWriteCommunity)
    }

    private fun emitSideEffect(sideEffect: HomeSideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

    private fun updateAiMatchingUsedCount() {
        updateHomeUiState {
            val nextUsedCount = aiMatchingUsedCount + 1

            copy(
                aiMatchingUsedCount = nextUsedCount,
                aiMatchingCard = aiMatchingCard.takeIf {
                    nextUsedCount < DAILY_AI_MATCHING_LIMIT
                }
            )
        }
    }

    private fun updateHomeUiState(reducer: HomeUiState.() -> HomeUiState) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(currentState.data.reducer())
        }
    }

    private companion object {
        const val DAILY_AI_MATCHING_LIMIT = 3
    }
}

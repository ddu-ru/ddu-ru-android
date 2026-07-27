package com.gildongmu.dduru.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.HomeMateRecommendations
import com.gildongmu.dduru.domain.repository.HomeRepository
import com.gildongmu.dduru.presentation.feature.home.effect.HomeSideEffect
import com.gildongmu.dduru.presentation.feature.home.mapper.toAiMatchingCardData
import com.gildongmu.dduru.presentation.feature.home.mapper.toTravelCompanionUiModel
import com.gildongmu.dduru.presentation.feature.home.mapper.toUiModel
import com.gildongmu.dduru.presentation.feature.home.mapper.toUpdatedText
import com.gildongmu.dduru.presentation.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffect.asSharedFlow()

    init {
        loadHome()
    }

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
        viewModelScope.launch {
            homeRepository.getHomePopularDestinations()
                .onSuccess { popularDestinations ->
                    updateHomeUiState {
                        copy(
                            popularDestinations = popularDestinations.items
                                .map { it.toUiModel() }
                                .toImmutableList(),
                            popularDestinationUpdatedText = popularDestinations.updatedAt.toUpdatedText()
                        )
                    }
                }
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

    private fun loadHome() {
        viewModelScope.launch {
            val initialConfig = homeRepository.getHomeInitialConfig()
                .getOrElse {
                    _uiState.value = UiState.Failure
                    return@launch
                }

            var nextState = HomeUiState.fake

            if (initialConfig.isSectionEnabled(SECTION_MATE_RECOMMENDATIONS)) {
                homeRepository.getHomeMateRecommendations()
                    .onSuccess { mateRecommendations ->
                        nextState = nextState.applyMateRecommendations(mateRecommendations)
                    }
            } else {
                nextState = nextState.copy(aiMatchingCard = null)
            }

            if (initialConfig.isSectionEnabled(SECTION_POPULAR_DESTINATIONS)) {
                homeRepository.getHomePopularDestinations()
                    .onSuccess { popularDestinations ->
                        nextState = nextState.copy(
                            popularDestinations = popularDestinations.items
                                .map { it.toUiModel() }
                                .toImmutableList(),
                            popularDestinationUpdatedText = popularDestinations.updatedAt.toUpdatedText()
                        )
                    }
            } else {
                nextState = nextState.copy(popularDestinations = persistentListOf())
            }

            if (initialConfig.isSectionEnabled(SECTION_SAME_DESTINATION_TRIPS)) {
                homeRepository.getHomeSameDestinationTrips()
                    .onSuccess { sameDestinationTrips ->
                        nextState = nextState.copy(
                            sameDestinationCompanions = sameDestinationTrips
                                .map { it.toTravelCompanionUiModel() }
                        )
                    }
            } else {
                nextState = nextState.copy(sameDestinationCompanions = emptyList())
            }

            _uiState.value = UiState.Success(nextState)
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

    private fun HomeInitialConfig.isSectionEnabled(key: String): Boolean {
        return sections.firstOrNull { it.key == key }?.enabled ?: true
    }

    private fun HomeUiState.applyMateRecommendations(
        mateRecommendations: HomeMateRecommendations
    ): HomeUiState {
        val usedCount = if (mateRecommendations.isAvailable) {
            DAILY_AI_MATCHING_LIMIT - mateRecommendations.remainingFreeCount
        } else {
            DAILY_AI_MATCHING_LIMIT
        }

        return copy(
            aiMatchingCard = mateRecommendations
                .recommendations
                .firstOrNull()
                ?.toAiMatchingCardData()
                .takeIf { mateRecommendations.isAvailable },
            aiMatchingUsedCount = usedCount.coerceIn(0, DAILY_AI_MATCHING_LIMIT)
        )
    }

    private companion object {
        const val DAILY_AI_MATCHING_LIMIT = 3
        const val SECTION_MATE_RECOMMENDATIONS = "MATE_RECOMMENDATIONS"
        const val SECTION_POPULAR_DESTINATIONS = "POPULAR_DESTINATIONS"
        const val SECTION_SAME_DESTINATION_TRIPS = "SAME_DESTINATION_TRIPS"
    }
}

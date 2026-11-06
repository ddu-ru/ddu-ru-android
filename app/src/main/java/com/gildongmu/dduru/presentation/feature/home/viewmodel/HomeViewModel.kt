package com.gildongmu.dduru.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.domain.repository.home.HomeRepository
import com.gildongmu.dduru.presentation.feature.home.DAILY_AI_MATCHING_LIMIT
import com.gildongmu.dduru.presentation.feature.home.effect.HomeSideEffect
import com.gildongmu.dduru.presentation.feature.home.loader.HomeDataLoader
import com.gildongmu.dduru.presentation.feature.home.mapper.toUiModel
import com.gildongmu.dduru.presentation.feature.home.mapper.toUpdatedText
import com.gildongmu.dduru.presentation.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val postService: PostService,
    private val homeDataLoader: HomeDataLoader
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffect.asSharedFlow()

    init {
        loadHome()
        startPopularDestinationTimestampUpdates()
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
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.isPopularDestinationsRefreshing) return

        updateHomeUiState {
            copy(
                isPopularDestinationsRefreshing = true,
                popularDestinationsRefreshFailed = false
            )
        }

        viewModelScope.launch {
            homeRepository.getHomePopularDestinations()
                .onSuccess { popularDestinations ->
                    updateHomeUiState {
                        copy(
                            popularDestinations = popularDestinations.items
                                .map { it.toUiModel() }
                                .toImmutableList(),
                            popularDestinationUpdatedAt = popularDestinations.updatedAt,
                            popularDestinationUpdatedText = popularDestinations.updatedAt.toUpdatedText(),
                            isPopularDestinationsRefreshing = false,
                            popularDestinationsRefreshFailed = false
                        )
                    }
                }
                .onFailure {
                    updateHomeUiState {
                        copy(
                            isPopularDestinationsRefreshing = false,
                            popularDestinationsRefreshFailed = true
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
        val postId = (_uiState.value as? UiState.Success)
            ?.data
            ?.aiMatchingCard
            ?.postId
            ?: return
        emitSideEffect(HomeSideEffect.NavigateToMatchingDetail(postId))
    }

    fun onSuperHostMoreClick() {
        emitSideEffect(HomeSideEffect.NavigateToMatching)
    }

    fun onSuperHostClick(id: Long) {
        emitSideEffect(HomeSideEffect.NavigateToMatchingDetail(id))
    }

    fun onSuperHostLikeClick(id: Long) {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (id in currentState.pendingSuperHostLikeIds) return
        val item = currentState.superHosts.firstOrNull { it.id == id } ?: return

        updateHomeUiState {
            copy(
                superHosts = superHosts.map { item ->
                    if (item.id == id) item.copy(isLiked = !item.isLiked) else item
                },
                pendingSuperHostLikeIds = pendingSuperHostLikeIds + id
            )
        }

        viewModelScope.launch {
            val succeeded = runCatching { postService.toggleLike(id).isSuccessful }
                .getOrDefault(false)
            updateHomeUiState {
                copy(
                    superHosts = if (succeeded) superHosts else superHosts.map { current ->
                        if (current.id == id) current.copy(isLiked = item.isLiked) else current
                    },
                    pendingSuperHostLikeIds = pendingSuperHostLikeIds - id
                )
            }
        }
    }

    fun onCompanionMoreClick() {
        emitSideEffect(HomeSideEffect.NavigateToMatching)
    }

    fun onCompanionClick(id: Long) {
        emitSideEffect(HomeSideEffect.NavigateToMatchingDetail(id))
    }

    fun onCreateTravelRoomClick() {
        emitSideEffect(HomeSideEffect.NavigateToCreateTravelRoom)
    }

    fun onTravelStyleSurveyClick() {
        emitSideEffect(HomeSideEffect.NavigateToTravelStyleSurvey)
    }

    fun onTravelPreferencesClick() {
        emitSideEffect(HomeSideEffect.NavigateToTravelPreferences)
    }

    fun onRetrySurveyStateClick() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.isSurveyStateRefreshing) return

        updateHomeUiState { copy(isSurveyStateRefreshing = true) }
        viewModelScope.launch {
            val surveyState = homeDataLoader.loadSurveyState()
            updateHomeUiState {
                copy(
                    surveyState = surveyState,
                    isSurveyStateRefreshing = false
                )
            }
        }
    }

    fun onRetryDestinationPreferenceClick() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.isDestinationPreferenceRefreshing) return

        updateHomeUiState { copy(isDestinationPreferenceRefreshing = true) }
        viewModelScope.launch {
            val preferenceState = homeDataLoader.loadDestinationPreferenceState()
            updateHomeUiState {
                copy(
                    destinationPreferenceState = preferenceState,
                    isDestinationPreferenceRefreshing = false
                )
            }
        }
    }

    private fun emitSideEffect(sideEffect: HomeSideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

    private fun loadHome() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            homeDataLoader()
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Failure }
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

    private fun startPopularDestinationTimestampUpdates() {
        viewModelScope.launch {
            while (true) {
                delay(POPULAR_DESTINATION_TIMESTAMP_REFRESH_MILLIS)
                updateHomeUiState {
                    val updatedAt = popularDestinationUpdatedAt ?: return@updateHomeUiState this
                    copy(popularDestinationUpdatedText = updatedAt.toUpdatedText())
                }
            }
        }
    }

    private fun updateHomeUiState(reducer: HomeUiState.() -> HomeUiState) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(currentState.data.reducer())
        }
    }

    private companion object {
        const val POPULAR_DESTINATION_TIMESTAMP_REFRESH_MILLIS = 60_000L
    }
}

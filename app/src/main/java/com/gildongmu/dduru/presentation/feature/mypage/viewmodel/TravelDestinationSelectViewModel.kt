package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
import com.gildongmu.dduru.domain.repository.MyPageRepository
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.mypage.model.TravelPreferenceDraft
import com.gildongmu.dduru.presentation.feature.mypage.model.toDestinationPreference
import com.gildongmu.dduru.presentation.feature.mypage.model.toLocationItem
import com.gildongmu.dduru.presentation.feature.mypage.uistate.TravelDestinationSelectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SEARCH_DEBOUNCE_MILLIS = 300L

sealed interface TravelDestinationSelectSideEffect {
    data object Close : TravelDestinationSelectSideEffect
    data class ShowMessage(val message: String) : TravelDestinationSelectSideEffect
}

@OptIn(FlowPreview::class)
@HiltViewModel
class TravelDestinationSelectViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val destinationRepository: DestinationRepository,
    private val travelPreferenceDraft: TravelPreferenceDraft
) : ViewModel() {

    private val savedLocations = travelPreferenceDraft.destinations.value.map { it.toLocationItem() }

    private val _uiState = MutableStateFlow(
        TravelDestinationSelectUiState(
            savedLocations = savedLocations,
            selectedLocations = savedLocations
        )
    )
    val uiState: StateFlow<TravelDestinationSelectUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<TravelDestinationSelectSideEffect>()
    val sideEffect: SharedFlow<TravelDestinationSelectSideEffect> = _sideEffect.asSharedFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        loadPopularLocations()
        viewModelScope.launch {
            searchQuery
                .debounce(SEARCH_DEBOUNCE_MILLIS)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest { search(it.trim()) }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query, hasSearchResult = false, searchedLocations = emptyList()) }
        searchQuery.value = query
    }

    fun onLocationClick(location: LocationSearchItemUiState) {
        val state = _uiState.value
        if (state.isMaxSelected && location !in state.selectedLocations) {
            viewModelScope.launch {
                _sideEffect.emit(TravelDestinationSelectSideEffect.ShowMessage("최대 3개까지 선택할 수 있어요."))
            }
            return
        }
        _uiState.update { it.toggle(location) }
    }

    fun onResetClick() {
        _uiState.update { it.reset() }
    }

    fun onConfirmClick() {
        travelPreferenceDraft.updateDestinations(_uiState.value.selectedLocations.map { it.toDestinationPreference() })
        viewModelScope.launch { _sideEffect.emit(TravelDestinationSelectSideEffect.Close) }
    }

    fun onCloseClick() {
        viewModelScope.launch { _sideEffect.emit(TravelDestinationSelectSideEffect.Close) }
    }

    private fun loadPopularLocations() {
        viewModelScope.launch {
            destinationRepository.getPopularDestinations().onSuccess { destinations ->
                _uiState.update { state ->
                    state.copy(
                        popularLocations = destinations.map {
                            LocationSearchItemUiState(
                                id = it.id,
                                countryCode = it.countryCode,
                                countryName = it.countryName,
                                city = it.city,
                                image = it.image
                            ).matchedIn(state.pinnedLocations)
                        }
                    )
                }
            }
        }
    }

    private suspend fun search(keyword: String) {
        myPageRepository.searchPreferenceDestinations(keyword)
            .onSuccess { results ->
                _uiState.update { state ->
                    state.copy(
                        searchedLocations = results.map { it.toLocationItem().matchedIn(state.pinnedLocations) },
                        hasSearchResult = true
                    )
                }
            }
            .onFailure {
                _uiState.update { it.copy(searchedLocations = emptyList(), hasSearchResult = true) }
            }
    }

    private fun LocationSearchItemUiState.matchedIn(
        candidates: List<LocationSearchItemUiState>
    ): LocationSearchItemUiState = candidates.firstOrNull {
        it.id == id && it.countryCode == countryCode && it.city == city
    } ?: this
}

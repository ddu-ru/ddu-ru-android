package com.gildongmu.dduru.presentation.feature.matching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.createPost.entity.PopularDestination
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
import com.gildongmu.dduru.domain.repository.MatchingPostRepository
import com.gildongmu.dduru.presentation.feature.matching.effect.MatchingSideEffect
import com.gildongmu.dduru.presentation.feature.matching.mapper.toUiModel
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingDestinationUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val matchingPostRepository: MatchingPostRepository,
    private val destinationRepository: DestinationRepository
) : ViewModel() {
    private companion object {
        const val DESTINATION_SEARCH_DEBOUNCE_MILLIS = 500L
    }

    private val _uiState = MutableStateFlow<UiState<MatchingUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchingSideEffect>()
    val sideEffect: SharedFlow<MatchingSideEffect> = _sideEffect.asSharedFlow()
    private var destinationSearchDebounceJob: Job? = null
    private var destinationSearchRequestJob: Job? = null

    init {
        fetchPosts()
    }

    fun onNotificationClick() {
        emitSideEffect(MatchingSideEffect.NavigateToNotification)
    }

    fun onMenuClick() {
        emitSideEffect(MatchingSideEffect.NavigateToMenu)
    }

    fun onSearchQueryChange(query: String) {
        updateMatchingUiState {
            copy(
                searchQuery = query,
                selectedDestinationId = null
            )
        }

        debounceSearchDestinations(query)
    }

    fun onSearchFieldClick() {
        updateMatchingUiState {
            copy(isSearchScreenVisible = true)
        }
        searchDestinations(currentMatchingUiState().searchQuery)
    }

    fun onSearchBackClick() {
        updateMatchingUiState {
            copy(isSearchScreenVisible = false)
        }
    }

    fun onSearchKeywordClick(keyword: String) {
        val trimmedKeyword = keyword.trim()
        if (trimmedKeyword.isBlank()) {
            updateMatchingUiState {
                copy(
                    searchQuery = "",
                    selectedDestinationId = null
                )
            }
            searchDestinations("")
            return
        }

        updateMatchingUiState {
            copy(
                searchQuery = trimmedKeyword,
                selectedDestinationId = null,
                recentSearchKeywords = listOf(trimmedKeyword)
                    .plus(recentSearchKeywords.filterNot { it == trimmedKeyword })
                    .take(5)
                    .toPersistentList()
            )
        }
        searchDestinations(trimmedKeyword)
    }

    fun onDestinationClick(destination: MatchingDestinationUiModel) {
        destinationSearchDebounceJob?.cancel()
        destinationSearchRequestJob?.cancel()
        updateMatchingUiState {
            copy(
                searchQuery = destination.city,
                selectedDestinationId = destination.id,
                isSearchScreenVisible = false,
                recentSearchKeywords = listOf(destination.city)
                    .plus(recentSearchKeywords.filterNot { it == destination.city })
                    .take(5)
                    .toPersistentList()
            )
        }
        fetchPosts(destinationId = destination.id)
    }

    fun onRecentSearchKeywordRemove(keyword: String) {
        updateMatchingUiState {
            copy(
                recentSearchKeywords = recentSearchKeywords
                    .filterNot { it == keyword }
                    .toPersistentList()
            )
        }
    }

    fun onSortSelected(sort: String) {
        if ((_uiState.value as? UiState.Success)?.data?.selectedSort == sort) return

        updateMatchingUiState {
            copy(selectedSort = sort)
        }
        fetchPosts(
            sort = sort,
            destinationId = currentMatchingUiState().selectedDestinationId
        )
    }

    fun onPostClick(id: Long) {
        emitSideEffect(MatchingSideEffect.NavigateToPostDetail(id))
    }

    fun onPostLikeClick(id: Long) {
        updateMatchingUiState {
            copy(
                posts = posts.map { post ->
                    if (post.id == id) post.copy(isLiked = !post.isLiked) else post
                }.toPersistentList()
            )
        }
    }

    fun onWriteClick() {
        emitSideEffect(MatchingSideEffect.NavigateToCreatePost)
    }

    private fun emitSideEffect(sideEffect: MatchingSideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

    private fun fetchPosts(
        sort: String = currentMatchingUiState().selectedSort,
        destinationId: Long? = currentMatchingUiState().selectedDestinationId
    ) {
        viewModelScope.launch {
            val baseState = currentMatchingUiState()
            val result = matchingPostRepository.getPosts(
                destinationId = destinationId,
                sort = sort.toSortCode()
            )

            result
                .onSuccess { postList ->
                    _uiState.value = UiState.Success(
                        baseState.copy(
                            posts = postList.posts
                                .map { it.toUiModel() }
                                .toPersistentList()
                        )
                    )
                }
                .onFailure {
                    _uiState.value = UiState.Failure
                }
        }
    }

    private fun updateMatchingUiState(reducer: MatchingUiState.() -> MatchingUiState) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(currentState.data.reducer())
        }
    }

    private fun currentMatchingUiState(): MatchingUiState {
        return (_uiState.value as? UiState.Success)?.data ?: MatchingUiState()
    }

    private fun searchDestinations(keyword: String) {
        destinationSearchRequestJob?.cancel()
        destinationSearchRequestJob = viewModelScope.launch {
            updateMatchingUiState {
                copy(
                    isDestinationSearchLoading = true,
                    hasDestinationSearchResult = false
                )
            }

            destinationRepository.searchDestinations(keyword.trim())
                .onSuccess { destinations ->
                    updateMatchingUiState {
                        copy(
                            destinationSearchResults = destinations
                                .map { it.toUiModel() }
                                .toPersistentList(),
                            isDestinationSearchLoading = false,
                            hasDestinationSearchResult = true
                        )
                    }
                }
                .onFailure {
                    updateMatchingUiState {
                        copy(
                            destinationSearchResults =
                            emptyList<MatchingDestinationUiModel>().toPersistentList(),
                            isDestinationSearchLoading = false,
                            hasDestinationSearchResult = true
                        )
                    }
                }
        }
    }

    private fun debounceSearchDestinations(keyword: String) {
        destinationSearchDebounceJob?.cancel()
        destinationSearchDebounceJob = viewModelScope.launch {
            delay(DESTINATION_SEARCH_DEBOUNCE_MILLIS)
            searchDestinations(keyword)
        }
    }

    private fun String.toSortCode(): String {
        return when (this) {
            "조회순" -> "VIEW"
            "좋아요순" -> "LIKE"
            else -> "LATEST"
        }
    }
}

private fun PopularDestination.toUiModel(): MatchingDestinationUiModel {
    return MatchingDestinationUiModel(
        id = id,
        countryCode = countryCode,
        countryName = countryName,
        city = city,
        image = image
    )
}

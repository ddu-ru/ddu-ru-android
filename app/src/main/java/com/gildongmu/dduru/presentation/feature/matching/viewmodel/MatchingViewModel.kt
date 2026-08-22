package com.gildongmu.dduru.presentation.feature.matching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.repository.MatchingPostRepository
import com.gildongmu.dduru.presentation.feature.matching.effect.MatchingSideEffect
import com.gildongmu.dduru.presentation.feature.matching.mapper.toUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val matchingPostRepository: MatchingPostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchingSideEffect>()
    val sideEffect: SharedFlow<MatchingSideEffect> = _sideEffect.asSharedFlow()

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
            copy(searchQuery = query)
        }
    }

    fun onSearchFieldClick() {
        updateMatchingUiState {
            copy(isSearchScreenVisible = true)
        }
    }

    fun onSearchBackClick() {
        updateMatchingUiState {
            copy(isSearchScreenVisible = false)
        }
    }

    fun onSearchKeywordClick(keyword: String) {
        val trimmedKeyword = keyword.trim()
        if (trimmedKeyword.isBlank()) return

        updateMatchingUiState {
            copy(
                searchQuery = trimmedKeyword,
                isSearchScreenVisible = false,
                recentSearchKeywords = listOf(trimmedKeyword)
                    .plus(recentSearchKeywords.filterNot { it == trimmedKeyword })
                    .take(5)
                    .toPersistentList()
            )
        }
        fetchPosts(keyword = trimmedKeyword)
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
        fetchPosts(sort = sort)
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
        keyword: String? = currentMatchingUiState().searchQuery.takeIf { it.isNotBlank() },
        sort: String = currentMatchingUiState().selectedSort
    ) {
        viewModelScope.launch {
            val baseState = currentMatchingUiState()
            val result = matchingPostRepository.getPosts(
                keyword = keyword,
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

    private fun String.toSortCode(): String {
        return when (this) {
            "조회순" -> "VIEW"
            "좋아요순" -> "LIKE"
            else -> "LATEST"
        }
    }
}

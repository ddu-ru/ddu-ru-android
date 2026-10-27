// ktlint-disable max-line-length
package com.gildongmu.dduru.presentation.feature.matching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.dto.response.post.PostSummaryResponse
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.presentation.feature.matching.effect.MatchingSideEffect
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingFilterOptions
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
    private val postService: PostService
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchingSideEffect>()
    val sideEffect: SharedFlow<MatchingSideEffect> = _sideEffect.asSharedFlow()
    private var searchJob: Job? = null

    init { loadPosts() }

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
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            loadPosts(showLoading = false)
        }
    }

    fun onSortSelected(sort: String) {
        if ((_uiState.value as? UiState.Success)?.data?.selectedSort == sort) return

        updateMatchingUiState {
            copy(selectedSort = sort)
        }
        loadPosts()
    }

    fun onFiltersApplied(filters: MatchingFilterOptions) {
        updateMatchingUiState { copy(filters = filters) }
        loadPosts()
    }

    fun loadMorePosts() {
        val state = (_uiState.value as? UiState.Success)?.data ?: return
        if (!state.hasNext || state.isLoadingMore) return
        loadPosts(append = true)
    }

    fun onPostClick(id: Long) {
        emitSideEffect(MatchingSideEffect.NavigateToPostDetail(id))
    }

    fun onPostLikeClick(id: Long) {
        viewModelScope.launch {
            runCatching { postService.toggleLike(id) }
                .onSuccess { response ->
                    if (response.isSuccessful) loadPosts(showLoading = false)
                }
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

    private fun updateMatchingUiState(reducer: MatchingUiState.() -> MatchingUiState) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(currentState.data.reducer())
        }
    }

    private fun loadPosts(append: Boolean = false, showLoading: Boolean = true) {
        viewModelScope.launch {
            val current = (_uiState.value as? UiState.Success)?.data ?: MatchingUiState()
            if (append) {
                _uiState.value = UiState.Success(current.copy(isLoadingMore = true))
            } else if (showLoading) {
                _uiState.value = UiState.Loading
            }
            runCatching {
                postService.getPosts(
                    cursor = if (append) current.nextCursor else null,
                    cursorValue = if (append) current.nextCursorValue else null,
                    keyword = current.searchQuery.takeIf { it.isNotBlank() },
                    startDate = current.filters.startDate,
                    endDate = current.filters.endDate,
                    preferredGender = current.filters.preferredGender,
                    minAge = current.filters.minAge,
                    maxAge = current.filters.maxAge,
                    minRecruitCapacity = current.filters.minRecruitCapacity,
                    maxRecruitCapacity = current.filters.maxRecruitCapacity,
                    recruitmentStatus = current.filters.recruitmentStatus,
                    companionType = current.filters.companionType,
                    sort = current.selectedSort.toSortCode()
                )
            }.onSuccess { response ->
                val data = response.data ?: error("게시글 목록 응답이 비어있어요.")
                _uiState.value = UiState.Success(
                    current.copy(
                        posts = (if (append) current.posts + data.posts.map { it.toUiModel() } else data.posts.map { it.toUiModel() }).toPersistentList(),
                        nextCursor = data.nextCursor,
                        nextCursorValue = data.nextCursorValue,
                        hasNext = data.hasNext,
                        isLoadingMore = false
                    )
                )
            }.onFailure { _uiState.value = UiState.Failure }
        }
    }

    private fun String.toSortCode() = when (this) { "조회순" -> "VIEW"; "좋아요순" -> "LIKE"; else -> "LATEST" }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
    }

    private fun PostSummaryResponse.toUiModel() = com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel(
        id = id, title = title, authorName = author.nickname, location = destination,
        dateRange = "$startDate - $endDate", currentMemberCount = recruitCount, maxMemberCount = recruitCapacity,
        gender = when (preferredGender) { "M" -> com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender.MALE; "F" -> com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender.FEMALE; else -> com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender.ANY },
        companionType = when (companionType) { "PARTIAL" -> com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType.PARTIAL; "MEAL" -> com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType.MEAL; else -> com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType.FULL },
        isSuperHost = author.isSuperHost, isLiked = hasLiked, thumbnailImageRes = com.gildongmu.dduru.R.drawable.fukuoka
    )
}

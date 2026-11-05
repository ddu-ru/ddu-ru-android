package com.gildongmu.dduru.presentation.feature.matching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.dto.response.post.PostSummaryResponse
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.domain.createPost.entity.PopularDestination
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
import com.gildongmu.dduru.presentation.feature.matching.effect.MatchingSideEffect
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingDestinationOption
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingFilterOptions
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CancellationException
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
    private val postService: PostService,
    private val destinationRepository: DestinationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchingSideEffect>()
    val sideEffect: SharedFlow<MatchingSideEffect> = _sideEffect.asSharedFlow()
    private var searchJob: Job? = null
    private var postLoadJob: Job? = null
    private val likeRequestsInFlight = mutableSetOf<Long>()

    init {
        loadPosts()
        loadDestinationOptions()
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
        searchJob?.cancel()
        postLoadJob?.cancel()
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
        if (!likeRequestsInFlight.add(id)) return
        val previousItem = (_uiState.value as? UiState.Success)?.data?.posts
            ?.firstOrNull { it.id == id } ?: run {
            likeRequestsInFlight.remove(id)
            return
        }
        updateMatchingUiState {
            copy(
                posts = posts.map { post ->
                    if (post.id == id) post.copy(isLiked = !post.isLiked) else post
                }.toPersistentList()
            )
        }
        viewModelScope.launch {
            runCatching { postService.toggleLike(id) }
                .onFailure {
                    updateMatchingUiState {
                        copy(
                            posts = posts.map { post ->
                                if (post.id == id) post.copy(isLiked = previousItem.isLiked) else post
                            }.toPersistentList()
                        )
                    }
                }
                .onSuccess { response ->
                    if (!response.isSuccessful) {
                        updateMatchingUiState {
                            copy(
                                posts = posts.map { post ->
                                    if (post.id == id) post.copy(isLiked = previousItem.isLiked) else post
                                }.toPersistentList()
                            )
                        }
                    }
                }
                .also { likeRequestsInFlight.remove(id) }
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
        postLoadJob?.cancel()
        postLoadJob = viewModelScope.launch {
            val current = (_uiState.value as? UiState.Success)?.data ?: MatchingUiState()
            if (append) {
                _uiState.value = UiState.Success(current.copy(isLoadingMore = true))
            } else if (showLoading) {
                _uiState.value = UiState.Loading
            }
            try {
                val response = postService.getPosts(
                    cursor = if (append) current.nextCursor else null,
                    cursorValue = if (append) current.nextCursorValue else null,
                    keyword = current.searchQuery.takeIf { it.isNotBlank() },
                    startDate = current.filters.startDate,
                    endDate = current.filters.endDate,
                    preferredGender = current.filters.preferredGender,
                    minAge = current.filters.minAge,
                    maxAge = current.filters.maxAge,
                    destinationId = current.filters.destinationId,
                    minRecruitCapacity = current.filters.minRecruitCapacity,
                    maxRecruitCapacity = current.filters.maxRecruitCapacity,
                    recruitmentStatus = current.filters.recruitmentStatus,
                    companionType = current.filters.companionType,
                    sort = current.selectedSort.toSortCode()
                )
                val data = response.data ?: error("게시글 목록 응답이 비어있어요.")
                val latest = (_uiState.value as? UiState.Success)?.data ?: current
                val latestPostsById = latest.posts.associateBy { it.id }
                val responsePosts = data.posts.map { postResponse ->
                    val post = postResponse.toUiModel()
                    latestPostsById[post.id]
                        ?.let { post.copy(isLiked = it.isLiked) }
                        ?: post
                }
                _uiState.value = UiState.Success(
                    latest.copy(
                        posts = (if (append) latest.posts + responsePosts else responsePosts).toPersistentList(),
                        nextCursor = data.nextCursor,
                        nextCursorValue = data.nextCursorValue,
                        hasNext = data.hasNext,
                        isLoadingMore = false
                    )
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                if (showLoading || append) _uiState.value = UiState.Failure
            }
        }
    }

    private fun String.toSortCode() = when (this) { "조회순" -> "VIEW"; "좋아요순" -> "LIKE"; else -> "LATEST" }

    private fun loadDestinationOptions() {
        viewModelScope.launch {
            destinationRepository.getPopularDestinations()
                .onSuccess { destinations ->
                    updateMatchingUiState {
                        copy(destinationOptions = destinations.map { it.toFilterOption() })
                    }
                }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
    }

    private fun PopularDestination.toFilterOption() = MatchingDestinationOption(
        id = id,
        name = "$countryName $city"
    )

    private fun PostSummaryResponse.toUiModel() =
        MatchingPostCardUiModel(
            id = id,
            title = title,
            authorName = author.nickname,
            location = destination,
            dateRange = compactDateRange(startDate, endDate),
            currentMemberCount = recruitCount,
            maxMemberCount = recruitCapacity,
            gender = when (preferredGender) {
                "M" -> MatchingGender.MALE
                "F" -> MatchingGender.FEMALE
                else -> MatchingGender.ANY
            },
            companionType = when (companionType) {
                "PARTIAL" -> MatchingCompanionType.PARTIAL
                "MEAL" -> MatchingCompanionType.MEAL
                else -> MatchingCompanionType.FULL
            },
            isSuperHost = author.isSuperHost,
            isLiked = hasLiked,
            thumbnailImageUrl = photoUrl,
            thumbnailImageRes = com.gildongmu.dduru.R.drawable.fukuoka
        )

    private fun compactDateRange(startDate: String, endDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("MM.dd")
        val start = startDate.toLocalDateOrNull()
        val end = endDate.toLocalDateOrNull()
        return when {
            start == null || end == null -> "$startDate - $endDate"
            start == end -> start.format(formatter)
            else -> "${start.format(formatter)} - ${end.format(formatter)}"
        }
    }

    private fun String.toLocalDateOrNull(): LocalDate? = runCatching { LocalDate.parse(this) }.getOrNull()
}

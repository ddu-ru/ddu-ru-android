package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.journey.entity.JourneyDetail
import com.gildongmu.dduru.domain.journey.entity.JourneyMember
import com.gildongmu.dduru.domain.journey.entity.JourneyPinnedNotice
import com.gildongmu.dduru.domain.journey.entity.JourneyPost
import com.gildongmu.dduru.domain.journey.repository.JourneyPostRepository
import com.gildongmu.dduru.domain.journey.repository.JourneyRepository
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailFilterType
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyMemberUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleDayUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleItemUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailPostUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyDetailViewModel @Inject constructor(
    private val journeyRepository: JourneyRepository,
    private val journeyPostRepository: JourneyPostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<JourneyDetailUiState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var loadedJourneyId: Long? = null

    fun refreshJourneyDetail(journeyId: Long) {
        loadedJourneyId = null
        loadJourneyDetail(journeyId)
    }

    fun loadJourneyDetail(
        journeyId: Long,
        initialFilter: JourneyDetailFilterType? = null
    ) {
        if (loadedJourneyId == journeyId && _uiState.value is UiState.Success) {
            initialFilter?.let(::onFilterClick)
            return
        }

        loadedJourneyId = journeyId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            journeyRepository.getJourneyDetail(journeyId).fold(
                onSuccess = { detail ->
                    Log.d(
                        TAG,
                        "여정 상세 조회 성공: journeyId=${detail.journeyId}, " +
                            "멤버 ${detail.members.size}명, 공지 ${detail.pinnedNotices.size}개"
                    )
                    loadJourneyPosts(detail, initialFilter)
                },
                onFailure = { throwable ->
                    Log.e(TAG, "여정 상세 조회 실패: journeyId=$journeyId", throwable)
                    _uiState.value = UiState.Failure
                }
            )
        }
    }

    private suspend fun loadJourneyPosts(
        detail: JourneyDetail,
        initialFilter: JourneyDetailFilterType?
    ) {
        journeyPostRepository.getJourneyPosts(detail.journeyId).fold(
            onSuccess = { page ->
                val scheduleList = journeyRepository.getJourneySchedules(detail.journeyId).getOrNull()
                val schedulesByDay = scheduleList?.days.orEmpty().associate { day ->
                    day.day to day.schedules.map {
                        JourneyScheduleItemUiState(
                            startTime = it.startTime?.take(5),
                            endTime = it.endTime?.take(5),
                            title = it.title,
                            location = it.placeName,
                            memo = it.memo
                        )
                    }
                }
                _uiState.value = UiState.Success(
                    detail.toUiState(
                        posts = page.posts.map(JourneyPost::toUiState),
                        nextCursor = page.nextCursor,
                        hasNext = page.hasNext,
                        selectedFilter = initialFilter ?: JourneyDetailFilterType.FIXED,
                        scheduleDays = scheduleList?.days.orEmpty().map {
                            JourneyScheduleDayUiState(it.day, it.date.split("-").takeLast(2).joinToString("."))
                        },
                        schedulesByDay = schedulesByDay
                    )
                )
            },
            onFailure = { throwable ->
                Log.e(TAG, "여정 게시글 목록 조회 실패: journeyId=${detail.journeyId}", throwable)
                _uiState.value = UiState.Success(detail.toUiState(selectedFilter = initialFilter))
            }
        )
    }

    fun loadMoreJourneyPosts() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        val cursor = currentState.nextCursor ?: return
        if (!currentState.hasNext || currentState.isLoadingMore) return

        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(data.copy(isLoadingMore = true))
        }

        val journeyId = loadedJourneyId ?: return
        viewModelScope.launch {
            journeyPostRepository.getJourneyPosts(journeyId, cursor).fold(
                onSuccess = { page ->
                    _uiState.update { state ->
                        val data = (state as? UiState.Success)?.data ?: return@update state
                        val posts = (data.posts + page.posts.map(JourneyPost::toUiState))
                            .distinctBy(JourneyDetailPostUiState::id)
                        UiState.Success(
                            data.copy(
                                posts = posts,
                                filteredPosts = posts.filterBy(data.selectedFilter),
                                nextCursor = page.nextCursor,
                                hasNext = page.hasNext,
                                isLoadingMore = false
                            )
                        )
                    }
                },
                onFailure = { throwable ->
                    Log.e(TAG, "여정 게시글 추가 조회 실패: journeyId=$journeyId", throwable)
                    _uiState.update { state ->
                        val data = (state as? UiState.Success)?.data ?: return@update state
                        UiState.Success(data.copy(isLoadingMore = false))
                    }
                }
            )
        }
    }

    fun onFilterClick(filter: JourneyDetailFilterType) {
        _uiState.update { state ->
            val currentState = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(
                currentState.copy(
                    selectedFilter = filter,
                    filteredPosts = currentState.posts.filterBy(filter)
                )
            )
        }
    }

    fun onScheduleDayClick(day: Int) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(data.copy(selectedScheduleDay = day))
        }
    }

    fun onPostPinClick(journeyPostId: Long) {
        val journeyId = loadedJourneyId ?: return
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        val post = currentState.posts.find { it.id == journeyPostId } ?: return

        viewModelScope.launch {
            journeyPostRepository.updateJourneyPostNotice(
                journeyId = journeyId,
                journeyPostId = journeyPostId,
                isNotice = !post.isPinned
            ).fold(
                onSuccess = { notice ->
                    _uiState.update { state ->
                        val data = (state as? UiState.Success)?.data ?: return@update state
                        val posts = data.posts.map {
                            if (it.id == journeyPostId) it.copy(isPinned = notice.isNotice) else it
                        }
                        UiState.Success(
                            data.copy(
                                posts = posts,
                                filteredPosts = posts.filterBy(data.selectedFilter)
                            )
                        )
                    }
                },
                onFailure = { throwable ->
                    Log.e(TAG, "게시글 고정 변경 실패: journeyId=$journeyId, postId=$journeyPostId", throwable)
                }
            )
        }
    }

    private fun List<JourneyDetailPostUiState>.filterBy(
        filter: JourneyDetailFilterType
    ) = when (filter) {
        JourneyDetailFilterType.FIXED -> filter { it.isPinned }
        JourneyDetailFilterType.VOTE -> emptyList()
        else -> filter { it.category == filter }
    }

    private companion object {
        const val TAG = "JourneyDetailViewModel"
    }
}

private fun JourneyDetail.toUiState(
    posts: List<JourneyDetailPostUiState> = pinnedNotices.map(JourneyPinnedNotice::toUiState),
    nextCursor: Long? = null,
    hasNext: Boolean = false,
    selectedFilter: JourneyDetailFilterType? = null,
    scheduleDays: List<JourneyScheduleDayUiState> = emptyList(),
    schedulesByDay: Map<Int, List<JourneyScheduleItemUiState>> = emptyMap()
): JourneyDetailUiState = JourneyDetailUiState(
    imageUrl = photoUrl,
    dDay = recruitDeadlineDDay,
    title = title,
    period = "$startDate - $endDate",
    isOwner = isOwner,
    members = members.map(JourneyMember::toUiState),
    posts = posts,
    filteredPosts = when (selectedFilter ?: JourneyDetailFilterType.FIXED) {
        JourneyDetailFilterType.FIXED -> posts.filter { it.isPinned }
        JourneyDetailFilterType.VOTE -> emptyList()
        else -> posts.filter { it.category == selectedFilter }
    },
    nextCursor = nextCursor,
    hasNext = hasNext,
    selectedFilter = selectedFilter ?: JourneyDetailFilterType.FIXED,
    scheduleDays = scheduleDays,
    selectedScheduleDay = scheduleDays.firstOrNull()?.day ?: 1,
    schedulesByDay = schedulesByDay
)

private fun JourneyMember.toUiState(): JourneyMemberUiState = JourneyMemberUiState(
    id = userId,
    username = nickname,
    profileImageUrl = profileImage.url,
    role = roles.firstOrNull()?.customRoleLabel ?: roles.firstOrNull()?.roleType.orEmpty()
)

private fun JourneyPinnedNotice.toUiState(): JourneyDetailPostUiState = JourneyDetailPostUiState(
    id = journeyPostId,
    category = JourneyDetailFilterType.FIXED,
    username = "",
    createdAt = createdAt,
    title = content,
    isPinned = true
)

private fun JourneyPost.toUiState(): JourneyDetailPostUiState = JourneyDetailPostUiState(
    id = journeyPostId,
    category = JourneyDetailFilterType.BOARD,
    profileImageUrl = author.profileImage.url,
    username = author.nickname,
    createdAt = createdAt,
    title = content,
    imageUrls = imageUrls,
    commentCount = commentCount,
    isPinned = isNotice
)

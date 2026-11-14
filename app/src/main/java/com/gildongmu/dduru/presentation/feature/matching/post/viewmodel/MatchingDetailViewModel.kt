// ktlint-disable max-line-length
package com.gildongmu.dduru.presentation.feature.matching.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.dto.response.post.ParticipationRequest
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.matching.post.effect.MatchingDetailSideEffect
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailUiState
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
class MatchingDetailViewModel @Inject constructor(
    private val postService: PostService,
    private val chatRoomRepository: ChatRoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingDetailUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingDetailUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchingDetailSideEffect>()
    val sideEffect: SharedFlow<MatchingDetailSideEffect> = _sideEffect.asSharedFlow()

    private var loadedPostId: Long? = null
    private var isDeleting = false

    fun loadMatchingDetail(postId: Long) {
        if (loadedPostId == postId && _uiState.value is UiState.Success) return

        loadedPostId = postId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { postService.getPost(postId) }
                .onSuccess { response ->
                    val post = response.data ?: error("게시글 상세 응답이 비어있어요.")
                    _uiState.value = UiState.Success(
                        MatchingDetailUiState(
                            id = post.id,
                            title = post.title,
                            content = post.content,
                            status = if (post.status == "CLOSED") com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostRecruitStatus.CLOSED else com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostRecruitStatus.OPEN,
                            isFull = post.isFull,
                            startDate = post.startDate,
                            endDate = post.endDate,
                            destination = post.destination,
                            recruitCapacity = post.recruitCapacity,
                            recruitCount = post.recruitCount,
                            preferredGender = post.preferredGender,
                            minAge = post.minAge.takeUnless { post.isAgeAny },
                            maxAge = post.maxAge.takeUnless { post.isAgeAny },
                            photoUrl = post.photoUrl,
                            tags = post.tags,
                            likeCount = post.likeCount,
                            author = com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailAuthorUiState(
                                userId = post.author.userId,
                                nickname = post.author.nickname,
                                gender = post.author.gender,
                                ageGroup = post.author.ageGroup?.filter(Char::isDigit)?.toIntOrNull() ?: 0,
                                isSuperHost = post.author.isSuperHost
                            ),
                            isOwner = post.isOwner,
                            hasLiked = post.hasLiked,
                            tripDurationText = post.tripDurationText,
                            recruitDeadlineDDay = post.recruitDeadlineDDay,
                            companionType = post.companionType,
                            myParticipationStatus = when (post.myParticipationStatus) { "PENDING", "CONTACTING" -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.PENDING; "APPROVED" -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.APPROVED; "REJECTED" -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.REJECTED; else -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.NONE }
                        )
                    )
                }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onLikeClick() {
        val postId = (_uiState.value as? UiState.Success)?.data?.id ?: return
        viewModelScope.launch {
            runCatching { postService.toggleLike(postId) }
                .onSuccess { response ->
                    if (response.isSuccessful) loadMatchingDetail(postId)
                }
        }
    }

    fun onDeleteClick() {
        val post = (_uiState.value as? UiState.Success)?.data ?: return
        if (!post.isOwner || isDeleting) return
        isDeleting = true
        viewModelScope.launch {
            runCatching { postService.deletePost(post.id) }
                .onSuccess { response ->
                    isDeleting = false
                    if (response.code() == 204) _sideEffect.emit(MatchingDetailSideEffect.NavigateBack)
                    else _sideEffect.emit(MatchingDetailSideEffect.ShowMessage("게시글을 삭제하지 못했어요."))
                }
                .onFailure {
                    isDeleting = false
                    _sideEffect.emit(MatchingDetailSideEffect.ShowMessage("게시글 삭제에 실패했어요."))
                }
        }
    }

    fun onContactClick(postId: Long) {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (current.isContactRequesting) return

        viewModelScope.launch {
            _uiState.value = UiState.Success(current.copy(isContactRequesting = true))
            chatRoomRepository.createPrivateChatRoom(postId)
                .onSuccess { chatRoom ->
                    _uiState.value = UiState.Success(current.copy(isContactRequesting = false))
                    _sideEffect.emit(MatchingDetailSideEffect.NavigateToChatRoom(chatRoom))
                }
                .onFailure { error ->
                    _uiState.value = UiState.Success(current.copy(isContactRequesting = false))
                    _sideEffect.emit(MatchingDetailSideEffect.ShowMessage(error.toContactErrorMessage()))
                }
        }
    }

    private fun Throwable.toContactErrorMessage(): String {
        return when (message) {
            "FORBIDDEN" -> "자신의 게시글에는 문의할 수 없어요."
            "NOT_FOUND" -> "사용자를 찾을 수 없어요."
            else -> "채팅방을 열지 못했어요. 다시 시도해 주세요."
        }
    }

    fun onParticipationClick(postId: Long) {
        viewModelScope.launch {
            val response = runCatching {
                postService.createParticipation(postId, ParticipationRequest())
            }.getOrNull() ?: return@launch
            if (response.status in 200..299) loadMatchingDetail(postId)
        }
    }
}

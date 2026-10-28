package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.journey.entity.JourneyMember
import com.gildongmu.dduru.domain.journey.entity.JourneyMemberRole
import com.gildongmu.dduru.domain.journey.repository.JourneyRepository
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyAdminViewModel @Inject constructor(
    private val journeyRepository: JourneyRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<JourneyAdminUiState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _roleUpdateSucceeded = MutableSharedFlow<Long>()
    val roleUpdateSucceeded = _roleUpdateSucceeded.asSharedFlow()

    private var journeyId: Long? = null

    fun loadMembers(journeyId: Long) {
        if (this.journeyId == journeyId && _uiState.value is UiState.Success) return

        this.journeyId = journeyId
        viewModelScope.launch { refreshMembers() }
    }

    fun updateMemberRoles(memberId: Long, roles: ImmutableList<String>) {
        val currentJourneyId = journeyId ?: return
        viewModelScope.launch {
            journeyRepository.replaceJourneyMemberRoles(
                journeyId = currentJourneyId,
                memberUserId = memberId,
                roles = roles.map(String::toJourneyMemberRole)
            ).fold(
                onSuccess = {
                    Log.d(TAG, "멤버 역할 변경 성공: journeyId=$currentJourneyId, memberId=$memberId")
                    refreshMembers()
                    _roleUpdateSucceeded.emit(memberId)
                },
                onFailure = { throwable ->
                    Log.e(TAG, "멤버 역할 변경 실패: journeyId=$currentJourneyId, memberId=$memberId", throwable)
                }
            )
        }
    }

    fun kickMember(memberId: Long) {
        val currentJourneyId = journeyId ?: return
        removeMemberFromUiState(memberId)

        viewModelScope.launch {
            journeyRepository.removeJourneyMember(currentJourneyId, memberId).fold(
                onSuccess = {
                    Log.d(TAG, "멤버 강퇴 성공: journeyId=$currentJourneyId, memberId=$memberId")
                    refreshMembers(showLoading = false)
                },
                onFailure = { throwable ->
                    Log.e(TAG, "멤버 강퇴 실패: journeyId=$currentJourneyId, memberId=$memberId", throwable)
                    refreshMembers()
                }
            )
        }
    }

    private fun removeMemberFromUiState(memberId: Long) {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        _uiState.value = UiState.Success(
            currentState.copy(
                members = currentState.members.filterNot { it.id == memberId }.toImmutableList()
            )
        )
    }

    private suspend fun refreshMembers(showLoading: Boolean = true) {
        val currentJourneyId = journeyId ?: return
        if (showLoading) _uiState.value = UiState.Loading
        journeyRepository.getJourneyMembers(currentJourneyId).fold(
            onSuccess = { members ->
                _uiState.value = UiState.Success(
                    JourneyAdminUiState(members = members.map(JourneyMember::toUiState).toImmutableList())
                )
            },
            onFailure = { _uiState.value = UiState.Failure }
        )
    }

    private companion object {
        const val TAG = "JourneyAdminViewModel"
    }
}

private fun JourneyMember.toUiState(): JourneyAdminMember = JourneyAdminMember(
    id = userId,
    username = nickname,
    profileImageUrl = profileImage.url,
    isHost = isHost,
    roles = roles.map(JourneyMemberRole::toDisplayName).toImmutableList()
)

private fun String.toJourneyMemberRole(): JourneyMemberRole = when (this) {
    "총무" -> JourneyMemberRole(roleType = "TREASURER", customRoleLabel = null)
    "일정 담당" -> JourneyMemberRole(roleType = "SCHEDULE", customRoleLabel = null)
    "사진 담당" -> JourneyMemberRole(roleType = "PHOTO", customRoleLabel = null)
    "길찾기 담당" -> JourneyMemberRole(roleType = "NAVIGATION", customRoleLabel = null)
    "예약 담당" -> JourneyMemberRole(roleType = "RESERVATION", customRoleLabel = null)
    else -> JourneyMemberRole(roleType = "CUSTOM", customRoleLabel = this)
}

private fun JourneyMemberRole.toDisplayName(): String = when (roleType) {
    "TREASURER" -> "총무"
    "SCHEDULE" -> "일정 담당"
    "PHOTO" -> "사진 담당"
    "NAVIGATION" -> "길찾기 담당"
    "RESERVATION" -> "예약 담당"
    "CUSTOM" -> customRoleLabel.orEmpty()
    else -> customRoleLabel ?: roleType
}

package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class JourneyAdminViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyAdminUiState())
    val uiState: StateFlow<JourneyAdminUiState> = _uiState.asStateFlow()

    fun updateMemberRoles(
        memberId: Long,
        roles: ImmutableList<String>
    ) {
        _uiState.value = _uiState.value.copy(
            members = _uiState.value.members.map { member ->
                if (member.id == memberId) member.copy(roles = roles) else member
            }.toImmutableList()
        )
    }

    fun kickMember(memberId: Long) {
        _uiState.value = _uiState.value.copy(
            members = _uiState.value.members.filterNot { it.id == memberId }.toImmutableList()
        )
    }
}

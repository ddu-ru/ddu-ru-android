package com.gildongmu.dduru.presentation.feature.matching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.presentation.feature.matching.effect.MatchingSideEffect
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
class MatchingViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingUiState>>(UiState.Success(MatchingUiState.fake))
    val uiState: StateFlow<UiState<MatchingUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchingSideEffect>()
    val sideEffect: SharedFlow<MatchingSideEffect> = _sideEffect.asSharedFlow()

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

    fun onSortSelected(sort: String) {
        if ((_uiState.value as? UiState.Success)?.data?.selectedSort == sort) return

        updateMatchingUiState {
            copy(selectedSort = sort)
        }
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

    private fun updateMatchingUiState(reducer: MatchingUiState.() -> MatchingUiState) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(currentState.data.reducer())
        }
    }
}

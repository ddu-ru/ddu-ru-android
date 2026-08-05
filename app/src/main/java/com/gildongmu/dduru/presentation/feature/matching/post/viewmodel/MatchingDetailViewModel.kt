package com.gildongmu.dduru.presentation.feature.matching.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MatchingDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingDetailUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingDetailUiState>> = _uiState.asStateFlow()

    private var loadedPostId: Long? = null

    fun loadMatchingDetail(postId: Long) {
        if (loadedPostId == postId && _uiState.value is UiState.Success) return

        loadedPostId = postId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(MatchingDetailUiState(id = postId))
        }
    }

    fun onLikeClick() {
        _uiState.update { currentState ->
            val currentData = (currentState as? UiState.Success)?.data ?: return@update currentState
            currentState.copy(
                data = currentData.copy(
                    hasLiked = !currentData.hasLiked,
                    likeCount = if (currentData.hasLiked) {
                        (currentData.likeCount - 1).coerceAtLeast(0)
                    } else {
                        currentData.likeCount + 1
                    }
                )
            )
        }
    }
}

package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.BlockedUser
import com.gildongmu.dduru.domain.repository.MyPageRepository
import com.gildongmu.dduru.presentation.component.DduruToastMessage
import com.gildongmu.dduru.presentation.component.toErrorToast
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BlockedListUiState(
    val users: List<BlockedUser> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false
)

@HiltViewModel
class BlockedListViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<BlockedListUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<BlockedListUiState>> = _uiState.asStateFlow()

    private val _errorToast = MutableSharedFlow<DduruToastMessage>()
    val errorToast: SharedFlow<DduruToastMessage> = _errorToast.asSharedFlow()

    init {
        load()
    }

    private var loadJob: Job? = null

    fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            myPageRepository.getBlockedUsers(cursor = null)
                .onSuccess { page ->
                    _uiState.value = UiState.Success(
                        BlockedListUiState(users = page.users, nextCursor = page.nextCursor, hasNext = page.hasNext)
                    )
                }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun loadMore() {
        val state = currentData() ?: return
        if (!state.hasNext || state.isLoadingMore) return

        viewModelScope.launch {
            updateData { it.copy(isLoadingMore = true) }
            myPageRepository.getBlockedUsers(cursor = state.nextCursor)
                .onSuccess { page ->
                    updateData {
                        it.copy(
                            users = it.users + page.users,
                            nextCursor = page.nextCursor,
                            hasNext = page.hasNext,
                            isLoadingMore = false
                        )
                    }
                }
                .onFailure { error ->
                    updateData { it.copy(isLoadingMore = false) }
                    _errorToast.emit(error.toErrorToast())
                }
        }
    }

    fun onUnblockClick(userId: Long) {
        val state = currentData() ?: return
        val index = state.users.indexOfFirst { it.userId == userId }
        if (index < 0) return
        val removed = state.users[index]
        updateData { data -> data.copy(users = data.users.filterNot { it.userId == userId }) }

        viewModelScope.launch {
            myPageRepository.unblockUser(userId).onFailure { error ->
                updateData { data ->
                    val users = data.users.toMutableList().apply { add(index.coerceAtMost(size), removed) }
                    data.copy(users = users)
                }
                _errorToast.emit(error.toErrorToast())
            }
        }
    }

    private fun currentData(): BlockedListUiState? = (_uiState.value as? UiState.Success)?.data

    private fun updateData(transform: (BlockedListUiState) -> BlockedListUiState) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(transform(data))
        }
    }
}

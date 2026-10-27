package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.MyPagePost
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostFilter
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostPage
import com.gildongmu.dduru.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MyPagePostListUiState(
    val posts: List<MyPagePost> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
    val totalCount: Long? = null,
    val filter: MyPagePostFilter = MyPagePostFilter.ALL
)

abstract class MyPagePostListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MyPagePostListUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MyPagePostListUiState>> = _uiState.asStateFlow()

    protected abstract suspend fun fetch(cursor: Long?, filter: MyPagePostFilter): Result<MyPagePostPage>

    protected fun load(filter: MyPagePostFilter = MyPagePostFilter.ALL) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            fetch(cursor = null, filter = filter)
                .onSuccess { page ->
                    _uiState.value = UiState.Success(
                        MyPagePostListUiState(
                            posts = page.posts,
                            nextCursor = page.nextCursor,
                            hasNext = page.hasNext,
                            totalCount = page.totalCount,
                            filter = filter
                        )
                    )
                }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun loadMore() {
        val state = (_uiState.value as? UiState.Success)?.data ?: return
        if (!state.hasNext || state.isLoadingMore) return

        viewModelScope.launch {
            updateData { it.copy(isLoadingMore = true) }
            fetch(cursor = state.nextCursor, filter = state.filter)
                .onSuccess { page ->
                    updateData {
                        it.copy(
                            posts = it.posts + page.posts,
                            nextCursor = page.nextCursor,
                            hasNext = page.hasNext,
                            isLoadingMore = false
                        )
                    }
                }
                .onFailure { updateData { it.copy(isLoadingMore = false) } }
        }
    }

    private fun updateData(transform: (MyPagePostListUiState) -> MyPagePostListUiState) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(transform(data))
        }
    }
}

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : MyPagePostListViewModel() {
    init { load() }

    override suspend fun fetch(cursor: Long?, filter: MyPagePostFilter): Result<MyPagePostPage> =
        myPageRepository.getLikedPosts(cursor)
}

@HiltViewModel
class MyPostsViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : MyPagePostListViewModel() {
    init { load() }

    fun onFilterSelected(filter: MyPagePostFilter) = load(filter)

    override suspend fun fetch(cursor: Long?, filter: MyPagePostFilter): Result<MyPagePostPage> =
        myPageRepository.getMyPosts(cursor, filter)
}

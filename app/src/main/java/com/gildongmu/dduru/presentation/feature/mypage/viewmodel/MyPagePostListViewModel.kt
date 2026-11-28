package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.MyPagePost
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostFilter
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostPage
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostSort
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

data class MyPagePostListUiState(
    val posts: List<MyPagePost> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
    val totalCount: Long? = null,
    val ticketCount: Int = 0,
    val filter: MyPagePostFilter = MyPagePostFilter.ALL,
    val sort: MyPagePostSort = MyPagePostSort.LATEST
)

abstract class MyPagePostListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MyPagePostListUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MyPagePostListUiState>> = _uiState.asStateFlow()

    private val _errorToast = MutableSharedFlow<DduruToastMessage>()
    val errorToast: SharedFlow<DduruToastMessage> = _errorToast.asSharedFlow()

    private var loadJob: Job? = null
    private var loadMoreJob: Job? = null

    protected abstract suspend fun fetch(
        cursor: Long?,
        filter: MyPagePostFilter,
        sort: MyPagePostSort
    ): Result<MyPagePostPage>

    fun load(
        filter: MyPagePostFilter = currentData()?.filter ?: MyPagePostFilter.ALL,
        sort: MyPagePostSort = currentData()?.sort ?: MyPagePostSort.LATEST
    ) {
        loadJob?.cancel()
        loadMoreJob?.cancel()
        onLoadStart()
        updateData { it.copy(isLoadingMore = false) }
        loadJob = viewModelScope.launch {
            if (_uiState.value !is UiState.Success) _uiState.value = UiState.Loading
            fetch(cursor = null, filter = filter, sort = sort)
                .onSuccess { page ->
                    _uiState.value = UiState.Success(
                        MyPagePostListUiState(
                            posts = filterLoadedPosts(page.posts),
                            nextCursor = page.nextCursor,
                            hasNext = page.hasNext,
                            totalCount = page.totalCount,
                            ticketCount = page.unusedSuperHostTicketCount,
                            filter = filter,
                            sort = sort
                        )
                    )
                }
                .onFailure { error ->
                    if (_uiState.value is UiState.Success) {
                        _errorToast.emit(error.toErrorToast())
                    } else {
                        _uiState.value = UiState.Failure
                    }
                }
        }
    }

    fun loadMore() {
        val state = currentData() ?: return
        if (!state.hasNext || state.isLoadingMore || loadJob?.isActive == true) return

        loadMoreJob = viewModelScope.launch {
            updateData { it.copy(isLoadingMore = true) }
            fetch(cursor = state.nextCursor, filter = state.filter, sort = state.sort)
                .onSuccess { page ->
                    updateData {
                        it.copy(
                            posts = it.posts + filterLoadedPosts(page.posts),
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

    protected open fun onLoadStart() = Unit

    protected open fun filterLoadedPosts(posts: List<MyPagePost>): List<MyPagePost> = posts

    protected fun currentData(): MyPagePostListUiState? = (_uiState.value as? UiState.Success)?.data

    protected fun updateData(transform: (MyPagePostListUiState) -> MyPagePostListUiState) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(transform(data))
        }
    }

    protected suspend fun emitError(error: Throwable) {
        _errorToast.emit(error.toErrorToast())
    }
}

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : MyPagePostListViewModel() {

    override suspend fun fetch(
        cursor: Long?,
        filter: MyPagePostFilter,
        sort: MyPagePostSort
    ): Result<MyPagePostPage> = myPageRepository.getLikedPosts(cursor)

    private val pendingUnlikeIds = mutableSetOf<Long>()
    private val completedUnlikeIds = mutableSetOf<Long>()

    override fun onLoadStart() {
        completedUnlikeIds.clear()
    }

    override fun filterLoadedPosts(posts: List<MyPagePost>): List<MyPagePost> =
        posts.filterNot { it.id in pendingUnlikeIds || it.id in completedUnlikeIds }

    fun onUnlikeClick(postId: Long) {
        val posts = currentData()?.posts ?: return
        val index = posts.indexOfFirst { it.id == postId }
        if (index < 0) return
        val removed = posts[index]
        pendingUnlikeIds += postId
        updateData { data -> data.copy(posts = data.posts.filterNot { it.id == postId }) }

        viewModelScope.launch {
            val result = myPageRepository.togglePostLike(postId)
            pendingUnlikeIds -= postId
            result.onSuccess { completedUnlikeIds += postId }
            result.onFailure { error ->
                updateData { data ->
                    if (data.posts.any { it.id == postId }) {
                        data
                    } else {
                        data.copy(posts = data.posts.toMutableList().apply { add(index.coerceAtMost(size), removed) })
                    }
                }
                emitError(error)
            }
        }
    }
}

@HiltViewModel
class MyPostsViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : MyPagePostListViewModel() {

    override suspend fun fetch(
        cursor: Long?,
        filter: MyPagePostFilter,
        sort: MyPagePostSort
    ): Result<MyPagePostPage> = myPageRepository.getMyPosts(cursor, filter, sort)

    fun onSortClick() {
        val current = currentData()?.sort ?: MyPagePostSort.LATEST
        val next = if (current == MyPagePostSort.LATEST) MyPagePostSort.OLDEST else MyPagePostSort.LATEST
        load(sort = next)
    }
}

package com.gildongmu.dduru.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.home.HomeNotification
import com.gildongmu.dduru.domain.repository.home.HomeNotificationRepository
import com.gildongmu.dduru.presentation.feature.home.effect.HomeNotificationSideEffect
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationFilter
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationItemUiState
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeNotificationViewModel @Inject constructor(
    private val homeNotificationRepository: HomeNotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeNotificationUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeNotificationUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeNotificationSideEffect>()
    val sideEffect: SharedFlow<HomeNotificationSideEffect> = _sideEffect.asSharedFlow()

    private var nextCursor: Long? = null

    init {
        loadNotifications(HomeNotificationFilter.ALL)
    }

    fun onFilterClick(filter: HomeNotificationFilter) {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.selectedFilter == filter) return

        loadNotifications(filter)
    }

    fun onMarkAllAsReadClick() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.isMarkingAllAsRead || currentState.unreadCount == 0) return

        updateUiState {
            copy(
                isMarkingAllAsRead = true,
                markAllAsReadFailed = false
            )
        }

        viewModelScope.launch {
            homeNotificationRepository.markAllAsRead()
                .onSuccess { loadNotifications(currentState.selectedFilter) }
                .onFailure {
                    updateUiState {
                        copy(
                            isMarkingAllAsRead = false,
                            markAllAsReadFailed = true
                        )
                    }
                }
        }
    }

    fun onDeleteAllClick() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.isDeletingAll) return

        updateUiState { copy(isDeletingAll = true, deleteAllFailed = false) }

        viewModelScope.launch {
            homeNotificationRepository.deleteAll()
                .onSuccess { loadNotifications(currentState.selectedFilter) }
                .onFailure {
                    updateUiState {
                        copy(isDeletingAll = false, deleteAllFailed = true)
                    }
                }
        }
    }

    fun onNotificationClick(notification: HomeNotificationItemUiState) {
        viewModelScope.launch {
            _sideEffect.emit(
                HomeNotificationSideEffect.NavigateToResource(
                    resourceType = notification.resourceType,
                    resourceId = notification.resourceId
                )
            )

            if (!notification.isRead) {
                homeNotificationRepository.markAsRead(notification.id)
                    .onSuccess {
                        loadNotifications(
                            (_uiState.value as? UiState.Success)
                                ?.data
                                ?.selectedFilter
                                ?: HomeNotificationFilter.ALL
                        )
                    }
                    .onFailure { /* 이동은 유지하고, 다음 알림함 진입 시 서버 상태를 다시 조회한다. */ }
            }
        }
    }

    fun loadMoreNotifications() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        val cursor = nextCursor ?: return
        if (!currentState.hasNext || currentState.isLoadingMore) return

        updateUiState { copy(isLoadingMore = true) }
        viewModelScope.launch {
            homeNotificationRepository.getNotifications(currentState.selectedFilter.apiValue, cursor)
                .onSuccess { page ->
                    nextCursor = page.nextCursor
                    updateUiState {
                        copy(
                            hasNext = page.hasNext,
                            isLoadingMore = false,
                            notifications = (notifications + page.notifications.map { it.toUiState() })
                                .toImmutableList()
                        )
                    }
                }
                .onFailure {
                    updateUiState { copy(isLoadingMore = false) }
                }
        }
    }

    private fun loadNotifications(filter: HomeNotificationFilter) {
        viewModelScope.launch {
            val notificationsResult = homeNotificationRepository.getNotifications(filter.apiValue)
            val unreadCountResult = homeNotificationRepository.getUnreadCount()

            val notificationPage = notificationsResult.getOrElse {
                _uiState.value = UiState.Failure
                return@launch
            }
            val unreadCount = unreadCountResult.getOrElse {
                _uiState.value = UiState.Failure
                return@launch
            }

            _uiState.value = UiState.Success(
                HomeNotificationUiState(
                    selectedFilter = filter,
                    unreadCount = unreadCount.total,
                    matchUnreadCount = unreadCount.match,
                    journeyUnreadCount = unreadCount.journey,
                    hasNext = notificationPage.hasNext,
                    notifications = notificationPage.notifications.map { it.toUiState() }.toImmutableList()
                )
            )
            nextCursor = notificationPage.nextCursor
        }
    }

    private fun updateUiState(reducer: HomeNotificationUiState.() -> HomeNotificationUiState) {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        _uiState.value = UiState.Success(currentState.reducer())
    }

}

private fun HomeNotification.toUiState(): HomeNotificationItemUiState {
    return HomeNotificationItemUiState(
        id = id,
        title = title,
        body = body,
        timeAgo = timeAgo,
        isRead = isRead,
        type = type,
        resourceType = resourceType,
        resourceId = resourceId
    )
}

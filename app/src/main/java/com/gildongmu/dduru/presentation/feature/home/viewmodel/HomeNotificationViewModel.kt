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
        loadNotifications()
    }

    fun onFilterClick(filter: HomeNotificationFilter) {
        updateUiState { copy(selectedFilter = filter) }
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
                .onSuccess { updateAllNotificationsReadState() }
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
                        updateNotificationReadState(notification.id)
                    }
                    .onFailure { _uiState.value = UiState.Failure }
            }
        }
    }

    fun loadMoreNotifications() {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        val cursor = nextCursor ?: return
        if (!currentState.hasNext || currentState.isLoadingMore) return

        updateUiState { copy(isLoadingMore = true) }
        viewModelScope.launch {
            homeNotificationRepository.getNotifications(cursor)
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

    private fun loadNotifications() {
        viewModelScope.launch {
            val notificationsResult = homeNotificationRepository.getNotifications()
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
                    unreadCount = unreadCount,
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

    private fun updateNotificationReadState(notificationId: Long) {
        updateUiState {
            val updatedNotifications = notifications.map { notification ->
                if (notification.id == notificationId) {
                    notification.copy(isRead = true)
                } else {
                    notification
                }
            }.toImmutableList()

            copy(
                unreadCount = updatedNotifications.count { !it.isRead },
                notifications = updatedNotifications
            )
        }
    }

    private fun updateAllNotificationsReadState() {
        updateUiState {
            copy(
                unreadCount = 0,
                isMarkingAllAsRead = false,
                markAllAsReadFailed = false,
                notifications = notifications.map { notification ->
                    notification.copy(isRead = true)
                }.toImmutableList()
            )
        }
    }
}

private fun HomeNotification.toUiState(): HomeNotificationItemUiState {
    return HomeNotificationItemUiState(
        id = id,
        title = title,
        body = body,
        timeAgo = timeAgo,
        category = category,
        isRead = isRead,
        type = type,
        resourceType = resourceType,
        resourceId = resourceId
    )
}

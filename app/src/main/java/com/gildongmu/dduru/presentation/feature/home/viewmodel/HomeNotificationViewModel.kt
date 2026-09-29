package com.gildongmu.dduru.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.home.HomeNotification
import com.gildongmu.dduru.domain.repository.home.HomeNotificationRepository
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationFilter
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationItemUiState
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeNotificationViewModel @Inject constructor(
    private val homeNotificationRepository: HomeNotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeNotificationUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeNotificationUiState>> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun onFilterClick(filter: HomeNotificationFilter) {
        updateUiState { copy(selectedFilter = filter) }
    }

    fun onMarkAllAsReadClick() {
        viewModelScope.launch {
            homeNotificationRepository.markAllAsRead()
                .onSuccess { loadNotifications() }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onDeleteAllClick() {
        viewModelScope.launch {
            homeNotificationRepository.deleteAll()
                .onSuccess { loadNotifications() }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            val notificationsResult = homeNotificationRepository.getNotifications()
            val unreadCountResult = homeNotificationRepository.getUnreadCount()

            val notifications = notificationsResult.getOrElse {
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
                    notifications = notifications.map { it.toUiState() }.toImmutableList()
                )
            )
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
        category = category,
        isRead = isRead
    )
}

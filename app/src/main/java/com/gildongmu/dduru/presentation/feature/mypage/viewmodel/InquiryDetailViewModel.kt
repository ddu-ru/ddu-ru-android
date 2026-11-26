package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.InquiryDetail
import com.gildongmu.dduru.domain.repository.CustomerSupportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class InquiryDetailViewModel @Inject constructor(
    private val customerSupportRepository: CustomerSupportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<InquiryDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<InquiryDetail>> = _uiState.asStateFlow()

    fun load(inquiryId: Long) {
        viewModelScope.launch {
            if (_uiState.value !is UiState.Success) _uiState.value = UiState.Loading
            customerSupportRepository.getInquiry(inquiryId)
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { if (_uiState.value !is UiState.Success) _uiState.value = UiState.Failure }
        }
    }
}

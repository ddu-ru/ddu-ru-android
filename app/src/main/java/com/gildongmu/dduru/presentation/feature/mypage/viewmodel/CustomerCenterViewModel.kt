package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.domain.entity.mypage.FaqCategory
import com.gildongmu.dduru.domain.repository.CustomerSupportRepository
import com.gildongmu.dduru.presentation.component.DduruToastMessage
import com.gildongmu.dduru.presentation.component.toErrorToast
import com.gildongmu.dduru.presentation.feature.mypage.model.FaqUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.toUiModel
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

data class CustomerCenterUiState(
    val segmentIndex: Int = 0,
    val categories: List<String> = listOf("전체", "서비스", "계정", "매칭", "보안"),
    val selectedCategoryIndex: Int = 0,
    val searchQuery: String = "",
    val faqs: List<FaqUiModel> = emptyList(),
    val expandedFaqIds: Set<Long> = emptySet(),
    val inquiries: List<InquiryUiModel> = emptyList(),
    val inquiryNextCursor: Long? = null,
    val hasNextInquiry: Boolean = false,
    val isLoadingInquiries: Boolean = false
)

@HiltViewModel
class CustomerCenterViewModel @Inject constructor(
    private val customerSupportRepository: CustomerSupportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerCenterUiState())
    val uiState: StateFlow<CustomerCenterUiState> = _uiState.asStateFlow()

    private val _errorToast = MutableSharedFlow<DduruToastMessage>()
    val errorToast: SharedFlow<DduruToastMessage> = _errorToast.asSharedFlow()

    private var faqJob: Job? = null

    init {
        loadFaqs()
    }

    fun onSegmentClick(index: Int) {
        _uiState.update { it.copy(segmentIndex = index) }
        if (index == 1) loadInquiries()
    }

    fun onCategoryClick(index: Int) {
        _uiState.update { it.copy(selectedCategoryIndex = index, expandedFaqIds = emptySet()) }
        loadFaqs()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onSearchClick() {
        _uiState.update { it.copy(expandedFaqIds = emptySet()) }
        loadFaqs()
    }

    fun onFaqClick(id: Long) {
        _uiState.update {
            val expanded = if (id in it.expandedFaqIds) it.expandedFaqIds - id else it.expandedFaqIds + id
            it.copy(expandedFaqIds = expanded)
        }
    }

    fun refreshInquiries() {
        if (_uiState.value.segmentIndex == 1) loadInquiries()
    }

    fun loadMoreInquiries() {
        val state = _uiState.value
        if (!state.hasNextInquiry || state.isLoadingInquiries) return
        fetchInquiries(cursor = state.inquiryNextCursor)
    }

    private fun loadFaqs() {
        val state = _uiState.value
        val category = FaqCategory.entries.getOrNull(state.selectedCategoryIndex - 1)
        faqJob?.cancel()
        faqJob = viewModelScope.launch {
            customerSupportRepository.getFaqs(category, state.searchQuery)
                .onSuccess { faqs -> _uiState.update { it.copy(faqs = faqs.map { faq -> faq.toUiModel() }) } }
                .onFailure { error -> _errorToast.emit(error.toErrorToast()) }
        }
    }

    private fun loadInquiries() {
        if (_uiState.value.isLoadingInquiries) return
        fetchInquiries(cursor = null)
    }

    private fun fetchInquiries(cursor: Long?) {
        _uiState.update { it.copy(isLoadingInquiries = true) }
        viewModelScope.launch {
            customerSupportRepository.getInquiries(cursor)
                .onSuccess { page ->
                    _uiState.update {
                        val inquiries = page.inquiries.map { inquiry -> inquiry.toUiModel() }
                        it.copy(
                            inquiries = if (cursor == null) inquiries else it.inquiries + inquiries,
                            inquiryNextCursor = page.nextCursor,
                            hasNextInquiry = page.hasNext,
                            isLoadingInquiries = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoadingInquiries = false) }
                    _errorToast.emit(error.toErrorToast())
                }
        }
    }
}

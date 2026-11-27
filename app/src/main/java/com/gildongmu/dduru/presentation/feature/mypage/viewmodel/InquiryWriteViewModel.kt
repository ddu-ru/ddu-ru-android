package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.entity.image.ImagePresignedUrlUploader
import com.gildongmu.dduru.data.entity.image.ImageUploadTarget
import com.gildongmu.dduru.domain.repository.CustomerSupportRepository
import com.gildongmu.dduru.presentation.component.DduruToastMessage
import com.gildongmu.dduru.presentation.component.toErrorToast
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryCategory
import com.gildongmu.dduru.presentation.feature.mypage.model.toInquiryCategory
import com.gildongmu.dduru.presentation.feature.mypage.model.toInquiryType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MAX_INQUIRY_IMAGE_COUNT = 4

data class InquiryWriteUiState(
    val inquiryId: Long? = null,
    val selectedCategory: InquiryCategory? = null,
    val content: String = "",
    val attachedImageUris: List<Uri> = emptyList(),
    val attachedImageUrls: List<String> = emptyList(),
    val attachedFileNames: List<String> = emptyList(),
    val isInquiryLoaded: Boolean = true,
    val showSubmitDialog: Boolean = false,
    val isSubmitting: Boolean = false
) {
    val attachedFileName: String?
        get() = attachedFileNames.firstOrNull()?.let { first ->
            if (attachedFileNames.size > 1) "$first 외 ${attachedFileNames.size - 1}장" else first
        }
}

sealed interface InquiryWriteEvent {
    data object Created : InquiryWriteEvent
    data object Updated : InquiryWriteEvent
    data class ShowError(val toast: DduruToastMessage) : InquiryWriteEvent
}

@HiltViewModel
class InquiryWriteViewModel @Inject constructor(
    private val customerSupportRepository: CustomerSupportRepository,
    private val imageUploader: ImagePresignedUrlUploader
) : ViewModel() {

    private val _uiState = MutableStateFlow(InquiryWriteUiState())
    val uiState: StateFlow<InquiryWriteUiState> = _uiState.asStateFlow()

    private val _event = Channel<InquiryWriteEvent>(Channel.BUFFERED)
    val event: Flow<InquiryWriteEvent> = _event.receiveAsFlow()

    private var loadJob: Job? = null

    fun loadInquiry(inquiryId: Long) {
        val state = _uiState.value
        if (state.inquiryId == inquiryId && (state.isInquiryLoaded || loadJob?.isActive == true)) return
        _uiState.update { it.copy(inquiryId = inquiryId, isInquiryLoaded = false) }

        loadJob = viewModelScope.launch {
            customerSupportRepository.getInquiry(inquiryId)
                .onSuccess { inquiry ->
                    _uiState.update {
                        it.copy(
                            selectedCategory = inquiry.type.toInquiryCategory(),
                            content = inquiry.content,
                            attachedImageUrls = inquiry.imageUrls,
                            attachedFileNames = inquiry.imageUrls.map { url -> url.substringAfterLast('/') },
                            isInquiryLoaded = true
                        )
                    }
                }
                .onFailure { error -> _event.send(InquiryWriteEvent.ShowError(error.toErrorToast())) }
        }
    }

    fun onCategoryClick(category: InquiryCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onContentChange(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun onImagesSelected(images: List<Pair<Uri, String>>) {
        if (images.isEmpty()) return
        val selected = images.take(MAX_INQUIRY_IMAGE_COUNT)
        _uiState.update {
            it.copy(
                attachedImageUris = selected.map { image -> image.first },
                attachedImageUrls = emptyList(),
                attachedFileNames = selected.map { image -> image.second }
            )
        }
    }

    fun onSubmitClick() {
        val state = _uiState.value
        if (state.inquiryId != null && !state.isInquiryLoaded) {
            loadInquiry(state.inquiryId)
            return
        }
        if (state.selectedCategory == null || state.content.isBlank()) {
            viewModelScope.launch {
                _event.send(
                    InquiryWriteEvent.ShowError(
                        DduruToastMessage("입력 내용을 확인해주세요", "문의 유형과 상세 내용을 입력해주세요")
                    )
                )
            }
            return
        }
        _uiState.update { it.copy(showSubmitDialog = true) }
    }

    fun onSubmitDialogDismiss() {
        _uiState.update { it.copy(showSubmitDialog = false) }
    }

    fun onSubmitConfirm() {
        val state = _uiState.value
        val category = state.selectedCategory ?: return
        if (state.isSubmitting || !state.isInquiryLoaded) return
        _uiState.update { it.copy(showSubmitDialog = false, isSubmitting = true) }

        viewModelScope.launch {
            val imageUrls = if (state.attachedImageUris.isEmpty()) {
                state.attachedImageUrls
            } else {
                state.attachedImageUris.map { uri ->
                    imageUploader.uploadImage(uri, ImageUploadTarget.INQUIRY).getOrElse { error ->
                        _uiState.update { it.copy(isSubmitting = false) }
                        _event.send(InquiryWriteEvent.ShowError(error.toErrorToast()))
                        return@launch
                    }
                }
            }

            val content = state.content.trim()
            val result = if (state.inquiryId == null) {
                customerSupportRepository.createInquiry(category.toInquiryType(), content, imageUrls)
            } else {
                customerSupportRepository.updateInquiry(state.inquiryId, category.toInquiryType(), content, imageUrls)
            }

            _uiState.update { it.copy(isSubmitting = false) }
            result
                .onSuccess {
                    _event.send(if (state.inquiryId == null) InquiryWriteEvent.Created else InquiryWriteEvent.Updated)
                }
                .onFailure { error -> _event.send(InquiryWriteEvent.ShowError(error.toErrorToast())) }
        }
    }
}

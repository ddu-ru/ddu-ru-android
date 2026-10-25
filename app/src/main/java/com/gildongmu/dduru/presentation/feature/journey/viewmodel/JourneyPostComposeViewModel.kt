package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.entity.image.ImagePresignedUrlUploader
import com.gildongmu.dduru.domain.journey.entity.JourneyPostCommand
import com.gildongmu.dduru.domain.journey.repository.JourneyPostRepository
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyPostComposeUiState
import com.gildongmu.dduru.presentation.feature.journey.state.MAX_PHOTO_COUNT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyPostComposeViewModel @Inject constructor(
    private val journeyPostRepository: JourneyPostRepository,
    private val imageUploader: ImagePresignedUrlUploader
) : ViewModel() {
    private val _uiState = MutableStateFlow(JourneyPostComposeUiState())
    val uiState: StateFlow<JourneyPostComposeUiState> = _uiState.asStateFlow()

    fun onContentChange(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun onPhotoSelected(photo: Uri) {
        _uiState.update { currentState ->
            if (photo in currentState.selectedPhotos || currentState.selectedPhotos.size >= MAX_PHOTO_COUNT) {
                currentState
            } else {
                currentState.copy(selectedPhotos = (currentState.selectedPhotos + photo).toImmutableList())
            }
        }
    }

    fun onRemovePhotoClick(index: Int) {
        _uiState.update { currentState ->
            if (index !in currentState.selectedPhotos.indices) {
                currentState
            } else {
                currentState.copy(
                    selectedPhotos = currentState.selectedPhotos
                        .filterIndexed { photoIndex, _ -> photoIndex != index }
                        .toImmutableList()
                )
            }
        }
    }

    fun onSubmitClick(journeyId: Long, onSuccess: (Long) -> Unit) {
        val currentState = _uiState.value
        if (!currentState.canSubmit) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            val imageUrls = mutableListOf<String>()
            for (photo in currentState.selectedPhotos) {
                val uploadResult = imageUploader.uploadImage(photo)
                if (uploadResult.isFailure) {
                    Log.e(TAG, "게시글 사진 업로드 실패", uploadResult.exceptionOrNull())
                    _uiState.update { it.copy(isSubmitting = false) }
                    return@launch
                }
                imageUrls += uploadResult.getOrThrow()
            }

            journeyPostRepository.createJourneyPost(
                journeyId = journeyId,
                command = JourneyPostCommand(
                    content = currentState.content.trim(),
                    imageUrls = imageUrls
                )
            ).fold(
                onSuccess = { post ->
                    _uiState.update { it.copy(isSubmitting = false) }
                    onSuccess(post.journeyPostId)
                },
                onFailure = { throwable ->
                    Log.e(TAG, "여정 게시글 작성 실패: journeyId=$journeyId", throwable)
                    _uiState.update { it.copy(isSubmitting = false) }
                }
            )
        }
    }

    private companion object {
        const val TAG = "JourneyPostComposeViewModel"
    }
}

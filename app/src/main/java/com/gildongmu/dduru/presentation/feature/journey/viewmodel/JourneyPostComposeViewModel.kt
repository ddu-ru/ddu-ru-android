package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyPostComposeUiState
import com.gildongmu.dduru.presentation.feature.journey.state.MAX_PHOTO_COUNT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class JourneyPostComposeViewModel @Inject constructor() : ViewModel() {
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
                currentState.copy(selectedPhotos = currentState.selectedPhotos + photo)
            }
        }
    }

    fun onRemovePhotoClick(index: Int) {
        _uiState.update { currentState ->
            if (index !in currentState.selectedPhotos.indices) {
                currentState
            } else {
                currentState.copy(
                    selectedPhotos = currentState.selectedPhotos.filterIndexed { photoIndex, _ -> photoIndex != index }
                )
            }
        }
    }

    fun onSubmitClick() = Unit
}

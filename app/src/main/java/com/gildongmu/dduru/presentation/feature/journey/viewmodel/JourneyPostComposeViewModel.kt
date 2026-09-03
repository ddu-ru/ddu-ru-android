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

@HiltViewModel
class JourneyPostComposeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(JourneyPostComposeUiState())
    val uiState: StateFlow<JourneyPostComposeUiState> = _uiState.asStateFlow()

    fun onContentChange(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }

    fun onPhotoSelected(photo: Uri) {
        if (photo !in _uiState.value.selectedPhotos) {
            _uiState.value = _uiState.value.copy(
                selectedPhotos = (_uiState.value.selectedPhotos + photo).take(MAX_PHOTO_COUNT),
            )
        }
    }

    fun onRemovePhotoClick(index: Int) {
        _uiState.value = _uiState.value.copy(
            selectedPhotos = _uiState.value.selectedPhotos.filterIndexed { photoIndex, _ -> photoIndex != index },
        )
    }

    fun onSubmitClick() = Unit
}

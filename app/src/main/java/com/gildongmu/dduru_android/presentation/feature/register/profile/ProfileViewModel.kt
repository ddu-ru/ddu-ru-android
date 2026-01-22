package com.gildongmu.dduru_android.presentation.feature.register.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.gildongmu.dduru_android.presentation.model.ProfileImageCropOption
import com.gildongmu.dduru_android.presentation.model.ProfileType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.gildongmu.dduru_android.R


@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    val radioOptions = listOf(
        ProfileImageCropOption(R.drawable.img_penguin_personallity, ProfileType.CHARACTER, 1.6f, 0.16f),
        ProfileImageCropOption(R.drawable.img_blank_profile, ProfileType.CUSTOM),
    )

    private val _selectedOption = MutableStateFlow(radioOptions[0])
    val selectedOption = _selectedOption.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    private val _introduction = MutableStateFlow("")
    val introduction = _introduction.asStateFlow()

    val maxIntroLength = 60

    fun onOptionSelected(option: ProfileImageCropOption) {
        _selectedOption.value = option
    }

    fun onImageSelected(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun onIntroductionChanged(newText: String) {
        if (newText.length <= maxIntroLength) {
            _introduction.value = newText
        }
    }
}

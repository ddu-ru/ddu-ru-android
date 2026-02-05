package com.gildongmu.dduru.presentation.feature.register.profile.viewmodel

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    val radioOptions = listOf(
        ProfileImageCropOption(
            R.drawable.img_penguin_personallity,
            ProfileType.CHARACTER,
            1.6f,
            0.16f
        ),
        ProfileImageCropOption(R.drawable.img_blank_profile, ProfileType.CUSTOM)
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

    val bgColors = ProfileBackgroundColorList

    private val _selectedBgColor = MutableStateFlow(bgColors.first().color)
    val selectedBgColor = _selectedBgColor.asStateFlow()

    private val _savedBgColor = MutableStateFlow(bgColors.first().color)
    val savedBgColor = _savedBgColor.asStateFlow()

    fun onBgColorSelected(color: Color) {
        _selectedBgColor.value = color
    }

    fun onImageSelected(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun commitBgColorSelection() {
        _savedBgColor.value = _selectedBgColor.value
    }

    fun onIntroductionChanged(newText: String) {
        if (newText.length <= maxIntroLength) {
            _introduction.value = newText
        }
    }
}

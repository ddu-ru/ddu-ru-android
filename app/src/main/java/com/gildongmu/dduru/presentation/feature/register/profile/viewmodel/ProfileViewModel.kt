package com.gildongmu.dduru.presentation.feature.register.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileBackgroundColor
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    val radioOptions = listOf(
        ProfileImageCropOption(
            R.drawable.img_penguin_personallity,
            ProfileType.CHARACTER,
            1.6f,
            0.16f
        ),
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

    val bgColors = ProfileBackgroundColorList

    private val _selectedBgColorId = MutableStateFlow(bgColors.first().id)
    val selectedBgColorId = _selectedBgColorId.asStateFlow()

    fun onBgColorSelected(id: Int) {
        _selectedBgColorId.value = id
    }

    val selectedBgColor: StateFlow<ProfileBackgroundColor> =
        selectedBgColorId
            .map { id -> bgColors.firstOrNull { it.id == id } ?: bgColors.first() }
            .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), bgColors.first())

    fun onImageSelected(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun onIntroductionChanged(newText: String) {
        if (newText.length <= maxIntroLength) {
            _introduction.value = newText
        }
    }
}

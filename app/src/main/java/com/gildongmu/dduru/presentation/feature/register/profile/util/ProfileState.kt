package com.gildongmu.dduru.presentation.feature.register.profile.util

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileImageCropOption

data class ProfileState(
    val profileSelectedOption: ProfileImageCropOption,
    val characterImage: Int,
    val selectedImageUri: Uri? = null,

    val introduction: String = "",
    val maxIntroLength: Int = 60,

    val nickname: String = "",
    val nicknameErrorMessage: String? = null,
    val maxNicknameLength: Int = 10,

    val selectedBgColor: Color,
    val savedBgColor: Color
)

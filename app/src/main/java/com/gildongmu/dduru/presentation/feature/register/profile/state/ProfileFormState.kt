package com.gildongmu.dduru.presentation.feature.register.profile.state

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption

data class ProfileFormState(
    val selectedOption: ProfileEditOption = ProfileEditOption.CUSTOM,
    val selectedImageUri: Uri? = null,
    val selectedBgColor: Color,
    val savedBgColor: Color,
    val profileUploadedUrl: String? = null,
    val profileAvatarUrl: String? = null,
    val nickname: String = "",
    val introduction: String = ""
)

package com.gildongmu.dduru.presentation.feature.register.profile.model

enum class ProfileEditOption {
    CHARACTER, CUSTOM
}

data class ProfileImageCropOption(
    val image: Any,
    val type: ProfileEditOption,
    val scale: Float = 1f,
    val offsetRatio: Float = 0f
)

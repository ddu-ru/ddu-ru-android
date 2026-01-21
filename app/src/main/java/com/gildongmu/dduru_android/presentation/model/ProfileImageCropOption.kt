package com.gildongmu.dduru_android.presentation.model

enum class ProfileType {
    CHARACTER, CUSTOM
}

data class ProfileImageCropOption(
    val resId: Int,
    val type: ProfileType,
    val scale: Float = 1f,
    val offsetRatio: Float = 0f,
)

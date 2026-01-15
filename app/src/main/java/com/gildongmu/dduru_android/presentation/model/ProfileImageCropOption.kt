package com.gildongmu.dduru_android.presentation.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ProfileType {
    CHARACTER, CUSTOM
}

data class ProfileImageCropOption(
    val resId: Int,
    val type: ProfileType,
    val scale: Float = 1f,
    val offsetRatio: Float = 0f,
)

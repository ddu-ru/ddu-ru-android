package com.gildongmu.dduru_android.presentation.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ProfileImageCropOption(
    val resId: Int,
    val scale: Float = 1f,
    val offset: Dp = 0.dp
)
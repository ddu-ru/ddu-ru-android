package com.gildongmu.dduru.presentation.feature.home.model

import androidx.compose.ui.graphics.painter.Painter

data class HomeSuperHostCardUiModel(
    val id: Long,
    val title: String,
    val location: String,
    val dateRange: String,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val hashtags: List<String>,
    val hostName: String,
    val viewCount: Int,
    val thumbnailPainter: Painter,
    val hostProfilePainter: Painter,
    val recruitmentText: String = "모집중",
    val isLiked: Boolean = false
)

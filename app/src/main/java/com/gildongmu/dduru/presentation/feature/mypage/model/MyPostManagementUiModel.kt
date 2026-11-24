package com.gildongmu.dduru.presentation.feature.mypage.model

data class MyPostManagementUiModel(
    val id: Long,
    val title: String,
    val destination: String,
    val dateRange: String,
    val capacity: String,
    val isRecruiting: Boolean,
    val viewCount: String,
    val likeCount: String,
    val thumbnailUrl: String? = null
)

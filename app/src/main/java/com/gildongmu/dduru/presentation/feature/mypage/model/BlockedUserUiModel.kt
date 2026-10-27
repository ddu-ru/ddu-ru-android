package com.gildongmu.dduru.presentation.feature.mypage.model

data class BlockedUserUiModel(
    val id: Long,
    val username: String,
    val introduction: String,
    val profileImageUrl: String? = null
)

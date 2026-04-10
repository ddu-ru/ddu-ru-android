package com.gildongmu.dduru.data.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileRequest(
    val nickname: String,
    val profileImageType: String,
    val uploadedImageUrl: String? = null,
    val bgColorId: Int? = null,
    val bio: String? = null
)

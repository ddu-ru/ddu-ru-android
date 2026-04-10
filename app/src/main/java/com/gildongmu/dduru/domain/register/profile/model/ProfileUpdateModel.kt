package com.gildongmu.dduru.domain.register.profile.model

data class ProfileUpdateModel(
    val nickname: String,
    val profileImageType: String,
    val uploadedImageUrl: String? = null,
    val bgColorId: Int? = null,
    val bio: String? = null,
    val bgColorIdValid: Boolean? = null,
    val uploadedImageUrlValid: Boolean? = null
)

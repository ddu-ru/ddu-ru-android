package com.gildongmu.dduru.domain.register.profile.model

enum class ProfileImageType(val apiValue: String) {
    AVATAR("AVATAR"),
    UPLOADED("UPLOADED"),
    DEFAULT("DEFAULT")
}

data class ProfileUpdateModel(
    val nickname: String,
    val profileImageType: ProfileImageType,
    val uploadedImageUrl: String? = null,
    val bgColorId: Int? = null,
    val bio: String? = null
)

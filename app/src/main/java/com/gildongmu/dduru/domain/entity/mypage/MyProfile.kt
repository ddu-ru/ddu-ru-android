package com.gildongmu.dduru.domain.entity.mypage

data class MyProfile(
    val nickname: String,
    val bio: String?,
    val profileImageType: String?,
    val profileImageUrl: String?,
    val bgColorId: Int?,
    val gender: String?,
    val birthday: String?,
    val phoneNumber: String?
)

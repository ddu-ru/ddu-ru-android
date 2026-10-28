package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class MyProfileResponse(
    val nickname: String = "",
    val bio: String? = null,
    val profileImage: MyProfileImageResponse? = null,
    val gender: String? = null,
    val birthday: String? = null,
    val phoneNumber: String? = null
)

@Serializable
data class MyProfileImageResponse(
    val type: String? = null,
    val url: String? = null,
    val bgColorId: Long? = null
)

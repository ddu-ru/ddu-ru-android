package com.gildongmu.dduru_android.data.nickname.api

import retrofit2.http.GET

interface NicknameApi {

    @GET("/nicknames/{nickname}/availability")
    suspend fun generateNickname(): NicknameGenerateResponseDto
}

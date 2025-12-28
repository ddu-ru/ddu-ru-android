package com.gildongmu.dduru_android.data.nickname.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NicknameApi {

    @GET("/nicknames/{nickname}/availability")
    suspend fun generateNickname(): NicknameGenerateResponseDto
}

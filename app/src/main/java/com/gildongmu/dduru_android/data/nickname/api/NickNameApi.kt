package com.gildongmu.dduru_android.data.nickname.api

import com.gildongmu.dduru_android.data.nickname.model.NicknameGenerateResponseDto
import retrofit2.http.GET

interface NicknameApi {

    @GET("/nicknames/{nickname}/availability")
    suspend fun generateNickname(): NicknameGenerateResponseDto
}

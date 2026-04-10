package com.gildongmu.dduru.data.nickname.api

import com.gildongmu.dduru.data.nickname.model.RandomNicknameResponse
import com.gildongmu.dduru.data.nickname.model.SanitizeNicknameResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NicknameApi {

    @GET("api/v1/nicknames/random")
    suspend fun getRandomNickname(): ApiResponse<RandomNicknameResponse>

    @GET("api/v1/nicknames/{nickname}/availability")
    suspend fun checkNicknameAvailability(
        @Path("nickname") nickname: String
    ): ApiResponse<SanitizeNicknameResponse>
}

package com.gildongmu.dduru.data.nickname.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NicknameService {
    @GET("api/v1/nickname/random")
    suspend fun getRandomNickname(): ApiResponse<String>

    @GET("api/v1/nicknames/{nickname}/availability")
    suspend fun checkNicknameAvailability(
        @Path("nickname") nickname: String
    ): ApiResponse<Boolean>
}

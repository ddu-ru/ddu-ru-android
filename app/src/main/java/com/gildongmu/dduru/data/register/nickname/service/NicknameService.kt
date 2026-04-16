package com.gildongmu.dduru.data.register.nickname.service

import com.gildongmu.dduru.data.register.nickname.api.NicknameApi
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NicknameService @Inject constructor(
    private val nicknameApi: NicknameApi
) {
    suspend fun getRandomNickname(): ApiResponse<String> {
        val response = nicknameApi.getRandomNickname()
        return ApiResponse(
            status = response.status,
            data = response.data?.nickname
        )
    }

    suspend fun checkNicknameAvailability(nickname: String): ApiResponse<Boolean> {
        val response = nicknameApi.checkNicknameAvailability(nickname)
        return ApiResponse(
            status = response.status,
            data = response.data?.sanitizedNickname?.isNotBlank() == true
        )
    }
}

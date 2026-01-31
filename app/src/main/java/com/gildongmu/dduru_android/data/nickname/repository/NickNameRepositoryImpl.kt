package com.gildongmu.dduru_android.data.nickname.repository

import com.gildongmu.dduru_android.data.nickname.api.NicknameApi
import com.gildongmu.dduru_android.domain.nickname.NicknameRepository
import javax.inject.Inject

class NicknameRepositoryImpl @Inject constructor(
    private val service: NicknameApi
) : NicknameRepository {

    override suspend fun getRandomNickname(): Result<String> {
        return runCatching {
            val response = service.getRandomNickname()
            response.data?.nickname
                ?: throw Exception("랜덤 닉네임 생성 실패")
        }
    }

    override suspend fun sanitizeNickname(nickname: String): Result<String?> {
        return try {
            val response = service.sanitizeNickname(nickname)

            if (response.status == 200 && response.data != null) {
                Result.success(response.data.sanitizedNickname)
            } else {
                Result.failure(
                    Exception(response.data?.message?: "닉네임을 확인해주세요.")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
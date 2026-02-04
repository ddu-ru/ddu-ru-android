package com.gildongmu.dduru_android.data.nickname.repository

import com.gildongmu.dduru_android.data.nickname.api.NicknameApi
import com.gildongmu.dduru_android.domain.nickname.NicknameRepository
import com.gildongmu.dduru_android.domain.entity.VerificationException
import javax.inject.Inject

class NicknameRepositoryImpl @Inject constructor(
    private val api: NicknameApi
) : NicknameRepository {

    override suspend fun getRandomNickname(): Result<String> {
        return runCatching {
            val response = api.getRandomNickname()

            if (response.status != 200) {
                throw VerificationException(
                    status = response.status
                )
            }

            response.data?.nickname
                ?: throw Exception("랜덤 닉네임 생성 실패")
        }
    }

    override suspend fun sanitizeNickname(nickname: String): Result<String?> {
        return runCatching {
            val response = api.sanitizeNickname(nickname)

            if (response.status != 200) {
                throw VerificationException(
                    status = response.status,
                )
            }

            response.data?.sanitizedNickname
                ?: throw IllegalStateException("sanitizedNickname이 비어있어요.")
        }
    }
}
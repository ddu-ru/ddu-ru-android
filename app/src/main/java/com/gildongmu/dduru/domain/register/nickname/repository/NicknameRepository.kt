package com.gildongmu.dduru.domain.register.nickname.repository

interface NicknameRepository {
    suspend fun getRandomNickname(): Result<String>
    suspend fun sanitizeNickname(nickname: String): Result<Boolean>
}

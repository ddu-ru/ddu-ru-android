package com.gildongmu.dduru.domain.register.nicname.repository

interface NicknameRepository {
    suspend fun getRandomNickname(): Result<String>
    suspend fun sanitizeNickname(nickname: String): Result<String?>
}

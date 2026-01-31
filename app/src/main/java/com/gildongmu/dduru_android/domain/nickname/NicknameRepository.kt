package com.gildongmu.dduru_android.domain.nickname

interface NicknameRepository {
    suspend fun getRandomNickname(): Result<String>
    suspend fun sanitizeNickname(nickname: String): Result<String?>
}
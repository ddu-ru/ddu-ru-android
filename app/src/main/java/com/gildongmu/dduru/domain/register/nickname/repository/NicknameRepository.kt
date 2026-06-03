package com.gildongmu.dduru.domain.register.nickname.repository

interface NicknameRepository {
    suspend fun getRandomNickname(): Result<String>
    suspend fun checkNicknameAvailability(nickname: String): Result<Boolean>
}

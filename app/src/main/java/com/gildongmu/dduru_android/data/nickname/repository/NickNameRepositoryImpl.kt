package com.gildongmu.dduru_android.data.nickname.repository

import com.gildongmu.dduru_android.data.nickname.api.NicknameApi

import javax.inject.Inject

class NicknameRepositoryImpl @Inject constructor(
    private val api: NicknameApi
) : NicknameRepository {

    override suspend fun generateNickname(): Nickname {
        val response = api.generateNickname()
        return Nickname(response.nickname)
    }
}

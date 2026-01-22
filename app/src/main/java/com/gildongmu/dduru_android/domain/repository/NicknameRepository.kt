package com.gildongmu.dduru_android.domain.repository

import com.gildongmu.dduru_android.domain.entity.Nickname

interface NicknameRepository {
    suspend fun checkNickname(nickname: Nickname): Boolean
    suspend fun generateNickname(): Nickname
}

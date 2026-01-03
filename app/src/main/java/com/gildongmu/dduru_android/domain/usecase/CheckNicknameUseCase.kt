package com.gildongmu.dduru_android.domain.usecase

import com.gildongmu.dduru_android.domain.entity.Nickname
import com.gildongmu.dduru_android.domain.repository.NicknameRepository
import javax.inject.Inject

class CheckNicknameUseCase @Inject constructor(
    private val repository: NicknameRepository
) {
    suspend operator fun invoke(nickname: Nickname): Boolean {
        if (nickname.value.length < 2) return false
        return repository.checkNickname(nickname)
    }
}

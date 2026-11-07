package com.gildongmu.dduru.presentation.feature.register.profile.util

import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import javax.inject.Inject

data class NicknameAvailability(
    val isAvailable: Boolean,
    val errorMessage: String?
)

class NicknameAvailabilityChecker @Inject constructor(
    private val nicknameRepository: NicknameRepository
) {
    suspend fun check(nickname: String): NicknameAvailability =
        nicknameRepository.checkNicknameAvailability(nickname).fold(
            onSuccess = { isAvailable ->
                NicknameAvailability(
                    isAvailable = isAvailable,
                    errorMessage = if (isAvailable) null else "이미 사용 중인 닉네임이에요."
                )
            },
            onFailure = { error ->
                NicknameAvailability(
                    isAvailable = false,
                    errorMessage = error.message ?: "네트워크 문제가 발생했어요."
                )
            }
        )
}

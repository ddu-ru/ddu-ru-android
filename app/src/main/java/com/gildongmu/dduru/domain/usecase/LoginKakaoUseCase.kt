package com.gildongmu.dduru.domain.usecase

import com.gildongmu.dduru.domain.repository.AuthRepository
import javax.inject.Inject

class LoginKakaoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<Boolean> {
        return authRepository.loginWithKakao(idToken)
    }
}

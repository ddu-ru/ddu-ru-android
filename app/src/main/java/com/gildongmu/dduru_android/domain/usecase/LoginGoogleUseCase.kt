package com.gildongmu.dduru_android.domain.usecase

import com.gildongmu.dduru_android.domain.repository.AuthRepository
import javax.inject.Inject

class LoginGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<Boolean> {
        return authRepository.loginWithGoogle(idToken)
    }
}
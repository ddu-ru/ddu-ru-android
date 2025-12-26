package com.gildongmu.dduru_android.domain.usecase

import android.content.Intent
import com.gildongmu.dduru_android.domain.repository.AuthRepository
import javax.inject.Inject

class LoginGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(intent: Intent): Result<Boolean> {
        return authRepository.loginWithGoogle(intent)
    }
}

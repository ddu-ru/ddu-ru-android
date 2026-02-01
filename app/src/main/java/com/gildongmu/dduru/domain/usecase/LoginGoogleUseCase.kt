package com.gildongmu.dduru.domain.usecase

import android.content.Context
import com.gildongmu.dduru.domain.repository.AuthRepository
import javax.inject.Inject

class LoginGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(context: Context): Result<Boolean> {
        return authRepository.loginWithGoogle(context)
    }
}

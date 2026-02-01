package gildong.dduru.domain.usecase

import gildong.dduru.domain.repository.AuthRepository
import javax.inject.Inject

class LoginKakaoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<Boolean> {
        return authRepository.loginWithKakao(idToken)
    }
}

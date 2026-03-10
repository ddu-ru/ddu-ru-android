package com.gildongmu.dduru.data.register.repository

import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSource
import com.gildongmu.dduru.data.register.api.OnboardingRegisterApi
import com.gildongmu.dduru.data.register.model.OnboardingRegisterRequest
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlin.coroutines.cancellation.CancellationException

class OnboardingRegisterRepositoryImpl @Inject constructor(
    private val api: OnboardingRegisterApi,
    private val verificationTokenLocalDataSource: PhoneVerificationTokenLocalDataSource
) : OnboardingRegisterRepository {
    override suspend fun onboardingRegister(
        gender: String,
        phoneNumber: String,
        birthday: String
    ): Result<Unit> {
        return try {
            val verificationToken = verificationTokenLocalDataSource.verificationToken.first()
                ?: throw IllegalStateException("인증 토큰이 없습니다.")

            val response = api.initProfile(
                OnboardingRegisterRequest(
                    gender = gender,
                    phoneNumber = phoneNumber,
                    birthday = birthday,
                    verificationToken = verificationToken
                )
            )

            if (response.status != 200) {
                throw ExceptionResponse(
                    status = response.status
                )
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

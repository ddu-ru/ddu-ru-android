package com.gildongmu.dduru.data.register.repository

import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSource
import com.gildongmu.dduru.data.register.api.OnboardingRegisterApi
import com.gildongmu.dduru.data.register.model.OnboardingRegisterRequest
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class OnboardingRegisterRepositoryImpl @Inject constructor(
    private val api: OnboardingRegisterApi,
    private val verificationTokenLocalDataSource: PhoneVerificationTokenLocalDataSource
) : OnboardingRegisterRepository {

    override suspend fun initProfile(
        gender: String,
        phoneNumber: String,
        birthday: String
    ): Result<Unit> {
        return runCatching {
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
        }
    }
}

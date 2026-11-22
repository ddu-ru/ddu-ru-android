package com.gildongmu.dduru.data.register.repository

import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSource
import com.gildongmu.dduru.data.register.api.OnboardingRegisterApi
import com.gildongmu.dduru.data.register.model.OnboardingRegisterRequest
import com.gildongmu.dduru.data.remote.toExceptionResponse
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class OnboardingRegisterRepositoryImpl @Inject constructor(
    private val api: OnboardingRegisterApi,
    private val verificationTokenLocalDataSource: PhoneVerificationTokenLocalDataSource,
    private val json: Json
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

            if (response.code() != 204) {
                throw response.toExceptionResponse(json)
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

package com.gildongmu.dduru_android.data.register.repository

import com.gildongmu.dduru_android.data.local.VerificationTokenLocalDataSource
import com.gildongmu.dduru_android.data.register.api.OnboardingRegisterApi
import com.gildongmu.dduru_android.data.register.model.OnboardingRegisterRequest
import com.gildongmu.dduru_android.domain.entity.VerificationException
import com.gildongmu.dduru_android.domain.register.OnboardingRegisterRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OnboardingRegisterRepositoryImpl @Inject constructor(
    private val api: OnboardingRegisterApi,
    private val verificationTokenLocalDataSource: VerificationTokenLocalDataSource
) : OnboardingRegisterRepository {

    override suspend fun initProfile(
        nickname: String,
        gender: String,
        phoneNumber: String,
        birthday: String,
    ): Result<Unit> {
        return runCatching {

            val verificationToken = verificationTokenLocalDataSource.verificationToken.first()
                ?: throw IllegalStateException("인증 토큰이 없습니다.")

            val response = api.initProfile(
                OnboardingRegisterRequest(
                    nickname = nickname,
                    gender = gender,
                    phoneNumber = phoneNumber,
                    birthday = birthday,
                    verificationToken = verificationToken
                )
            )

            if (response.status != 200) {
                throw VerificationException(
                    status = response.status
                )
            }
        }
    }
}

package com.gildongmu.dduru.data.phone.repository

import com.gildongmu.dduru.data.local.VerificationTokenLocalDataSource
import com.gildongmu.dduru.data.phone.api.PhoneVerificationApi
import com.gildongmu.dduru.data.phone.model.VerificationConfirmRequest
import com.gildongmu.dduru.data.phone.model.VerificationSendRequest
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.phone.PhoneVerificationRepository
import javax.inject.Inject

class PhoneVerificationRepositoryImpl @Inject constructor(
    private val api: PhoneVerificationApi,
    private val verificationTokenLocalDataSource: VerificationTokenLocalDataSource
) : PhoneVerificationRepository {

    override suspend fun sendVerificationCode(phoneNumber: String): Result<String> {
        return runCatching {
            val response = api.sendVerificationCode(
                VerificationSendRequest(phoneNumber = phoneNumber)
            )

            if (response.status != 200) {
                throw ExceptionResponse(
                    status = response.status
                )
            }

            response.data?.expiresAt
                ?: throw IllegalStateException("인증번호 발송 응답(expiresAt)이 비어있어요.")
        }
    }

    override suspend fun confirmVerificationCode(
        phoneNumber: String,
        verificationCode: String
    ): Result<Unit> {
        return runCatching {
            val response = api.confirmVerificationCode(
                VerificationConfirmRequest(
                    phoneNumber = phoneNumber,
                    code = verificationCode
                )
            )

            if (response.status != 200) {
                throw ExceptionResponse(
                    status = response.status
                )
            }

            val token = response.data?.token
                ?: throw IllegalStateException("token이 비어있어요.")

            verificationTokenLocalDataSource.saveVerificationToken(token)
        }
    }
}

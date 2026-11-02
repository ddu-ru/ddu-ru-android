package com.gildongmu.dduru.data.register.phone.repository

import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSource
import com.gildongmu.dduru.data.register.phone.api.PhoneVerificationApi
import com.gildongmu.dduru.data.register.phone.model.VerificationConfirmRequest
import com.gildongmu.dduru.data.register.phone.model.VerificationSendRequest
import com.gildongmu.dduru.data.remote.toExceptionResponse
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.phone.PhoneVerificationRepository
import javax.inject.Inject
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class PhoneVerificationRepositoryImpl @Inject constructor(
    private val api: PhoneVerificationApi,
    private val verificationTokenLocalDataSource: PhoneVerificationTokenLocalDataSource,
    private val json: Json
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
        }.withServerError()
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
        }.withServerError()
    }

    private fun <T> Result<T>.withServerError(): Result<T> = recoverCatching { e ->
        throw if (e is HttpException) e.toExceptionResponse(json) else e
    }
}

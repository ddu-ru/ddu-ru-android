package com.gildongmu.dduru.data.register.phone.repository

import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSource
import com.gildongmu.dduru.data.register.phone.api.PhoneVerificationApi
import com.gildongmu.dduru.data.register.phone.model.VerificationConfirmRequest
import com.gildongmu.dduru.data.register.phone.model.VerificationSendRequest
import com.gildongmu.dduru.data.remote.toExceptionResponse
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.phone.PhoneVerificationRepository
import com.gildongmu.dduru.domain.phone.VerificationSendResult
import com.gildongmu.dduru.domain.phone.VerificationTimers
import java.util.Base64
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import retrofit2.HttpException

private const val JWT_PAYLOAD_INDEX = 1
private const val JWT_PHONE_NUMBER_KEY = "phone_number"
private const val JWT_EXPIRES_AT_KEY = "exp"
private const val MILLIS_PER_SECOND = 1000L

class PhoneVerificationRepositoryImpl @Inject constructor(
    private val api: PhoneVerificationApi,
    private val verificationTokenLocalDataSource: PhoneVerificationTokenLocalDataSource,
    private val json: Json
) : PhoneVerificationRepository {

    override suspend fun sendVerificationCode(phoneNumber: String): Result<VerificationSendResult> {
        return runCatching {
            val response = api.sendVerificationCode(
                VerificationSendRequest(phoneNumber = phoneNumber)
            )

            if (response.status != 200) {
                throw ExceptionResponse(
                    status = response.status
                )
            }

            val data = response.data
                ?: throw IllegalStateException("인증번호 발송 응답이 비어있어요.")

            VerificationSendResult(
                expiresAt = data.expiresAt,
                resendAvailableAt = data.resendAvailableAt
            )
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

    override suspend fun hasValidVerificationToken(phoneNumber: String): Boolean {
        val token = verificationTokenLocalDataSource.verificationToken.first() ?: return false
        return runCatching {
            val payload = token.split(".")[JWT_PAYLOAD_INDEX]
            val claims = json.parseToJsonElement(
                String(Base64.getUrlDecoder().decode(payload), Charsets.UTF_8)
            ).jsonObject
            val tokenPhoneNumber = claims[JWT_PHONE_NUMBER_KEY]?.jsonPrimitive?.contentOrNull
            val expiresAtSeconds = claims[JWT_EXPIRES_AT_KEY]?.jsonPrimitive?.longOrNull ?: 0L

            tokenPhoneNumber == phoneNumber &&
                expiresAtSeconds > System.currentTimeMillis() / MILLIS_PER_SECOND
        }.getOrDefault(false)
    }

    override suspend fun clearVerificationToken() {
        verificationTokenLocalDataSource.clearVerificationToken()
    }

    override suspend fun saveVerificationTimers(timers: VerificationTimers) {
        verificationTokenLocalDataSource.saveVerificationTimers(timers)
    }

    override suspend fun getVerificationTimers(phoneNumber: String): VerificationTimers? =
        verificationTokenLocalDataSource.verificationTimers.first()
            ?.takeIf { it.phoneNumber == phoneNumber }

    override suspend fun clearVerificationTimers() {
        verificationTokenLocalDataSource.clearVerificationTimers()
    }

    private fun <T> Result<T>.withServerError(): Result<T> = recoverCatching { e ->
        throw if (e is HttpException) e.toExceptionResponse(json) else e
    }
}

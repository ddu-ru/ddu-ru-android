package com.gildongmu.dduru.data.register.repository

import com.gildongmu.dduru.data.register.api.OnboardingStatusApi
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.register.model.SurveyStatus
import com.gildongmu.dduru.domain.register.repository.OnboardingStatusRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class OnboardingStatusRepositoryImpl @Inject constructor(
    private val api: OnboardingStatusApi
) : OnboardingStatusRepository {
    override suspend fun getSurveyStatus(): Result<SurveyStatus> {
        return try {
            val response = api.getSurveyStatus()

            if (response.status != 200) {
                throw ExceptionResponse(status = response.status)
            }

            Result.success(SurveyStatus.fromApi(response.data?.surveyStatus))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

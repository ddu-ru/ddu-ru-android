package com.gildongmu.dduru.domain.usecase.survey

import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.domain.repository.SurveyRepository
import javax.inject.Inject

class SubmitSurveyUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(request: SurveyRequest): Result<SurveyResponse> {
        return surveyRepository.submitSurvey(request)
    }
}

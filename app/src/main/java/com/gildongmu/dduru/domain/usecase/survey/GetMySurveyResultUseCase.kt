package com.gildongmu.dduru.domain.usecase.survey

import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.domain.repository.SurveyRepository
import javax.inject.Inject

class GetMySurveyResultUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(): Result<SurveyResponse> {
        return surveyRepository.getMySurveyResult()
    }
}

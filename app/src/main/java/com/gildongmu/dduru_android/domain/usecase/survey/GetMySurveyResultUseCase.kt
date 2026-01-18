package com.gildongmu.dduru_android.domain.usecase.survey

import com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru_android.domain.repository.SurveyRepository
import javax.inject.Inject

class GetMySurveyResultUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(): Result<SurveyResponse> {
        return surveyRepository.getMySurveyResult()
    }
}

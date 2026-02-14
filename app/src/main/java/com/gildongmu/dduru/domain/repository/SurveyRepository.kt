package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyQuestionsResponse

interface SurveyRepository {
    suspend fun submitSurvey(request: SurveyRequest): Result<SurveyResponse>
    suspend fun getMySurveyResult(): Result<SurveyResponse>
    suspend fun getSurveyQuestions(): Result<SurveyQuestionsResponse>
}

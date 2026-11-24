package com.gildongmu.dduru.data.remote.datasource

import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyQuestionsResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import retrofit2.Response

interface SurveyRemoteDataSource {
    suspend fun submitSurvey(request: SurveyRequest): ApiResponse<SurveyResponse>
    suspend fun getMySurveyResult(): ApiResponse<SurveyResponse>
    suspend fun getSurveyQuestions(): ApiResponse<SurveyQuestionsResponse>
    suspend fun skipSurvey(): Response<Unit>
    suspend fun updateSurvey(request: SurveyRequest): ApiResponse<SurveyResponse>
}

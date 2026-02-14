package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.remote.datasource.SurveyRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyQuestionsResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.data.remote.service.SurveyService
import javax.inject.Inject

class SurveyRemoteDataSourceImpl @Inject constructor(
    private val surveyService: SurveyService
) : SurveyRemoteDataSource {
    override suspend fun submitSurvey(request: SurveyRequest): ApiResponse<SurveyResponse> {
        return surveyService.submitSurvey(request)
    }

    override suspend fun getMySurveyResult(): ApiResponse<SurveyResponse> {
        return surveyService.getMySurveyResult()
    }

    override suspend fun getSurveyQuestions(): ApiResponse<SurveyQuestionsResponse> {
        return surveyService.getSurveyQuestions()
    }
}

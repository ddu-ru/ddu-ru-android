package com.gildongmu.dduru_android.data.remote.datasource


import com.gildongmu.dduru_android.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru_android.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse


interface SurveyRemoteDataSource {
    suspend fun submitSurvey(request: SurveyRequest): ApiResponse<SurveyResponse>

    suspend fun getMySurveyResult(): ApiResponse<SurveyResponse>
}

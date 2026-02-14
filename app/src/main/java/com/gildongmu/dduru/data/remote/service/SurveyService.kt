package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyQuestionsResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyService {
    @POST("api/v1/surveys")
    suspend fun submitSurvey(@Body request: SurveyRequest): ApiResponse<SurveyResponse>

    @GET("api/v1/surveys/me")
    suspend fun getMySurveyResult(): ApiResponse<SurveyResponse>

    @GET("api/v1/surveys/questions")
    suspend fun getSurveyQuestions(): ApiResponse<SurveyQuestionsResponse>
}

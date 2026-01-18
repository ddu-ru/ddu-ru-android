package com.gildongmu.dduru_android.domain.repository

import com.gildongmu.dduru_android.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse

interface SurveyRepository {

    suspend fun submitSurvey(request: SurveyRequest): Result<SurveyResponse>

    suspend fun getMySurveyResult(): Result<SurveyResponse>
}

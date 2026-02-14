package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.SurveyAnswers
import com.gildongmu.dduru.domain.entity.SurveyQuestions
import com.gildongmu.dduru.domain.entity.SurveyResult

interface SurveyRepository {
    suspend fun submitSurvey(answers: SurveyAnswers): Result<SurveyResult>
    suspend fun getMySurveyResult(): Result<SurveyResult>
    suspend fun getSurveyQuestions(): Result<SurveyQuestions>
}

package com.gildongmu.dduru.domain.register.repository

import com.gildongmu.dduru.domain.register.model.SurveyStatus

interface OnboardingStatusRepository {
    suspend fun getSurveyStatus(): Result<SurveyStatus>
}

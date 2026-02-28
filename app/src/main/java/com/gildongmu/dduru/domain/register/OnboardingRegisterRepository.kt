package com.gildongmu.dduru.domain.register

interface OnboardingRegisterRepository {
    suspend fun initProfile(
        gender: String,
        phoneNumber: String,
        birthday: String
    ): Result<Unit>
}

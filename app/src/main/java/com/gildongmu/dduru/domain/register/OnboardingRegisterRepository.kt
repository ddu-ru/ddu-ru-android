package com.gildongmu.dduru.domain.register

interface OnboardingRegisterRepository {
    suspend fun onboardingRegister(
        gender: String,
        phoneNumber: String,
        birthday: String
    ): Result<Unit>
}

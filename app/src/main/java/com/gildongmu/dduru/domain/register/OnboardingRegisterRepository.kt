package com.gildongmu.dduru.domain.register

interface OnboardingRegisterRepository {
    suspend fun initProfile(
        nickname: String,
        gender: String,
        phoneNumber: String,
        birthday: String,
    ): Result<Unit>
}

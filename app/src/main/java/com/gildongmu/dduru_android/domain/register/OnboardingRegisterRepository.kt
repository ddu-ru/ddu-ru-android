package com.gildongmu.dduru_android.domain.register

interface OnboardingRegisterRepository {
    suspend fun initProfile(
        nickname: String,
        gender: String,
        phoneNumber: String,
        birthday: String,
    ): Result<Unit>
}
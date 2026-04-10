package com.gildongmu.dduru.domain.register.profile.repository

import com.gildongmu.dduru.domain.register.profile.model.ProfileUpdatePayload

interface ProfileRepository {
    suspend fun updateProfile(payload: ProfileUpdatePayload): Result<Unit>
}

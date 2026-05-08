package com.gildongmu.dduru.domain.register.profile.repository

import com.gildongmu.dduru.domain.register.profile.model.ProfileUpdateModel

interface ProfileRepository {
    suspend fun updateProfile(payload: ProfileUpdateModel): Result<Unit>
}

package com.gildongmu.dduru.data.profile.repository

import com.gildongmu.dduru.data.profile.api.ProfileApi
import com.gildongmu.dduru.data.profile.model.ProfileRequest
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.register.profile.model.ProfileUpdateModel
import com.gildongmu.dduru.domain.register.profile.repository.ProfileRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun updateProfile(payload: ProfileUpdateModel): Result<Unit> {
        return try {
            val response = api.updateProfile(
                ProfileRequest(
                    nickname = payload.nickname,
                    profileImageType = payload.profileImageType,
                    uploadedImageUrl = payload.uploadedImageUrl,
                    bgColorId = payload.bgColorId,
                    bio = payload.bio
                )
            )
            val responseBody = response.body()

            if (!response.isSuccessful) {
                throw ExceptionResponse(status = response.code())
            }

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

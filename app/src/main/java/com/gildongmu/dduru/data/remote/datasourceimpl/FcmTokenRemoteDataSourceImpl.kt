package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.remote.datasource.FcmTokenRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.request.FcmTokenDeleteRequest
import com.gildongmu.dduru.data.remote.dto.request.FcmTokenRegisterRequest
import com.gildongmu.dduru.data.remote.service.FcmTokenService
import javax.inject.Inject
import retrofit2.Response

class FcmTokenRemoteDataSourceImpl @Inject constructor(
    private val fcmTokenService: FcmTokenService
) : FcmTokenRemoteDataSource {
    override suspend fun registerToken(token: String): Response<Unit> {
        return fcmTokenService.registerToken(
            request = FcmTokenRegisterRequest(token = token)
        )
    }

    override suspend fun deleteToken(token: String): Response<Unit> {
        return fcmTokenService.deleteToken(
            request = FcmTokenDeleteRequest(token = token)
        )
    }
}

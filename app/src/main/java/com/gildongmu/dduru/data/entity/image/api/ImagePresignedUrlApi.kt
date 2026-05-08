package com.gildongmu.dduru.data.entity.image.api

import com.gildongmu.dduru.data.entity.image.model.ImagePresignedUrlRequest
import com.gildongmu.dduru.data.entity.image.model.ImagePresignedUrlResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ImagePresignedUrlApi {
    @POST("api/v1/images/profiles/presigned-url")
    suspend fun createPresignedUrl(
        @Body request: ImagePresignedUrlRequest
    ): ApiResponse<List<ImagePresignedUrlResponse>>
}

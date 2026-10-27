package com.gildongmu.dduru.data.entity.image.api

import com.gildongmu.dduru.data.entity.image.model.ImagePresignedUrlResponse
import com.gildongmu.dduru.data.entity.image.model.ImageUploadRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ImagePresignedUrlApi {
    @POST("api/v1/images/profiles/presigned-url")
    suspend fun createProfilePresignedUrl(
        @Body request: ImageUploadRequest
    ): ApiResponse<List<ImagePresignedUrlResponse>>

    @POST("api/v1/images/chats/presigned-url")
    suspend fun createChatPresignedUrls(
        @Body request: ImageUploadRequest
    ): ApiResponse<List<ImagePresignedUrlResponse>>

    @POST("api/v1/images/journeys/presigned-url")
    suspend fun createJourneyPresignedUrl(
        @Body request: ImageUploadRequest
    ): ApiResponse<List<ImagePresignedUrlResponse>>
}

package com.gildongmu.dduru.data.createPost.api

import com.gildongmu.dduru.data.createPost.model.CreatePostRequest
import com.gildongmu.dduru.data.createPost.model.CreatePostResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CreatePostApi {
    @POST("/api/v1/posts")
    suspend fun createPost(
        @Body request: CreatePostRequest
    ): ApiResponse<CreatePostResponse>
}

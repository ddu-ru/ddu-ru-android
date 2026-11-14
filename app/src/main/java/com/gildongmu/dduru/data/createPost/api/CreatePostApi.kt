package com.gildongmu.dduru.data.createPost.api

import com.gildongmu.dduru.data.createPost.model.CreatePostRequest
import com.gildongmu.dduru.data.createPost.model.CreatePostResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CreatePostApi {
    @POST("/api/v1/posts")
    suspend fun createPost(
        @Body request: CreatePostRequest
    ): ApiResponse<CreatePostResponse>

    @PATCH("/api/v1/posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Long,
        @Body request: CreatePostRequest
    ): Response<Unit>
}

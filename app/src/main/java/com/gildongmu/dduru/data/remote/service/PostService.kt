package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.post.ParticipationRequest
import com.gildongmu.dduru.data.remote.dto.response.post.PostDetailResponse
import com.gildongmu.dduru.data.remote.dto.response.post.PostListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

interface PostService {
    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("cursor") cursor: Long? = null,
        @Query("cursorValue") cursorValue: Int? = null,
        @Query("keyword") keyword: String? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("preferredGender") preferredGender: String? = null,
        @Query("minAge") minAge: Int? = null,
        @Query("maxAge") maxAge: Int? = null,
        @Query("destinationId") destinationId: Long? = null,
        @Query("minRecruitCapacity") minRecruitCapacity: Int? = null,
        @Query("maxRecruitCapacity") maxRecruitCapacity: Int? = null,
        @Query("recruitmentStatus") recruitmentStatus: String? = null,
        @Query("companionType") companionType: String? = null,
        @Query("sort") sort: String? = null
    ): ApiResponse<PostListResponse>

    @GET("api/v1/posts/{postId}")
    suspend fun getPost(@Path("postId") postId: Long): ApiResponse<PostDetailResponse>

    @POST("api/v1/posts/{postId}/likes")
    suspend fun toggleLike(@Path("postId") postId: Long): Response<Unit>

    @POST("api/v1/posts/{postId}/participations")
    suspend fun createParticipation(
        @Path("postId") postId: Long,
        @Body request: ParticipationRequest
    ): ApiResponse<Unit>
}

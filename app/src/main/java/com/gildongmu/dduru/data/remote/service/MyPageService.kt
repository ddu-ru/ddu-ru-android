package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.mypage.TravelPreferenceUpdateRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.DestinationPreferenceSearchResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyPageLikedPostListResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyPagePostListResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyProfileResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.TravelPreferenceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface MyPageService {
    @GET("api/v1/users/me/liked-posts")
    suspend fun getLikedPosts(
        @Query("cursor") cursor: Long? = null,
        @Query("size") size: Int? = null
    ): ApiResponse<MyPageLikedPostListResponse>

    @GET("api/v1/users/me/posts")
    suspend fun getMyPosts(
        @Query("cursor") cursor: Long? = null,
        @Query("size") size: Int? = null,
        @Query("status") status: String? = null
    ): ApiResponse<MyPagePostListResponse>

    @GET("api/v1/users/me/travel-preferences")
    suspend fun getTravelPreferences(): ApiResponse<TravelPreferenceResponse>

    @PATCH("api/v1/users/me/travel-preferences")
    suspend fun updateTravelPreferences(
        @Body request: TravelPreferenceUpdateRequest
    ): Response<Unit>

    @GET("api/v1/destinations/preference-search")
    suspend fun searchPreferenceDestinations(
        @Query("keyword") keyword: String
    ): ApiResponse<List<DestinationPreferenceSearchResponse>>

    @GET("api/v1/users/me/profile")
    suspend fun getMyProfile(): ApiResponse<MyProfileResponse>
}

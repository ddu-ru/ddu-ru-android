package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MySuperHostStatusResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.SuperHostApplyResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SuperHostService {
    @GET("api/v1/posts/super-hosts/me")
    suspend fun getMySuperHostStatus(): ApiResponse<MySuperHostStatusResponse>

    @POST("api/v1/posts/{postId}/super-host")
    suspend fun applySuperHost(@Path("postId") postId: Long): ApiResponse<SuperHostApplyResponse>
}

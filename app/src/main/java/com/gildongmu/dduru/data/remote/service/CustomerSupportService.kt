package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.mypage.InquiryWriteRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.FaqResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.InquiryDetailResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.InquiryListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomerSupportService {
    @GET("api/v1/faqs")
    suspend fun getFaqs(
        @Query("category") category: String? = null,
        @Query("keyword") keyword: String? = null
    ): ApiResponse<List<FaqResponse>>

    @GET("api/v1/inquiries")
    suspend fun getInquiries(
        @Query("cursor") cursor: Long? = null,
        @Query("size") size: Int? = null
    ): ApiResponse<InquiryListResponse>

    @GET("api/v1/inquiries/{id}")
    suspend fun getInquiry(@Path("id") id: Long): ApiResponse<InquiryDetailResponse>

    @POST("api/v1/inquiries")
    suspend fun createInquiry(@Body request: InquiryWriteRequest): ApiResponse<InquiryDetailResponse>

    @PUT("api/v1/inquiries/{id}")
    suspend fun updateInquiry(
        @Path("id") id: Long,
        @Body request: InquiryWriteRequest
    ): ApiResponse<InquiryDetailResponse>
}

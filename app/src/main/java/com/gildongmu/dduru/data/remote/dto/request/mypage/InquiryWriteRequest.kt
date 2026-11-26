package com.gildongmu.dduru.data.remote.dto.request.mypage

import kotlinx.serialization.Serializable

@Serializable
data class InquiryWriteRequest(
    val category: String,
    val content: String,
    val imageUrls: List<String> = emptyList()
)

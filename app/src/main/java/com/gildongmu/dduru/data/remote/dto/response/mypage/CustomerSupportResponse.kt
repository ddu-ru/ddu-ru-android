package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class FaqResponse(
    val id: Long,
    val category: String = "",
    val question: String = "",
    val answer: String = ""
)

@Serializable
data class InquiryListResponse(
    val inquiries: List<InquirySummaryResponse> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val size: Int = 0
)

@Serializable
data class InquirySummaryResponse(
    val id: Long,
    val category: String = "",
    val content: String = "",
    val status: String = "",
    val createdAt: String = ""
)

@Serializable
data class InquiryDetailResponse(
    val id: Long,
    val category: String = "",
    val content: String = "",
    val status: String = "",
    val createdAt: String = "",
    val modifiedAt: String? = null,
    val imageUrls: List<String> = emptyList(),
    val editable: Boolean = false,
    val answer: InquiryAnswerResponse? = null
)

@Serializable
data class InquiryAnswerResponse(
    val content: String = "",
    val answeredAt: String = "",
    val modifiedAt: String? = null
)

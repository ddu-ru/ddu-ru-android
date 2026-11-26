package com.gildongmu.dduru.domain.entity.mypage

import java.time.LocalDateTime

enum class FaqCategory { SERVICE, ACCOUNT, MATCHING, SECURITY }

enum class InquiryType { SERVICE, ACCOUNT, MATCHING, SECURITY, BUG, OTHER }

enum class InquiryState { WAITING, ANSWERED }

data class Faq(
    val id: Long,
    val category: FaqCategory?,
    val question: String,
    val answer: String
)

data class Inquiry(
    val id: Long,
    val type: InquiryType?,
    val content: String,
    val state: InquiryState,
    val createdAt: LocalDateTime?
)

data class InquiryPage(
    val inquiries: List<Inquiry>,
    val nextCursor: Long?,
    val hasNext: Boolean
)

data class InquiryDetail(
    val id: Long,
    val type: InquiryType?,
    val content: String,
    val state: InquiryState,
    val createdAt: LocalDateTime?,
    val imageUrls: List<String>,
    val editable: Boolean,
    val answer: String?,
    val answeredAt: LocalDateTime?
)

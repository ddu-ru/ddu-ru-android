package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.mypage.Faq
import com.gildongmu.dduru.domain.entity.mypage.FaqCategory
import com.gildongmu.dduru.domain.entity.mypage.InquiryDetail
import com.gildongmu.dduru.domain.entity.mypage.InquiryPage
import com.gildongmu.dduru.domain.entity.mypage.InquiryType

interface CustomerSupportRepository {
    suspend fun getFaqs(category: FaqCategory?, keyword: String?): Result<List<Faq>>
    suspend fun getInquiries(cursor: Long?): Result<InquiryPage>
    suspend fun getInquiry(id: Long): Result<InquiryDetail>
    suspend fun createInquiry(type: InquiryType, content: String, imageUrls: List<String>): Result<InquiryDetail>
    suspend fun updateInquiry(
        id: Long,
        type: InquiryType,
        content: String,
        imageUrls: List<String>
    ): Result<InquiryDetail>
}

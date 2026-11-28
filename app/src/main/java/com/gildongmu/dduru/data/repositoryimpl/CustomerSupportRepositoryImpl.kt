package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.request.mypage.InquiryWriteRequest
import com.gildongmu.dduru.data.remote.dto.response.mypage.InquiryDetailResponse
import com.gildongmu.dduru.data.remote.service.CustomerSupportService
import com.gildongmu.dduru.domain.entity.mypage.Faq
import com.gildongmu.dduru.domain.entity.mypage.FaqCategory
import com.gildongmu.dduru.domain.entity.mypage.Inquiry
import com.gildongmu.dduru.domain.entity.mypage.InquiryDetail
import com.gildongmu.dduru.domain.entity.mypage.InquiryPage
import com.gildongmu.dduru.domain.entity.mypage.InquiryState
import com.gildongmu.dduru.domain.entity.mypage.InquiryType
import com.gildongmu.dduru.domain.repository.CustomerSupportRepository
import javax.inject.Inject

private const val INQUIRY_PAGE_SIZE = 20

class CustomerSupportRepositoryImpl @Inject constructor(
    private val customerSupportService: CustomerSupportService
) : CustomerSupportRepository {

    override suspend fun getFaqs(category: FaqCategory?, keyword: String?): Result<List<Faq>> = apiRequest {
        customerSupportService.getFaqs(category?.name, keyword?.trim()?.takeIf { it.isNotEmpty() })
            .requireData()
            .map { faq ->
                Faq(
                    id = faq.id,
                    category = FaqCategory.entries.firstOrNull { it.name == faq.category },
                    question = faq.question,
                    answer = faq.answer
                )
            }
    }

    override suspend fun getInquiries(cursor: Long?): Result<InquiryPage> = apiRequest {
        val data = customerSupportService.getInquiries(cursor, INQUIRY_PAGE_SIZE).requireData()
        InquiryPage(
            inquiries = data.inquiries.map {
                Inquiry(
                    id = it.id,
                    type = it.category.toInquiryType(),
                    content = it.content,
                    state = it.status.toInquiryState(),
                    createdAt = it.createdAt.toLocalDateTimeOrNull()
                )
            },
            nextCursor = data.nextCursor,
            hasNext = data.hasNext
        )
    }

    override suspend fun getInquiry(id: Long): Result<InquiryDetail> = apiRequest {
        customerSupportService.getInquiry(id).requireData().toEntity()
    }

    override suspend fun createInquiry(
        type: InquiryType,
        content: String,
        imageUrls: List<String>
    ): Result<InquiryDetail> = apiRequest {
        customerSupportService.createInquiry(InquiryWriteRequest(type.name, content, imageUrls))
            .requireData()
            .toEntity()
    }

    override suspend fun updateInquiry(
        id: Long,
        type: InquiryType,
        content: String,
        imageUrls: List<String>
    ): Result<InquiryDetail> = apiRequest {
        customerSupportService.updateInquiry(id, InquiryWriteRequest(type.name, content, imageUrls))
            .requireData()
            .toEntity()
    }
}

private fun InquiryDetailResponse.toEntity() = InquiryDetail(
    id = id,
    type = category.toInquiryType(),
    content = content,
    state = status.toInquiryState(),
    createdAt = createdAt.toLocalDateTimeOrNull(),
    imageUrls = imageUrls,
    editable = editable,
    answer = answer?.content,
    answeredAt = answer?.answeredAt.toLocalDateTimeOrNull()
)

private fun String.toInquiryType(): InquiryType? = InquiryType.entries.firstOrNull { it.name == this }

private fun String.toInquiryState(): InquiryState =
    InquiryState.entries.firstOrNull { it.name == this } ?: InquiryState.WAITING

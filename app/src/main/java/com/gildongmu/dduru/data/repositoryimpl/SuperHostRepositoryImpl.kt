package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.response.mypage.SuperHostManagePostResponse
import com.gildongmu.dduru.data.remote.service.SuperHostService
import com.gildongmu.dduru.domain.entity.mypage.SuperHostApplyResult
import com.gildongmu.dduru.domain.entity.mypage.SuperHostPost
import com.gildongmu.dduru.domain.entity.mypage.SuperHostStatus
import com.gildongmu.dduru.domain.repository.SuperHostRepository
import javax.inject.Inject

class SuperHostRepositoryImpl @Inject constructor(
    private val superHostService: SuperHostService
) : SuperHostRepository {

    override suspend fun getMyStatus(): Result<SuperHostStatus> = apiRequest {
        val data = superHostService.getMySuperHostStatus().requireData()
        SuperHostStatus(
            unusedTicketCount = data.unusedTicketCount,
            appliedPosts = data.appliedPosts.map { it.toEntity() },
            availablePosts = data.availablePosts.map { it.toEntity() }
        )
    }

    override suspend fun applyTicket(postId: Long): Result<SuperHostApplyResult> = apiRequest {
        val data = superHostService.applySuperHost(postId).requireData()
        SuperHostApplyResult(
            postId = data.postId,
            endsAt = data.endsAt.toLocalDateTimeOrNull(),
            unusedTicketCount = data.unusedTicketCount
        )
    }
}

private fun SuperHostManagePostResponse.toEntity() = SuperHostPost(
    postId = postId,
    title = title,
    destination = destination,
    startDate = startDate,
    endDate = endDate,
    recruitCount = recruitCount,
    recruitCapacity = recruitCapacity,
    photoUrl = photoUrl,
    viewCount = viewCount,
    superHostEndsAt = superHostEndsAt.toLocalDateTimeOrNull()
)

package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.mypage.SuperHostApplyResult
import com.gildongmu.dduru.domain.entity.mypage.SuperHostStatus

interface SuperHostRepository {
    suspend fun getMyStatus(): Result<SuperHostStatus>
    suspend fun applyTicket(postId: Long): Result<SuperHostApplyResult>
}

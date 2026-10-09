package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.journey.JourneyPost
import com.gildongmu.dduru.domain.entity.journey.JourneyPostCommand
import com.gildongmu.dduru.domain.entity.journey.JourneyPostNotice
import com.gildongmu.dduru.domain.entity.journey.JourneyPostPage

interface JourneyPostRepository {
    suspend fun getJourneyPosts(
        journeyId: Long,
        cursor: Long? = null,
        size: Int? = null
    ): Result<JourneyPostPage>

    suspend fun createJourneyPost(journeyId: Long, command: JourneyPostCommand): Result<JourneyPost>
    suspend fun getJourneyPost(journeyId: Long, journeyPostId: Long): Result<JourneyPost>
    suspend fun updateJourneyPost(
        journeyId: Long,
        journeyPostId: Long,
        command: JourneyPostCommand
    ): Result<JourneyPost>

    suspend fun deleteJourneyPost(journeyId: Long, journeyPostId: Long): Result<Unit>
    suspend fun updateJourneyPostNotice(
        journeyId: Long,
        journeyPostId: Long,
        isNotice: Boolean
    ): Result<JourneyPostNotice>
}

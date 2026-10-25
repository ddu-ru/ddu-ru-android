package com.gildongmu.dduru.domain.journey.repository

import com.gildongmu.dduru.domain.journey.entity.JourneyPost
import com.gildongmu.dduru.domain.journey.entity.JourneyPostCommand
import com.gildongmu.dduru.domain.journey.entity.JourneyPostNotice
import com.gildongmu.dduru.domain.journey.entity.JourneyPostPage

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

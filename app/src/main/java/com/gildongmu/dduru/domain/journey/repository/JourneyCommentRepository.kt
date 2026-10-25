package com.gildongmu.dduru.domain.journey.repository

import com.gildongmu.dduru.domain.journey.entity.JourneyComment
import com.gildongmu.dduru.domain.journey.entity.JourneyCommentCommand
import com.gildongmu.dduru.domain.journey.entity.JourneyCommentList

interface JourneyCommentRepository {
    suspend fun getJourneyComments(journeyId: Long, journeyPostId: Long): Result<JourneyCommentList>
    suspend fun createJourneyComment(
        journeyId: Long,
        journeyPostId: Long,
        command: JourneyCommentCommand
    ): Result<JourneyComment>

    suspend fun updateJourneyComment(
        journeyId: Long,
        journeyPostId: Long,
        commentId: Long,
        command: JourneyCommentCommand
    ): Result<JourneyComment>

    suspend fun deleteJourneyComment(
        journeyId: Long,
        journeyPostId: Long,
        commentId: Long
    ): Result<Unit>
}

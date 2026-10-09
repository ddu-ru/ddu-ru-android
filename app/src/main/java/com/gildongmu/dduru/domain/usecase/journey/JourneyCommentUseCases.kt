package com.gildongmu.dduru.domain.usecase.journey

import com.gildongmu.dduru.domain.entity.journey.JourneyComment
import com.gildongmu.dduru.domain.entity.journey.JourneyCommentCommand
import com.gildongmu.dduru.domain.entity.journey.JourneyCommentList
import com.gildongmu.dduru.domain.repository.JourneyCommentRepository
import javax.inject.Inject

class GetJourneyCommentsUseCase @Inject constructor(
    private val repository: JourneyCommentRepository
) {
    suspend operator fun invoke(journeyId: Long, journeyPostId: Long): Result<JourneyCommentList> =
        repository.getJourneyComments(journeyId, journeyPostId)
}

class CreateJourneyCommentUseCase @Inject constructor(
    private val repository: JourneyCommentRepository
) {
    suspend operator fun invoke(
        journeyId: Long,
        journeyPostId: Long,
        command: JourneyCommentCommand
    ): Result<JourneyComment> = repository.createJourneyComment(journeyId, journeyPostId, command)
}

class UpdateJourneyCommentUseCase @Inject constructor(
    private val repository: JourneyCommentRepository
) {
    suspend operator fun invoke(
        journeyId: Long,
        journeyPostId: Long,
        commentId: Long,
        command: JourneyCommentCommand
    ): Result<JourneyComment> = repository.updateJourneyComment(journeyId, journeyPostId, commentId, command)
}

class DeleteJourneyCommentUseCase @Inject constructor(
    private val repository: JourneyCommentRepository
) {
    suspend operator fun invoke(journeyId: Long, journeyPostId: Long, commentId: Long): Result<Unit> =
        repository.deleteJourneyComment(journeyId, journeyPostId, commentId)
}

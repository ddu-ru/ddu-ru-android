package com.gildongmu.dduru.domain.usecase.journey

import com.gildongmu.dduru.domain.entity.journey.JourneyPost
import com.gildongmu.dduru.domain.entity.journey.JourneyPostCommand
import com.gildongmu.dduru.domain.entity.journey.JourneyPostNotice
import com.gildongmu.dduru.domain.entity.journey.JourneyPostPage
import com.gildongmu.dduru.domain.repository.JourneyPostRepository
import javax.inject.Inject

class GetJourneyPostsUseCase @Inject constructor(
    private val repository: JourneyPostRepository
) {
    suspend operator fun invoke(journeyId: Long, cursor: Long? = null, size: Int? = null): Result<JourneyPostPage> =
        repository.getJourneyPosts(journeyId, cursor, size)
}

class CreateJourneyPostUseCase @Inject constructor(
    private val repository: JourneyPostRepository
) {
    suspend operator fun invoke(journeyId: Long, command: JourneyPostCommand): Result<JourneyPost> =
        repository.createJourneyPost(journeyId, command)
}

class GetJourneyPostUseCase @Inject constructor(
    private val repository: JourneyPostRepository
) {
    suspend operator fun invoke(journeyId: Long, journeyPostId: Long): Result<JourneyPost> =
        repository.getJourneyPost(journeyId, journeyPostId)
}

class UpdateJourneyPostUseCase @Inject constructor(
    private val repository: JourneyPostRepository
) {
    suspend operator fun invoke(
        journeyId: Long,
        journeyPostId: Long,
        command: JourneyPostCommand
    ): Result<JourneyPost> = repository.updateJourneyPost(journeyId, journeyPostId, command)
}

class DeleteJourneyPostUseCase @Inject constructor(
    private val repository: JourneyPostRepository
) {
    suspend operator fun invoke(journeyId: Long, journeyPostId: Long): Result<Unit> =
        repository.deleteJourneyPost(journeyId, journeyPostId)
}

class UpdateJourneyPostNoticeUseCase @Inject constructor(
    private val repository: JourneyPostRepository
) {
    suspend operator fun invoke(
        journeyId: Long,
        journeyPostId: Long,
        isNotice: Boolean
    ): Result<JourneyPostNotice> = repository.updateJourneyPostNotice(journeyId, journeyPostId, isNotice)
}

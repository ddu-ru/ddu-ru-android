package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

data class ParticipationItem(
    val participationId: Long,
    val postId: Long,
    val postTitle: String,
    val photoUrl: String?,
    val status: ParticipationStatus,
    val appliedAt: LocalDateTime,
    val cancellable: Boolean,
    val privateRoomId: Long?,
    val groupRoomId: Long?
)

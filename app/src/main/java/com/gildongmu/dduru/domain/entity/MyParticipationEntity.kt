package com.gildongmu.dduru.domain.entity

data class MyParticipationEntity(
    val participationId: Long,
    val postId: Long,
    val postTitle: String,
    val status: String,
    val appliedAt: String,
    val cancellable: Boolean,
    val privateRoomId: Long?,
    val groupRoomId: Long?
)

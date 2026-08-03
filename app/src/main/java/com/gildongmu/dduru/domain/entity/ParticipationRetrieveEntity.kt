package com.gildongmu.dduru.domain.entity

data class ParticipationRetrieveEntity(
    val participationId: Long,
    val userId: Long,
    val userName: String,
    val profileImage: String?,
    val message: String?,
    val status: String,
    val appliedAt: String,
    val postId: Long,
    val postTitle: String
)

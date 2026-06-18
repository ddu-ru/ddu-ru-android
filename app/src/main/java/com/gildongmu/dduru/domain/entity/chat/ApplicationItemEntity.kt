package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

data class ApplicationItemEntity(
    val applicationId: Long,
    val postTitle: String,
    val applicantNickname: String,
    val status: ApplicationStatus,
    val appliedAt: LocalDateTime
)

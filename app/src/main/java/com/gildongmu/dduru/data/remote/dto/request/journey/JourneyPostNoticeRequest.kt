package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyPostNoticeRequest(
    val isNotice: Boolean
)

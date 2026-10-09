package com.gildongmu.dduru.domain.entity.journey

data class JourneyApiException(
    val status: Int? = null,
    val errorCode: String? = null,
    override val message: String? = null
) : RuntimeException(message)

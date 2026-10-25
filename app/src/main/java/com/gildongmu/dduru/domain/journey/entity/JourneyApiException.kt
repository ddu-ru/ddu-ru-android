package com.gildongmu.dduru.domain.journey.entity

data class JourneyApiException(
    val status: Int? = null,
    val errorCode: String? = null,
    override val message: String? = null
) : RuntimeException(message)

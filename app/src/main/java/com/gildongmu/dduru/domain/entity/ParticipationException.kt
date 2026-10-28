package com.gildongmu.dduru.domain.entity

class ParticipationException(
    val errorCode: String?,
    override val message: String?
) : Exception(message)

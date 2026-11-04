package com.gildongmu.dduru.domain.entity

data class ExceptionResponse(
    val status: Int,
    val errorCode: String? = null,
    val field: String? = null,
    val retryAfterSeconds: Long? = null
) : RuntimeException()

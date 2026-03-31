package com.gildongmu.dduru.domain.entity

data class ExceptionResponse(
    val status: Int
) : RuntimeException()

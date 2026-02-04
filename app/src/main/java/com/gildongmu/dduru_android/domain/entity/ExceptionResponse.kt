package com.gildongmu.dduru_android.domain.entity

data class ExceptionResponse(
    val status: Int
) : RuntimeException()
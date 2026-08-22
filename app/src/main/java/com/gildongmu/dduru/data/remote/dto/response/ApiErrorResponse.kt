package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val errorCode: String,
    val message: String
)

package com.gildongmu.dduru.data.remote

import com.gildongmu.dduru.domain.entity.ExceptionResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException

internal fun HttpException.toExceptionResponse(json: Json): ExceptionResponse {
    val errorCode = response()?.errorBody()?.string()?.let { body ->
        runCatching {
            json.parseToJsonElement(body).jsonObject["data"]
                ?.jsonObject?.get("errorCode")
                ?.jsonPrimitive?.contentOrNull
        }.getOrNull()
    }
    return ExceptionResponse(status = code(), errorCode = errorCode)
}

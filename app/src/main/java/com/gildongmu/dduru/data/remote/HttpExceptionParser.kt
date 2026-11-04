package com.gildongmu.dduru.data.remote

import com.gildongmu.dduru.domain.entity.ExceptionResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import retrofit2.Response

internal fun HttpException.toExceptionResponse(json: Json): ExceptionResponse =
    response()?.toExceptionResponse(json) ?: ExceptionResponse(status = code())

internal fun Response<*>.toExceptionResponse(json: Json): ExceptionResponse {
    val data = errorBody()?.string()?.let { body ->
        runCatching { json.parseToJsonElement(body).jsonObject["data"]?.jsonObject }.getOrNull()
    }
    return ExceptionResponse(
        status = code(),
        errorCode = data?.get("errorCode")?.jsonPrimitive?.contentOrNull,
        field = data?.get("field")?.jsonPrimitive?.contentOrNull,
        retryAfterSeconds = headers()["Retry-After"]?.toLongOrNull()?.takeIf { it >= 0 }
    )
}

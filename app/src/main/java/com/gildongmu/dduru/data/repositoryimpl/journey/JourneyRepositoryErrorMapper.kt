package com.gildongmu.dduru.data.repositoryimpl.journey

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.domain.journey.entity.JourneyApiException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import retrofit2.Response

internal fun ApiResponse<*>.toJourneyApiException(): JourneyApiException =
    JourneyApiException(status = status)

internal fun Response<*>.toJourneyApiException(json: Json): JourneyApiException {
    val errorBody = errorBody()?.string() ?: return JourneyApiException(status = code())
    return json.parseJourneyApiException(errorBody, code())
}

internal fun HttpException.toJourneyApiException(json: Json): JourneyApiException {
    val errorBody = response()?.errorBody()?.string() ?: return JourneyApiException(status = code())
    return json.parseJourneyApiException(errorBody, code())
}

private fun Json.parseJourneyApiException(errorBody: String, status: Int): JourneyApiException =
    runCatching {
        val error = parseToJsonElement(errorBody).jsonObject
        JourneyApiException(
            status = error["status"]?.jsonPrimitive?.content?.toIntOrNull() ?: status,
            errorCode = error["code"]?.jsonPrimitive?.content
                ?: error["errorCode"]?.jsonPrimitive?.content,
            message = error["message"]?.jsonPrimitive?.content
                ?: error["errorMessage"]?.jsonPrimitive?.content
        )
    }.getOrElse {
        JourneyApiException(status = status)
    }

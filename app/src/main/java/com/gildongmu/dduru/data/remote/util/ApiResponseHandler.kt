package com.gildongmu.dduru.data.remote.util

import com.gildongmu.dduru.data.remote.dto.response.ApiErrorResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import java.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import retrofit2.HttpException

internal suspend inline fun <reified Response, Domain> requestApi(
    crossinline request: suspend () -> ApiResponse<JsonElement>,
    crossinline mapper: (Response) -> Domain
): Result<Domain> {
    return try {
        val response = request()
        val data = response.data ?: return Result.failure(Exception("서버 응답에 데이터가 없습니다."))

        if (response.status == API_SUCCESS_STATUS) {
            val successBody = ApiResponseJson.decodeFromJsonElement<Response>(data)
            Result.success(mapper(successBody))
        } else {
            val errorBody = ApiResponseJson.decodeFromJsonElement<ApiErrorResponse>(data)
            Result.failure(ApiErrorException(response.status, errorBody.errorCode, errorBody.message))
        }
    } catch (e: ApiErrorException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
    } catch (e: IOException) {
        Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
    } catch (e: Exception) {
        Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
    }
}

class ApiErrorException(
    val status: Int,
    val errorCode: String,
    override val message: String
) : Exception(message)

@PublishedApi
internal val ApiResponseJson: Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
}

@PublishedApi
internal const val API_SUCCESS_STATUS = 200

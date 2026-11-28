package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import java.time.LocalDateTime
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException
import retrofit2.Response

internal suspend fun <T> apiRequest(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Result.failure(e)
}

internal fun <T> ApiResponse<T>.requireData(): T {
    if (status !in 200..299) throw IllegalStateException("Status $status")
    return data ?: throw IllegalStateException("Response data is null")
}

internal fun Response<Unit>.requireSuccess() {
    if (!isSuccessful) throw HttpException(this)
}

internal fun String?.toLocalDateTimeOrNull(): LocalDateTime? =
    this?.let { runCatching { LocalDateTime.parse(it) }.getOrNull() }

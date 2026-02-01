package gildong.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val status: Int,
    val data: T?
)

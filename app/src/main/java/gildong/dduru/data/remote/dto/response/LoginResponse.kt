package gildong.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean = false
)

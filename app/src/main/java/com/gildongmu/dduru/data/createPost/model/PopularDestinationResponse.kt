package com.gildongmu.dduru.data.createPost.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularDestinationResponse(
    @SerialName("id") val id: Long,
    @SerialName("countryCode") val countryCode: String,
    @SerialName("countryName") val countryName: String,
    @SerialName("city") val city: String,
    @SerialName("image") val image: String? = null
)

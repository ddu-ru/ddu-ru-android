package com.gildongmu.dduru.data.entity.image.model

import kotlinx.serialization.Serializable

@Serializable
data class ImagePresignedUrlResponse(
    val presignedUrl: String,
    val fileUrl: String
)

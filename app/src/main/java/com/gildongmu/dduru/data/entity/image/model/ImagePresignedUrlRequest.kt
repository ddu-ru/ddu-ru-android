package com.gildongmu.dduru.data.entity.image.model

import kotlinx.serialization.Serializable

@Serializable
data class ImagePresignedUrlRequest(
    val fileNames: List<String>
)

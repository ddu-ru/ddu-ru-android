package com.gildongmu.dduru.data.entity.image.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadRequest(
    val files: List<ImageUploadFileRequest>
)

@Serializable
data class ImageUploadFileRequest(
    val fileName: String,
    val fileSize: Long
)

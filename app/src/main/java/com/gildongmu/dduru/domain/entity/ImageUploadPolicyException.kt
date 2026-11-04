package com.gildongmu.dduru.domain.entity

enum class ImageUploadPolicyReason {
    UNSUPPORTED_FORMAT,
    TOO_LARGE,
    TOO_MANY
}

class ImageUploadPolicyException(
    val reason: ImageUploadPolicyReason
) : RuntimeException()

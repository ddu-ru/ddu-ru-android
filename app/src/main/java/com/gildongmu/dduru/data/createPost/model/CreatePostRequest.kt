package com.gildongmu.dduru.data.createPost.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val destinationId: Long,
    val title: String,
    val content: String,
    val startDate: String,
    val endDate: String,
    val recruitCapacity: Int,
    val preferredGender: String,
    val isAgeAny: Boolean,
    val minAge: Int?,
    val maxAge: Int?,
    val photoUrl: String?,
    val tags: List<String>,
    val companionType: String
)

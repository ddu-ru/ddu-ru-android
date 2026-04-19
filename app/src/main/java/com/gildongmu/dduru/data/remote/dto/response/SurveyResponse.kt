package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyResponse(
    val r: Double,
    val w: Double,
    val s: Double,
    val p: Double,
    val avatarCode: Int,
    val avatarType: String,
    val avatarName: String,
    val avatarDescription: String,
    val personality: String,
    val strength: String,
    val tip: String,
    val tags: List<String>
)

package com.gildongmu.dduru.domain.entity

data class SurveyResult(
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

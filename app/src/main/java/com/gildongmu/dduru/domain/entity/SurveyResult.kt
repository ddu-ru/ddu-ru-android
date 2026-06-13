package com.gildongmu.dduru.domain.entity

data class SurveyResult(
    val rhythmScore: Double,
    val energyScore: Double,
    val consumptionScore: Double,
    val decisionScore: Double,
    val avatarCode: Int,
    val avatarType: String,
    val recordStyleType: String,
    val avatarLabel: String,
    val characterName: String,
    val speechBubbleText: String,
    val tags: List<String>,
    val descriptionLine1: String,
    val descriptionLine2: String,
    val descriptionLine3: String,
    val imageUrl: String?
)

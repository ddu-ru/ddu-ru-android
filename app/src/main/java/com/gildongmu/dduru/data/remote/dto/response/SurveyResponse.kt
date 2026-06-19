package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyResponse(
    val tendencyScores: TendencyScoresDto,
    val avatarCode: Int,
    val avatarType: String,
    val recordStyleType: String,
    val avatarLabel: String,
    val avatarProfile: AvatarProfileDto
)

@Serializable
data class TendencyScoresDto(
    val rhythmScore: Double,
    val energyScore: Double,
    val consumptionScore: Double,
    val decisionScore: Double
)

@Serializable
data class AvatarProfileDto(
    val characterName: String,
    val speechBubbleText: String,
    val tags: List<String>,
    val descriptionLine1: String,
    val descriptionLine2: String,
    val descriptionLine3: String,
    val imageUrl: String?
)

package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.QuestionItem
import com.gildongmu.dduru.data.remote.dto.response.SurveyQuestionsResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.domain.entity.Question
import com.gildongmu.dduru.domain.entity.QuestionOption
import com.gildongmu.dduru.domain.entity.QuestionType
import com.gildongmu.dduru.domain.entity.SurveyAnswers
import com.gildongmu.dduru.domain.entity.SurveyQuestions
import com.gildongmu.dduru.domain.entity.SurveyResult

fun SurveyResponse.toDomain(): SurveyResult {
    return SurveyResult(
        rhythmScore = tendencyScores.rhythmScore,
        energyScore = tendencyScores.energyScore,
        consumptionScore = tendencyScores.consumptionScore,
        decisionScore = tendencyScores.decisionScore,
        avatarCode = avatarCode,
        avatarType = avatarType,
        recordStyleType = recordStyleType,
        avatarLabel = avatarLabel,
        characterName = avatarProfile.characterName,
        speechBubbleText = avatarProfile.speechBubbleText,
        tags = avatarProfile.tags,
        descriptionLine1 = avatarProfile.descriptionLine1,
        descriptionLine2 = avatarProfile.descriptionLine2,
        descriptionLine3 = avatarProfile.descriptionLine3,
        imageUrl = avatarProfile.imageUrl
    )
}

fun SurveyQuestionsResponse.toDomain(): SurveyQuestions {
    return SurveyQuestions(
        version = version,
        count = count,
        questions = questions.map { it.toDomain() }
    )
}

fun QuestionItem.toDomain(): Question {
    return Question(
        id = id,
        displayOrder = displayOrder,
        questionText = questionText,
        imageUrl = imageUrl,
        type = when (type) {
            "SINGLE" -> QuestionType.SINGLE
            "MULTI" -> QuestionType.MULTI
            else -> QuestionType.SINGLE
        },
        required = required,
        minSelect = minSelect,
        maxSelect = maxSelect,
        options = options.map { it.toDomain() }
    )
}

fun com.gildongmu.dduru.data.remote.dto.response.QuestionOption.toDomain(): QuestionOption {
    return QuestionOption(
        code = code,
        icon = icon,
        text = text
    )
}

fun SurveyAnswers.toRequest(): SurveyRequest {
    return SurveyRequest(
        rhythmQ1 = rhythmQ1,
        rhythmQ2 = rhythmQ2,
        rhythmQ3 = rhythmQ3,
        activityTags = activityTags,
        consumptionQ1 = consumptionQ1,
        consumptionQ2 = consumptionQ2,
        consumptionQ3 = consumptionQ3,
        energyQ1 = energyQ1,
        energyQ2 = energyQ2,
        energyQ3 = energyQ3,
        decisionQ1 = decisionQ1,
        decisionQ2 = decisionQ2,
        decisionQ3 = decisionQ3,
        recordStyle = recordStyle
    )
}

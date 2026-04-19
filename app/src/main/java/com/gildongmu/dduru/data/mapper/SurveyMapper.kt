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
        r = r,
        w = w,
        s = s,
        p = p,
        avatarCode = avatarCode,
        avatarType = avatarType,
        avatarName = avatarName,
        avatarDescription = avatarDescription,
        personality = personality,
        strength = strength,
        tip = tip,
        tags = tags
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
        q1 = q1,
        q2 = q2,
        q3 = q3,
        q4 = q4,
        q5 = q5,
        q6 = q6,
        q7 = q7,
        q8 = q8,
        q9 = q9,
        q10 = q10,
        q11 = q11
    )
}

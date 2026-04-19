package com.gildongmu.dduru.domain.entity

data class SurveyQuestions(
    val version: Int,
    val count: Int,
    val questions: List<Question>
)

data class Question(
    val id: String,
    val displayOrder: Int,
    val questionText: String,
    val imageUrl: String?,
    val type: QuestionType,
    val required: Boolean,
    val minSelect: Int,
    val maxSelect: Int,
    val options: List<QuestionOption>
)

enum class QuestionType {
    SINGLE,
    MULTI
}

data class QuestionOption(
    val code: Int,
    val icon: String,
    val text: String
)

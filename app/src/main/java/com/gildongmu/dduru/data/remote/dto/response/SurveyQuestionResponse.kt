package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurveyQuestionsResponse(
    @SerialName("version") val version: Int,
    @SerialName("count") val count: Int,
    @SerialName("questions") val questions: List<QuestionItem>
)

@Serializable
data class QuestionItem(
    @SerialName("id") val id: String,
    @SerialName("displayOrder") val displayOrder: Int,
    @SerialName("questionText") val questionText: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("type") val type: String,
    @SerialName("required") val required: Boolean,
    @SerialName("minSelect") val minSelect: Int,
    @SerialName("maxSelect") val maxSelect: Int,
    @SerialName("options") val options: List<QuestionOption>
)

@Serializable
data class QuestionOption(
    @SerialName("code") val code: Int,
    @SerialName("icon") val icon: String,
    @SerialName("text") val text: String
)

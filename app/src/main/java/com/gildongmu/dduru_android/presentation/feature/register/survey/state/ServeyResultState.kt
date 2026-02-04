package com.gildongmu.dduru_android.presentation.feature.register.survey.state

data class SurveyResultUiState(
    val bubbleMessage: String = "",
    val imageUrl: String = "",   // 서버면 URL이 더 맞음
    val title: String = "",
    val tags: List<String> = emptyList(),
    val descriptions: List<String> = emptyList()
)
package com.gildongmu.dduru.presentation.feature.register.survey.state

data class SurveyResultState(
    val avatarName: String = "",
    val avatarDescription: String = "",
    val imageUrl: Int = 0,
    val personality: String = "",
    val strength: String = "",
    val tip: String = "",
    val tags: List<String> = emptyList()
)

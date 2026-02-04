package com.gildongmu.dduru_android.presentation.feature.register.survey.state

import com.gildongmu.dduru_android.R

data class SurveyResultUiState(
    val avatarName: String = "",
    val avatarDescription: String = "",
    val imageUrl: Int =  R.drawable.img_penguin_personallity, //현재 더미데이터 사용중
    val personality: String = "",
    val strength: String = "",
    val tip: String = "",
    val tags: List<String> = emptyList(),
)
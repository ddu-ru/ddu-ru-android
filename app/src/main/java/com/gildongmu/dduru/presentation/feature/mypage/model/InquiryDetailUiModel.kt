package com.gildongmu.dduru.presentation.feature.mypage.model

data class InquiryDetailUiModel(
    val category: String,
    val status: InquiryStatus,
    val date: String,
    val title: String,
    val body: String,
    val answer: String? = null,
    val answerDate: String? = null
)

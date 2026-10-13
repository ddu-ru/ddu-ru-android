package com.gildongmu.dduru.presentation.feature.mypage.model

enum class InquiryStatus(val label: String) {
    WAITING("답변 대기"),
    ANSWERED("답변 완료")
}

data class InquiryUiModel(
    val id: Long,
    val category: String,
    val title: String,
    val date: String,
    val status: InquiryStatus
)

package com.gildongmu.dduru.presentation.feature.mypage.model

data class TravelStyleTag(
    val emoji: String,
    val label: String,
    val isActive: Boolean = true
) {
    val text: String
        get() = "$emoji  $label"
}

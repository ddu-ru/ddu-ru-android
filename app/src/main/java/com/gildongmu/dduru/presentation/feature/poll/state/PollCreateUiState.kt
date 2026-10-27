package com.gildongmu.dduru.presentation.feature.poll.state

data class PollCreateUiState(
    val title: String = "",
    val subjects: List<String> = listOf("", ""),
    val multipleChoice: Boolean = false,
    val anonymous: Boolean = false,
    val allowAddOptions: Boolean = false,
    val endDate: String = "",
    val endTime: String = ""
)

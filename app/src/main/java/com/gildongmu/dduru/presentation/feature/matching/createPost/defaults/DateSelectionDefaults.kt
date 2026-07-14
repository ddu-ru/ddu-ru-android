package com.gildongmu.dduru.presentation.feature.matching.createPost.defaults

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateSelectionDefaults {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    fun formatDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }
}

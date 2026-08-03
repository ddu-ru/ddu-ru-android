package com.gildongmu.dduru.core.common.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val KoreanDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

fun LocalDate?.toKoreanDateString(): String? = this?.format(KoreanDateFormatter)

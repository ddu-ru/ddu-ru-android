package com.gildongmu.dduru.core.common.extension

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun String.parseDateTime(): LocalDateTime =
    try {
        ZonedDateTime.parse(this).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    } catch (e: Exception) {
        LocalDateTime.parse(this)
    }

fun LocalDateTime.toRelativeTime(): String {
    val now = LocalDateTime.now()
    val minutes = maxOf(0, ChronoUnit.MINUTES.between(this, now))
    val hours = maxOf(0, ChronoUnit.HOURS.between(this, now))
    val days = maxOf(0, ChronoUnit.DAYS.between(this, now))
    return when {
        minutes < 1 -> "방금"
        hours < 1 -> "${minutes}분 전"
        days < 1 -> "${hours}시간 전"
        else -> "${days}일 전"
    }
}

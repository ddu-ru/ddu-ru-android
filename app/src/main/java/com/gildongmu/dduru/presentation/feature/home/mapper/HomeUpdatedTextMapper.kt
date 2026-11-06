package com.gildongmu.dduru.presentation.feature.home.mapper

import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun String.toUpdatedText(now: Instant = Instant.now()): String {
    val updatedAt = toInstantOrNull() ?: return "방금 전"
    val elapsedMinutes = ChronoUnit.MINUTES.between(updatedAt, now).coerceAtLeast(0)
    return when {
        elapsedMinutes < 1 -> "방금 전"
        elapsedMinutes < 60 -> "${elapsedMinutes}분 전"
        elapsedMinutes < 1_440 -> "${elapsedMinutes / 60}시간 전"
        else -> "${elapsedMinutes / 1_440}일 전"
    }
}

private fun String.toInstantOrNull(): Instant? =
    runCatching { Instant.parse(this) }.getOrNull()
        ?: runCatching { OffsetDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME).toInstant() }.getOrNull()
        ?: runCatching {
            LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
                .atZone(ZoneId.systemDefault())
                .toInstant()
        }.getOrNull()

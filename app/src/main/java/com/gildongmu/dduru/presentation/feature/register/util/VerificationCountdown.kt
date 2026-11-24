package com.gildongmu.dduru.presentation.feature.register.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import kotlin.math.ceil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VerificationCountdown(
    private val scope: CoroutineScope,
    private val onTick: (remainingSeconds: Int) -> Unit
) {
    private var job: Job? = null

    fun start(endAt: Instant) {
        job?.cancel()
        job = scope.launch {
            do {
                val remaining = remainingSeconds(endAt)
                onTick(remaining)
                if (remaining > 0) delay(1000L)
            } while (remaining > 0)
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    private fun remainingSeconds(endAt: Instant): Int {
        val millis = Duration.between(Instant.now(), endAt).toMillis()
        return ceil(millis / 1000.0).toInt().coerceAtLeast(0)
    }
}

object VerificationTimeParser {
    private const val DEFAULT_AUTH_SECONDS = 180L
    private val KST_ZONE = ZoneId.of("Asia/Seoul")

    fun parseExpiresAt(value: String): Instant =
        runCatching { LocalDateTime.parse(value).atZone(KST_ZONE).toInstant() }
            .getOrElse { Instant.now().plusSeconds(DEFAULT_AUTH_SECONDS) }

    fun parseResendAvailableAt(value: String?): Instant? =
        value?.let { runCatching { OffsetDateTime.parse(it).toInstant() }.getOrNull() }
}

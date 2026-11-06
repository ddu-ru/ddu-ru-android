package com.gildongmu.dduru.presentation.feature.home.mapper

import java.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeUpdatedTextMapperTest {
    private val updatedAt = Instant.parse("2026-11-06T10:00:00Z")

    @Test
    fun `less than a minute is shown as just now`() {
        assertEquals("방금 전", updatedAt.toString().toUpdatedText(updatedAt.plusSeconds(59)))
    }

    @Test
    fun `elapsed minutes are shown`() {
        assertEquals("1분 전", updatedAt.toString().toUpdatedText(updatedAt.plusSeconds(60)))
    }

    @Test
    fun `elapsed hours are shown`() {
        assertEquals("1시간 전", updatedAt.toString().toUpdatedText(updatedAt.plusSeconds(3_600)))
    }

    @Test
    fun `elapsed days are shown`() {
        assertEquals("2일 전", updatedAt.toString().toUpdatedText(updatedAt.plusSeconds(172_800)))
    }

    @Test
    fun `invalid timestamp falls back to just now`() {
        assertEquals("방금 전", "not-a-timestamp".toUpdatedText(updatedAt))
    }
}

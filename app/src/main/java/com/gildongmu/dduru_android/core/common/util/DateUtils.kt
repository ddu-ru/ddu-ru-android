package com.gildongmu.dduru_android.core.common.util

import java.time.LocalDate
import java.time.YearMonth

object DateUtils {

    fun getMaxDayOfMonth(year: Int, month: Int): Int {
        return YearMonth.of(year, month).lengthOfMonth()
    }

    fun getDayListInMonth(year: Int?, month: Int?): List<Int> {
        val y = year ?: LocalDate.now().year
        val m = month ?: 1
        val lastDay = getMaxDayOfMonth(y, m)
        return (1..lastDay).toList()
    }

    fun getCorrectedDay(year: Int, month: Int, currentDay: Int): Int {
        val maxDay = getMaxDayOfMonth(year, month)
        return if (currentDay > maxDay) maxDay else currentDay
    }
}
package com.gildongmu.dduru.core.common.extension

fun String.toDotDate(): String = replace("-", ".")

fun String.toDateLabel(includeYear: Boolean): String {
    val parts = split("-")

    if (parts.size != DATE_PART_COUNT) return this

    val year = parts[0]
    val month = parts[1]
    val day = parts[2]

    return if (includeYear) {
        "$year.$month.$day"
    } else {
        "$month.$day"
    }
}

fun formatDateRange(startDate: String, endDate: String): String {
    val start = startDate.toDateLabel(includeYear = true)
    val end = endDate.toDateLabel(includeYear = false)

    return when {
        start.isBlank() && end.isBlank() -> ""
        end.isBlank() -> start
        start.isBlank() -> end
        else -> "$start - $end"
    }
}

private const val DATE_PART_COUNT = 3

package com.gildongmu.dduru.presentation.feature.home.mapper

fun String.toDateLabel(includeYear: Boolean): String {
    val parts = split("-")

    if (parts.size != 3) return this

    val year = parts[0]
    val month = parts[1]
    val day = parts[2]

    return if (includeYear) {
        "$year.$month.$day"
    } else {
        "$month.$day"
    }
}

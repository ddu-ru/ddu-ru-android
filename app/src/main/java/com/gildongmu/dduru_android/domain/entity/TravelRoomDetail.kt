// domain/entity/TravelRoomDetail.kt
package com.gildongmu.dduru_android.domain.entity

data class TravelRoomDetail(
    // GET /api/v1/posts/{postId} 응답 필드 (API 명세서 기준)
    val id: Long,
    val title: String,
    val content: String,

    // 날짜
    val startDate: String,           // "2026-01-02"
    val endDate: String,
    val recruitDeadline: String,
    val createdAt: String,

    // 지역
    val destination: String,         // "제주"
    val destinationId: Long,         // FK to cities

    // 모집
    val isRecruitOpen: Boolean,
    val recruitCapacity: Int,
    val recruitCount: Int,
    val daysLeft: Int,

    // 선호 조건
    val preferredGender: String,     // U/M/F
    val preferredAgeMin: String,     // "10s", "20s", ...
    val preferredAgeMax: String,

    // 예산
    val budgetMin: Int?,
    val budgetMax: Int?,

    // 미디어 & 메타
    val photoUrls: List<String>?,
    val tags: List<String>?,
    val viewCount: Int,
    val likeCount: Int,
    val status: String,              // "OPEN", "FULL", "CLOSED"

    val author: Author
) {
    // === UI 계산 메서드 ===

    fun getDurationText(): String {
        // TODO: startDate, endDate 파싱 후 계산
        return "(3박 4일)"
    }

    fun getDaysLeftBadge(): String {
        return "D-$daysLeft"
    }

    fun getPreferredAgeRange(): String {
        // "10s" -> "10대", "20s" -> "20대"
        val minAge = preferredAgeMin.replace("s", "대")
        val maxAge = preferredAgeMax.replace("s", "대")
        return "$minAge ~ $maxAge"
    }

    fun getRecruitPeriodDays(): String {
        // TODO: recruitDeadline과 현재 날짜 비교
        return "30일"
    }

    fun getCountryName(): String {
        // TODO: cities 테이블 JOIN 시 country_ko 사용
        return "국내"
    }

    fun getGenderText(): String {
        return when (preferredGender) {
            "M" -> "남성"
            "F" -> "여성"
            "U" -> "무관"
            else -> preferredGender
        }
    }
}
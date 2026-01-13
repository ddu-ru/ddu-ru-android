// domain/entity/TravelRoomSummary.kt
package com.gildongmu.dduru_android.domain.entity

data class TravelRoomSummary(
    // GET /api/v1/posts 응답 필드 (필수만)
    val id: Long,
    val title: String,
    val isRecruitOpen: Boolean,
    val daysLeft: Int,
    val startDate: String,           // "2026-01-02"
    val endDate: String,
    val destination: String,
    val photoUrls: List<String>? = null,

    val viewCount: Int = 0,
    val likeCount: Int = 0,

    val recruitCapacity: Int,
    val recruitCount: Int,
    val preferredGender: String,  // "M", "F", "U"
    val budgetMin: Int? ,
    val budgetMax: Int?,
    val tags: List<String>? = null,

    // UI 전용
    val isSimilar: Boolean = false
) {
    // === UI 계산 메서드 ===

    fun getLocationText(): String {
        // TODO: cities 테이블 JOIN 시 country_ko 사용
        return "국내 | $destination"
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
// domain/entity/Author.kt
package com.gildongmu.dduru_android.domain.entity

data class Author(
    // GET /api/v1/posts/{postId} 응답의 author 필드
    val id: Long,
    val name: String,
    val nickname: String,
    val profileImage: String?,
    val gender: String,              // M/F/U
    val birthday: String             // "1990-01-01" (API에 있음)
) {
    fun getAge(): String {
        // TODO: birthday 파싱 후 나이 계산
        return "34세"
    }

    fun getGenderText(): String {
        return when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            "U" -> "알 수 없음"
            else -> gender
        }
    }
}
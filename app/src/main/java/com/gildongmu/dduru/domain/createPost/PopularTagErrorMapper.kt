package com.gildongmu.dduru.domain.createPost

object PopularTagErrorMapper {
    fun popularTagMessage(status: Int?): String {
        return when (status) {
            400 -> "인기 태그 요청이 올바르지 않습니다."
            401 -> "인기 태그 조회를 위한 인증이 필요합니다."
            403 -> "인기 태그를 조회할 권한이 없습니다."
            404 -> "인기 태그 정보를 찾을 수 없습니다."
            500 -> "인기 태그 서버에 일시적인 오류가 발생했습니다."
            else -> "인기 태그를 불러오지 못했습니다."
        }
    }
}

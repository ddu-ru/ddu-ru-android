package com.gildongmu.dduru.presentation.feature.matching.createPost.util

object TagValidator {
    fun validate(tag: String): String? {
        if (tag.isBlank()) return null

        val allowedPattern = Regex("^[가-힣A-Za-z0-9]+$")
        if (!allowedPattern.matches(tag)) {
            return "한글, 영문, 숫자만 사용할 수 있어요."
        }

        val isEnglishOrNumberOnly = tag.all { it.isLetterOrDigit() && it.code < 128 }
        if (isEnglishOrNumberOnly) {
            return if (tag.length <= 7) null else "영문/숫자는 7자 이하로 입력해 주세요."
        }

        val koreanSyllableCount = tag.count { it in '\uAC00'..'\uD7A3' }
        return if (koreanSyllableCount <= 4) null else "한글은 4자 이하로 입력해 주세요."
    }
}

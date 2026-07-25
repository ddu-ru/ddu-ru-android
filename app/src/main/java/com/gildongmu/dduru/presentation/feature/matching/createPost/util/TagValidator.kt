package com.gildongmu.dduru.presentation.feature.matching.createPost.util

object TagValidator {
    fun validate(tag: String): String? {
        if (tag.isBlank()) return null

        val allowedPattern = Regex("^[ㄱ-ㅎㅏ-ㅣ가-힣A-Za-z0-9]+$")
        if (!allowedPattern.matches(tag)) {
            return "한글, 영문, 숫자만 사용할 수 있어요."
        }

        val englishOrNumberCount = tag.count { it.isLetterOrDigit() && it.code < 128 }
        if (englishOrNumberCount > 7) {
            return "영문/숫자는 7자 이하로 입력해 주세요."
        }

        val koreanCount = tag.count { it.isKoreanCharacter() }
        return if (koreanCount <= 4) null else "한글은 4자 이하로 입력해 주세요."
    }

    private fun Char.isKoreanCharacter(): Boolean {
        return this in '\u3131'..'\u314E' ||
            this in '\u314F'..'\u3163' ||
            this in '\uAC00'..'\uD7A3'
    }
}

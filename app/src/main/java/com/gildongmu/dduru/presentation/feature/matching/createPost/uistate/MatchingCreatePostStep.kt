package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

enum class MatchingCreatePostStep(val stepNumber: Int) {
    LOCATION(1),
    DATE(2),
    RECRUITMENT_COUNT(3),
    RECRUITMENT_AGE(4),
    POST_WRITING(5),
    THUMBNAIL_AND_TAG(6);

    fun previousOrNull(): MatchingCreatePostStep? = entries.getOrNull(ordinal - 1)

    fun nextOrNull(): MatchingCreatePostStep? = entries.getOrNull(ordinal + 1)
}

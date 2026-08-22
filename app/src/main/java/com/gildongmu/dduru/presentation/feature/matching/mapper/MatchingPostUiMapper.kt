package com.gildongmu.dduru.presentation.feature.matching.mapper

import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.formatDateRange
import com.gildongmu.dduru.domain.entity.matching.MatchingPostEntity
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel

fun MatchingPostEntity.toUiModel(): MatchingPostCardUiModel {
    return MatchingPostCardUiModel(
        id = id,
        title = title,
        authorName = author.nickname ?: DEFAULT_AUTHOR_NAME,
        location = destination,
        dateRange = formatDateRange(startDate = startDate, endDate = endDate),
        currentMemberCount = recruitCount,
        maxMemberCount = recruitCapacity,
        gender = preferredGender.toMatchingGender(),
        companionType = companionType.toMatchingCompanionType(),
        isSuperHost = author.isSuperHost,
        isLiked = hasLiked,
        thumbnailImageRes = R.drawable.fukuoka,
        thumbnailImageUrl = photoUrl
    )
}

private fun String.toMatchingGender(): MatchingGender {
    return MatchingGender.entries.firstOrNull { it.code == this } ?: MatchingGender.ANY
}

private fun String.toMatchingCompanionType(): MatchingCompanionType {
    return MatchingCompanionType.entries.firstOrNull { it.code == this } ?: MatchingCompanionType.FULL
}

private const val DEFAULT_AUTHOR_NAME = "알 수 없음"

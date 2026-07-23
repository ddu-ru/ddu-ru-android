package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomeMateHostResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeMateRecommendationResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeProfileImageInfoResponse
import com.gildongmu.dduru.domain.entity.HomeMateHost
import com.gildongmu.dduru.domain.entity.HomeMateRecommendation
import com.gildongmu.dduru.domain.entity.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.HomeProfileImageInfo

fun HomeMateRecommendationsResponse.toDomain(): HomeMateRecommendations {
    return HomeMateRecommendations(
        isAvailable = isAvailable,
        remainingFreeCount = remainingFreeCount,
        recommendations = recommendations.map { it.toDomain() }
    )
}

fun HomeMateRecommendationResponse.toDomain(): HomeMateRecommendation {
    return HomeMateRecommendation(
        recommendationId = recommendationId,
        postId = postId,
        matchPercentage = matchPercentage,
        title = title,
        location = location,
        startDate = startDate,
        endDate = endDate,
        host = host.toDomain(),
        currentMemberCount = currentMemberCount,
        maxMemberCount = maxMemberCount,
        description = description,
        tags = tags
    )
}

fun HomeMateHostResponse.toDomain(): HomeMateHost {
    return HomeMateHost(
        nickname = nickname,
        profileImageInfo = profileImageInfo?.toDomain(),
        age = age,
        gender = gender
    )
}

fun HomeProfileImageInfoResponse.toDomain(): HomeProfileImageInfo {
    return HomeProfileImageInfo(
        type = type,
        url = url,
        bgColorId = bgColorId
    )
}

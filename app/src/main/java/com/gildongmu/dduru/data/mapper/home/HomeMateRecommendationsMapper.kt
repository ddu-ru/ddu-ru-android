package com.gildongmu.dduru.data.mapper.home

import com.gildongmu.dduru.data.remote.dto.response.home.HomeMateHostResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeMateRecommendationResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeProfileImageInfoResponse
import com.gildongmu.dduru.domain.entity.home.HomeMateHost
import com.gildongmu.dduru.domain.entity.home.HomeMateRecommendation
import com.gildongmu.dduru.domain.entity.home.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.home.HomeProfileImageInfo

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

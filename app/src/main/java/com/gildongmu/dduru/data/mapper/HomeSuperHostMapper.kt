package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomeSuperHostHostResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSuperHostResponse
import com.gildongmu.dduru.domain.entity.HomeSuperHost
import com.gildongmu.dduru.domain.entity.HomeSuperHostHost

fun HomeSuperHostResponse.toDomain(): HomeSuperHost {
    return HomeSuperHost(
        postId = postId,
        status = status,
        title = title,
        location = location,
        startDate = startDate,
        endDate = endDate,
        currentMemberCount = currentMemberCount,
        maxMemberCount = maxMemberCount,
        tags = tags,
        host = host.toDomain(),
        viewCount = viewCount,
        thumbnailUrl = thumbnailUrl,
        hasLiked = hasLiked
    )
}

fun HomeSuperHostHostResponse.toDomain(): HomeSuperHostHost {
    return HomeSuperHostHost(
        nickname = nickname,
        profileImageInfo = profileImageInfo?.toDomain(),
        age = age,
        gender = gender
    )
}

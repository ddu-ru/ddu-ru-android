package com.gildongmu.dduru.data.createPost.mapper

import com.gildongmu.dduru.data.createPost.model.PopularDestinationResponse
import com.gildongmu.dduru.domain.createPost.entity.PopularDestination

fun PopularDestinationResponse.toDomain(): PopularDestination {
    return PopularDestination(
        id = id,
        countryCode = countryCode,
        countryName = countryName,
        city = city,
        image = image
    )
}

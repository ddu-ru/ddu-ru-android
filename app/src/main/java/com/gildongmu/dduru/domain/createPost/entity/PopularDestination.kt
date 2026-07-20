package com.gildongmu.dduru.domain.createPost.entity

data class PopularDestination(
    val id: Long,
    val countryCode: String,
    val countryName: String,
    val city: String,
    val image: String?
)

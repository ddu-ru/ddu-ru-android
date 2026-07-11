package com.gildongmu.dduru.domain.createPost.repository

interface PopularTagRepository {
    suspend fun getPopularTags(): Result<List<String>>
}

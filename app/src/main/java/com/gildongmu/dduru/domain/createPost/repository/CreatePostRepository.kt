package com.gildongmu.dduru.domain.createPost.repository

import com.gildongmu.dduru.data.createPost.model.CreatePostRequest

interface CreatePostRepository {
    suspend fun createPost(request: CreatePostRequest): Result<Long>
}

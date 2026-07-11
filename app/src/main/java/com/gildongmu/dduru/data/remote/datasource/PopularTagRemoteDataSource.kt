package com.gildongmu.dduru.data.remote.datasource

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse

interface PopularTagRemoteDataSource {
    suspend fun getPopularTags(): ApiResponse<List<String>>
}

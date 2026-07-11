package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.createPost.api.PopularTagApi
import com.gildongmu.dduru.data.remote.datasource.PopularTagRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import javax.inject.Inject

class PopularTagRemoteDataSourceImpl @Inject constructor(
    private val popularTagApi: PopularTagApi
) : PopularTagRemoteDataSource {
    override suspend fun getPopularTags(): ApiResponse<List<String>> {
        return popularTagApi.getPopularTags()
    }
}

package com.gildongmu.dduru.data.createPost

import com.gildongmu.dduru.data.createPost.api.DestinationApi
import com.gildongmu.dduru.data.createPost.api.PopularTagApi
import com.gildongmu.dduru.data.createPost.repository.DestinationRepositoryImpl
import com.gildongmu.dduru.data.createPost.repository.PopularTagRepositoryImpl
import com.gildongmu.dduru.data.remote.datasource.PopularTagRemoteDataSource
import com.gildongmu.dduru.data.remote.datasourceimpl.PopularTagRemoteDataSourceImpl
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
import com.gildongmu.dduru.domain.createPost.repository.PopularTagRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class CreatePostModule {
    @Binds
    @Singleton
    abstract fun bindDestinationRepository(
        destinationRepositoryImpl: DestinationRepositoryImpl
    ): DestinationRepository

    @Binds
    @Singleton
    abstract fun bindPopularTagRemoteDataSource(
        popularTagRemoteDataSourceImpl: PopularTagRemoteDataSourceImpl
    ): PopularTagRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPopularTagRepository(
        popularTagRepositoryImpl: PopularTagRepositoryImpl
    ): PopularTagRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CreatePostNetworkModule {
    @Provides
    @Singleton
    fun provideDestinationApi(retrofit: Retrofit): DestinationApi {
        return retrofit.create(DestinationApi::class.java)
    }

    @Provides
    @Singleton
    fun providePopularTagApi(retrofit: Retrofit): PopularTagApi {
        return retrofit.create(PopularTagApi::class.java)
    }
}

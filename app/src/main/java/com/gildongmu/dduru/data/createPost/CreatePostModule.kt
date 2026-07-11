package com.gildongmu.dduru.data.createPost

import com.gildongmu.dduru.data.createPost.api.CreatePostApi
import com.gildongmu.dduru.data.createPost.api.DestinationApi
import com.gildongmu.dduru.data.createPost.repository.CreatePostRepositoryImpl
import com.gildongmu.dduru.data.createPost.repository.DestinationRepositoryImpl
import com.gildongmu.dduru.data.remote.datasource.CreatePostRemoteDataSource
import com.gildongmu.dduru.data.remote.datasourceimpl.CreatePostRemoteDataSourceImpl
import com.gildongmu.dduru.domain.createPost.repository.CreatePostRepository
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
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
    abstract fun bindCreatePostRemoteDataSource(
        createPostRemoteDataSourceImpl: CreatePostRemoteDataSourceImpl
    ): CreatePostRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCreatePostRepository(
        createPostRepositoryImpl: CreatePostRepositoryImpl
    ): CreatePostRepository

    @Binds
    @Singleton
    abstract fun bindDestinationRepository(
        destinationRepositoryImpl: DestinationRepositoryImpl
    ): DestinationRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CreatePostNetworkModule {
    @Provides
    @Singleton
    fun provideCreatePostApi(retrofit: Retrofit): CreatePostApi {
        return retrofit.create(CreatePostApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDestinationApi(retrofit: Retrofit): DestinationApi {
        return retrofit.create(DestinationApi::class.java)
    }
}

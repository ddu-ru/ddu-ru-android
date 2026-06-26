package com.gildongmu.dduru.data.createPost

import com.gildongmu.dduru.data.createPost.api.DestinationApi
import com.gildongmu.dduru.data.createPost.datasource.DestinationRemoteDataSource
import com.gildongmu.dduru.data.createPost.datasourceimpl.DestinationRemoteDataSourceImpl
import com.gildongmu.dduru.data.createPost.repository.DestinationRepositoryImpl
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
    abstract fun bindDestinationRepository(
        destinationRepositoryImpl: DestinationRepositoryImpl
    ): DestinationRepository

    @Binds
    @Singleton
    abstract fun bindDestinationRemoteDataSource(
        destinationRemoteDataSourceImpl: DestinationRemoteDataSourceImpl
    ): DestinationRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object CreatePostNetworkModule {
    @Provides
    @Singleton
    fun provideDestinationApi(retrofit: Retrofit): DestinationApi {
        return retrofit.create(DestinationApi::class.java)
    }
}

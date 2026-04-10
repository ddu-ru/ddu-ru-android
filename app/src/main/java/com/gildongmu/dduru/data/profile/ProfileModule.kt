package com.gildongmu.dduru.data.profile

import com.gildongmu.dduru.data.profile.api.ProfileApi
import com.gildongmu.dduru.data.entity.image.api.ImagePresignedUrlApi
import com.gildongmu.dduru.data.profile.repository.ProfileRepositoryImpl
import com.gildongmu.dduru.domain.register.profile.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import javax.inject.Named
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProfileNetworkModule {

    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileImageApi(retrofit: Retrofit): ImagePresignedUrlApi {
        return retrofit.create(ImagePresignedUrlApi::class.java)
    }

    @Provides
    @Singleton
    @Named("presignedUploadClient")
    fun providePresignedUploadClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}

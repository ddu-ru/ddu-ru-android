package com.gildongmu.dduru_android.domain.nickname

import com.gildongmu.dduru_android.data.nickname.api.NicknameApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class NicknameModule {
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideNicknameApi(retrofit: Retrofit): NicknameApi =
        retrofit.create(NicknameApi::class.java)
}

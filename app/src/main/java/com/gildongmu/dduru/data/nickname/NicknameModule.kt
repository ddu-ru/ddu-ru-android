package com.gildongmu.dduru.data.nickname

import com.gildongmu.dduru.data.nickname.api.NicknameApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NicknameModule {

    @Provides
    @Singleton
    fun provideNicknameApi(retrofit: Retrofit): NicknameApi {
        return retrofit.create(NicknameApi::class.java)
    }
}

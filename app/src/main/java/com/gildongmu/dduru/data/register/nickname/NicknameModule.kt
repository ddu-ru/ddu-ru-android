package com.gildongmu.dduru.data.register.nickname

import com.gildongmu.dduru.data.register.nickname.api.NicknameApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NicknameModule {

    @Provides
    @Singleton
    fun provideNicknameApi(retrofit: Retrofit): NicknameApi {
        return retrofit.create(NicknameApi::class.java)
    }
}

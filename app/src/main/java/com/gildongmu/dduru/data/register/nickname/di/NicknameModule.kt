package com.gildongmu.dduru.data.register.nickname

import com.gildongmu.dduru.data.nickname.api.NicknameApi
import com.gildongmu.dduru.data.register.nickname.repositoryimpl.NicknameRepositoryImpl
import com.gildongmu.dduru.domain.register.nicname.repository.NicknameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class NicknameModule {
    @Binds
    abstract fun bindNicknameRepository(
        impl: NicknameRepositoryImpl
    ): NicknameRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideNicknameApi(retrofit: Retrofit): NicknameApi =
        retrofit.create(NicknameApi::class.java)
}

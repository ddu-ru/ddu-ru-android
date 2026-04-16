package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.data.register.nickname.repositoryimpl.NicknameRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.AuthRepositoryImpl
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import com.gildongmu.dduru.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindNicknameRepository(
        nicknameRepositoryImpl: NicknameRepositoryImpl
    ): NicknameRepository
}

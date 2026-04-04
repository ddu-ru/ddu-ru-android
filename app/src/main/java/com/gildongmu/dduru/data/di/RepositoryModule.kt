package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.data.repositoryimpl.AuthRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.SurveyRepositoryImpl
import com.gildongmu.dduru.domain.repository.AuthRepository
import com.gildongmu.dduru.domain.repository.SurveyRepository
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
    abstract fun bindSurveyRepository(
        surveyRepositoryImpl: SurveyRepositoryImpl
    ): SurveyRepository
}

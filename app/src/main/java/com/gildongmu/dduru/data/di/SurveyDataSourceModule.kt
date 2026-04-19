package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.data.remote.datasource.SurveyRemoteDataSource
import com.gildongmu.dduru.data.remote.datasourceimpl.SurveyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SurveyDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindSurveyRemoteDataSource(
        surveyRemoteDataSourceImpl: SurveyRemoteDataSourceImpl
    ): SurveyRemoteDataSource
}

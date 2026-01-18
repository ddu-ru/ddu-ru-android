package com.gildongmu.dduru_android.data.di


import com.gildongmu.dduru_android.data.remote.datasource.SurveyRemoteDataSource
import com.gildongmu.dduru_android.data.remote.datasourceimpl.SurveyRemoteDataSourceImpl
import com.gildongmu.dduru_android.data.remote.service.SurveyService
import com.gildongmu.dduru_android.data.repositoryimpl.SurveyRepositoryImpl
import com.gildongmu.dduru_android.domain.repository.SurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SurveyModule {

    @Provides
    @Singleton
    fun provideSurveyService(
        retrofit: Retrofit
    ): SurveyService {
        return retrofit.create(SurveyService::class.java)
    }

    @Provides
    @Singleton
    fun provideSurveyRemoteDataSource(
        surveyService: SurveyService
    ): SurveyRemoteDataSource {
        return SurveyRemoteDataSourceImpl(surveyService)
    }

    @Provides
    @Singleton
    fun provideSurveyRepository(
        surveyRemoteDataSource: SurveyRemoteDataSource
    ): SurveyRepository {
        return SurveyRepositoryImpl(surveyRemoteDataSource)
    }
}

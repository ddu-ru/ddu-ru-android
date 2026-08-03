package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.BuildConfig
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.interceptor.AuthInterceptor
import com.gildongmu.dduru.data.remote.service.AuthService
import com.gildongmu.dduru.data.remote.service.ChatRoomService
import com.gildongmu.dduru.data.remote.service.ParticipationService
import com.gildongmu.dduru.data.remote.service.SurveyService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenLocalDataSource: TokenLocalDataSource
    ): AuthInterceptor {
        return AuthInterceptor(tokenLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
            redactHeader("Authorization")
        }

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideSurveyService(retrofit: Retrofit): SurveyService {
        return retrofit.create(SurveyService::class.java)
    }

    @Provides
    @Singleton
    fun provideChatRoomService(retrofit: Retrofit): ChatRoomService {
        return retrofit.create(ChatRoomService::class.java)
    }

    @Provides
    @Singleton
    fun provideParticipationService(retrofit: Retrofit): ParticipationService {
        return retrofit.create(ParticipationService::class.java)
    }
}

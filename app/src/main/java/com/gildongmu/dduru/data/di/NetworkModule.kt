package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.BuildConfig
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.interceptor.AuthInterceptor
import com.gildongmu.dduru.data.remote.interceptor.TokenAuthenticator
import com.gildongmu.dduru.data.remote.service.AuthService
import com.gildongmu.dduru.data.remote.service.ChatRoomService
import com.gildongmu.dduru.data.remote.service.FcmTokenService
import com.gildongmu.dduru.data.remote.service.ParticipationService
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.data.remote.service.SurveyService
import com.gildongmu.dduru.data.remote.service.home.HomeNotificationService
import com.gildongmu.dduru.data.remote.service.home.HomeService
import com.gildongmu.dduru.data.remote.websocket.StompChatClient
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
        tokenLocalDataSource: TokenLocalDataSource,
        tokenAuthenticator: TokenAuthenticator
    ): AuthInterceptor {
        return AuthInterceptor(
            tokenLocalDataSource = tokenLocalDataSource,
            tokenAuthenticator = tokenAuthenticator
        )
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenLocalDataSource: TokenLocalDataSource,
        baseUrl: String,
        json: Json
    ): TokenAuthenticator {
        return TokenAuthenticator(
            tokenLocalDataSource = tokenLocalDataSource,
            baseUrl = baseUrl,
            json = json
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
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
            .authenticator(tokenAuthenticator)
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
        baseUrl: String,
        json: Json
    ): Retrofit {
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

    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)

    @Provides
    @Singleton
    fun provideFcmTokenService(retrofit: Retrofit): FcmTokenService {
        return retrofit.create(FcmTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Provides
    @Singleton
    fun provideStompChatClient(
        okHttpClient: OkHttpClient,
        tokenLocalDataSource: TokenLocalDataSource,
        baseUrl: String
    ): StompChatClient = StompChatClient(
        tokenLocalDataSource = tokenLocalDataSource,
        okHttpClient = okHttpClient,
        baseUrl = baseUrl
    )

    @Provides
    @Singleton
    fun provideHomeNotificationService(retrofit: Retrofit): HomeNotificationService {
        return retrofit.create(HomeNotificationService::class.java)
    }
}

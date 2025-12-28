package com.gildongmu.dduru_android.data.di

import com.gildongmu.dduru_android.data.local.TokenLocalDataSource
import com.gildongmu.dduru_android.data.local.TokenLocalDataSourceImpl
import com.gildongmu.dduru_android.data.remote.datasource.AuthDataSource
import com.gildongmu.dduru_android.data.remote.datasourceimpl.AuthDataSourceImpl
import com.gildongmu.dduru_android.data.remote.datasource.GoogleAuthDataSource
import com.gildongmu.dduru_android.data.remote.datasource.GoogleAuthDataSourceImpl
import com.gildongmu.dduru_android.data.remote.datasource.KakaoAuthDataSource
import com.gildongmu.dduru_android.data.remote.datasourceimpl.KakaoAuthDataSourceImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindTokenLocalDataSource(
        tokenLocalDataSourceImpl: TokenLocalDataSourceImpl
    ): TokenLocalDataSource

    @Binds
    @Singleton
    abstract fun bindKakaoAuthDataSource(
        impl: KakaoAuthDataSourceImpl
    ): KakaoAuthDataSource

    @Binds
    @Singleton
    abstract fun bindGoogleAuthDataSource(
        googleAuthDataSourceImpl: GoogleAuthDataSourceImpl
    ): GoogleAuthDataSource
}

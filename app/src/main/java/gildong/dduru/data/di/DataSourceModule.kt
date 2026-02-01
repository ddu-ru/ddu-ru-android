package gildong.dduru.data.di

import gildong.dduru.data.local.TokenLocalDataSource
import gildong.dduru.data.local.TokenLocalDataSourceImpl
import gildong.dduru.data.remote.datasource.AuthDataSource
import gildong.dduru.data.remote.datasource.GoogleAuthDataSource
import gildong.dduru.data.remote.datasource.KakaoAuthDataSource
import gildong.dduru.data.remote.datasourceimpl.AuthDataSourceImpl
import gildong.dduru.data.remote.datasourceimpl.GoogleAuthDataSourceImpl
import gildong.dduru.data.remote.datasourceimpl.KakaoAuthDataSourceImpl
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

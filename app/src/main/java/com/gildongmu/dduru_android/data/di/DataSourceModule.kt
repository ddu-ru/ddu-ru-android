package com.gildongmu.dduru_android.data.di

import com.gildongmu.dduru_android.data.remote.datasource.AuthDataSource
import com.gildongmu.dduru_android.data.remote.datasourceimpl.AuthDataSourceImpl
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
}
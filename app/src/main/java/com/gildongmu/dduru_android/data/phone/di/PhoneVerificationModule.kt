package com.gildongmu.dduru_android.data.phone.di

import com.gildongmu.dduru_android.data.local.VerificationTokenLocalDataSource
import com.gildongmu.dduru_android.data.local.VerificationTokenLocalDataSourceImpl
import com.gildongmu.dduru_android.data.phone.api.PhoneVerificationApi
import com.gildongmu.dduru_android.data.phone.repository.PhoneVerificationRepositoryImpl
import com.gildongmu.dduru_android.domain.phone.PhoneVerificationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PhoneVerificationBindModule {

    @Binds
    @Singleton
    abstract fun bindPhoneVerificationRepository(
        impl: PhoneVerificationRepositoryImpl
    ): PhoneVerificationRepository

    @Binds
    @Singleton
    abstract fun bindVerificationTokenLocalDataSource(
        impl: VerificationTokenLocalDataSourceImpl
    ): VerificationTokenLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object PhoneVerificationProvideModule {

    @Provides
    @Singleton
    fun providePhoneVerificationApi(
        retrofit: Retrofit
    ): PhoneVerificationApi =
        retrofit.create(PhoneVerificationApi::class.java)
}
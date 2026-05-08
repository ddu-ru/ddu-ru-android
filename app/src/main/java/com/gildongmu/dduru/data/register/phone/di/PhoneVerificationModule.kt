package com.gildongmu.dduru.data.register.phone.di

import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSource
import com.gildongmu.dduru.data.local.PhoneVerificationTokenLocalDataSourceImpl
import com.gildongmu.dduru.data.register.phone.api.PhoneVerificationApi
import com.gildongmu.dduru.data.register.phone.repository.PhoneVerificationRepositoryImpl
import com.gildongmu.dduru.domain.phone.PhoneVerificationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

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
        impl: PhoneVerificationTokenLocalDataSourceImpl
    ): PhoneVerificationTokenLocalDataSource
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

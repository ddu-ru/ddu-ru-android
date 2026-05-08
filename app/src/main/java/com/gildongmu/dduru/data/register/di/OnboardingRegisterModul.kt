package com.gildongmu.dduru.data.register.di

import com.gildongmu.dduru.data.register.api.OnboardingRegisterApi
import com.gildongmu.dduru.data.register.api.OnboardingStatusApi
import com.gildongmu.dduru.data.register.repository.OnboardingRegisterRepositoryImpl
import com.gildongmu.dduru.data.register.repository.OnboardingStatusRepositoryImpl
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
import com.gildongmu.dduru.domain.register.repository.OnboardingStatusRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingRegisterModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: OnboardingRegisterRepositoryImpl
    ): OnboardingRegisterRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingStatusRepository(
        impl: OnboardingStatusRepositoryImpl
    ): OnboardingStatusRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OnboardingRegisterNetworkModule {

    @Provides
    @Singleton
    fun provideOnboardingRegister(retrofit: Retrofit): OnboardingRegisterApi {
        return retrofit.create(OnboardingRegisterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOnboardingStatusApi(retrofit: Retrofit): OnboardingStatusApi {
        return retrofit.create(OnboardingStatusApi::class.java)
    }
}

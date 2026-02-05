package com.gildongmu.dduru.data.register.di

import com.gildongmu.dduru.data.register.api.OnboardingRegisterApi
import com.gildongmu.dduru.data.register.repository.OnboardingRegisterRepositoryImpl
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
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
    abstract fun bindProfileRepository(
        impl: OnboardingRegisterRepositoryImpl
    ): OnboardingRegisterRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OnboardingRegisterNetworkModule {

    @Provides
    @Singleton
    fun provideOnboardingRegister(retrofit: Retrofit): OnboardingRegisterApi {
        return retrofit.create(OnboardingRegisterApi::class.java)
    }
}

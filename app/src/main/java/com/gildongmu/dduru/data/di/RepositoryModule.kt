package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.data.register.nickname.repositoryimpl.NicknameRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.AuthRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.ChatRoomRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.ParticipationRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.SurveyRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.home.FakeHomeNotificationRepository
import com.gildongmu.dduru.data.repositoryimpl.home.HomeRepositoryImpl
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import com.gildongmu.dduru.domain.repository.AuthRepository
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import com.gildongmu.dduru.domain.repository.SurveyRepository
import com.gildongmu.dduru.domain.repository.home.HomeNotificationRepository
import com.gildongmu.dduru.domain.repository.home.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindHomeNotificationRepository(
        fakeHomeNotificationRepository: FakeHomeNotificationRepository
    ): HomeNotificationRepository

    @Binds
    @Singleton
    abstract fun bindSurveyRepository(
        surveyRepositoryImpl: SurveyRepositoryImpl
    ): SurveyRepository

    @Binds
    @Singleton
    abstract fun bindNicknameRepository(
        nicknameRepositoryImpl: NicknameRepositoryImpl
    ): NicknameRepository

    @Binds
    @Singleton
    abstract fun bindChatRoomRepository(
        impl: ChatRoomRepositoryImpl
    ): ChatRoomRepository

    @Binds
    @Singleton
    abstract fun bindParticipationRepository(
        impl: ParticipationRepositoryImpl
    ): ParticipationRepository
}

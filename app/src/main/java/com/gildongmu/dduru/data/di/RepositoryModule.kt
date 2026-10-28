package com.gildongmu.dduru.data.di

import com.gildongmu.dduru.data.register.nickname.repositoryimpl.NicknameRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.AuthRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.ChatRoomRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.FcmTokenRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.MyPageRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.ParticipationRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.SurveyRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.home.HomeNotificationRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.home.HomeRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.journey.JourneyCommentRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.journey.JourneyPostRepositoryImpl
import com.gildongmu.dduru.data.repositoryimpl.journey.JourneyRepositoryImpl
import com.gildongmu.dduru.domain.journey.repository.JourneyCommentRepository
import com.gildongmu.dduru.domain.journey.repository.JourneyPostRepository
import com.gildongmu.dduru.domain.journey.repository.JourneyRepository
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import com.gildongmu.dduru.domain.repository.AuthRepository
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.domain.repository.FcmTokenRepository
import com.gildongmu.dduru.domain.repository.MyPageRepository
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
        homeNotificationRepositoryImpl: HomeNotificationRepositoryImpl
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
    abstract fun bindJourneyPostRepository(
        impl: JourneyPostRepositoryImpl
    ): JourneyPostRepository

    @Binds
    @Singleton
    abstract fun bindJourneyCommentRepository(
        impl: JourneyCommentRepositoryImpl
    ): JourneyCommentRepository

    @Binds
    @Singleton
    abstract fun bindJourneyRepository(
        impl: JourneyRepositoryImpl
    ): JourneyRepository

    @Binds
    @Singleton
    abstract fun bindParticipationRepository(
        impl: ParticipationRepositoryImpl
    ): ParticipationRepository

    @Binds
    @Singleton
    abstract fun bindFcmTokenRepository(
        fcmTokenRepositoryImpl: FcmTokenRepositoryImpl
    ): FcmTokenRepository

    @Binds
    @Singleton
    abstract fun bindMyPageRepository(
        myPageRepositoryImpl: MyPageRepositoryImpl
    ): MyPageRepository
}

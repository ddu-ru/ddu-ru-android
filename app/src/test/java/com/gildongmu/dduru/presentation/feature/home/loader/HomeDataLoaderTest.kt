package com.gildongmu.dduru.presentation.feature.home.loader

import com.gildongmu.dduru.domain.entity.home.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.home.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.home.HomePopularDestinations
import com.gildongmu.dduru.domain.entity.home.HomeSameAgeTrip
import com.gildongmu.dduru.domain.entity.home.HomeSameDestinationTrip
import com.gildongmu.dduru.domain.entity.home.HomeSectionConfig
import com.gildongmu.dduru.domain.entity.home.HomeSuperHost
import com.gildongmu.dduru.domain.entity.home.HomeUpcomingTrip
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostFilter
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostPage
import com.gildongmu.dduru.domain.entity.mypage.MyProfile
import com.gildongmu.dduru.domain.entity.mypage.TravelPreference
import com.gildongmu.dduru.domain.register.model.SurveyStatus
import com.gildongmu.dduru.domain.register.repository.OnboardingStatusRepository
import com.gildongmu.dduru.domain.repository.MyPageRepository
import com.gildongmu.dduru.domain.repository.home.HomeRepository
import com.gildongmu.dduru.presentation.feature.home.state.HomeSurveyState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeDataLoaderTest {
    @Test
    fun `failed section request does not fail the whole home load`() = runBlocking {
        val loader = createLoader(enabledSections = setOf("UPCOMING_TRIP"))

        val result = loader()

        assertTrue(result.isSuccess)
        assertFalse(result.getOrThrow().upcomingTrip.hasUpcomingTrip)
    }

    @Test
    fun `failed survey status is represented as unavailable`() = runBlocking {
        val loader = createLoader(surveyResult = Result.failure(IllegalStateException("offline")))

        assertEquals(HomeSurveyState.UNAVAILABLE, loader.loadSurveyState())
    }

    @Test
    fun `incomplete survey status is not treated as completed`() = runBlocking {
        val loader = createLoader(surveyResult = Result.success(SurveyStatus.NOT_STARTED))

        assertEquals(HomeSurveyState.NOT_COMPLETED, loader.loadSurveyState())
    }

    private fun createLoader(
        enabledSections: Set<String> = emptySet(),
        surveyResult: Result<SurveyStatus> = Result.success(SurveyStatus.COMPLETED)
    ) = HomeDataLoader(
        homeRepository = StubHomeRepository(enabledSections),
        myPageRepository = StubMyPageRepository(),
        onboardingStatusRepository = StubOnboardingStatusRepository(surveyResult)
    )

    private class StubHomeRepository(enabledSections: Set<String>) : HomeRepository {
        private val config = HomeInitialConfig(
            userAccessStatus = "ACTIVE",
            sections = SECTION_KEYS.map { key ->
                HomeSectionConfig(
                    key = key,
                    enabled = key in enabledSections,
                    endpoint = "",
                    disabledReason = null
                )
            }
        )

        override suspend fun getHomeInitialConfig() = Result.success(config)

        override suspend fun getHomeMateRecommendations(): Result<HomeMateRecommendations> =
            error("Mate recommendations are disabled in this test")

        override suspend fun getHomePopularDestinations(): Result<HomePopularDestinations> =
            error("Popular destinations are disabled in this test")

        override suspend fun getHomeSameAgeTrips(): Result<List<HomeSameAgeTrip>> =
            error("Same-age trips are disabled in this test")

        override suspend fun getHomeSameDestinationTrips(): Result<List<HomeSameDestinationTrip>> =
            error("Same-destination trips are disabled in this test")

        override suspend fun getHomeSuperHosts(): Result<List<HomeSuperHost>> =
            error("Superhosts are disabled in this test")

        override suspend fun getHomeUpcomingTrip(): Result<HomeUpcomingTrip> =
            error("Simulated upcoming-trip endpoint exception")

        private companion object {
            val SECTION_KEYS = setOf(
                "UPCOMING_TRIP",
                "MATE_RECOMMENDATIONS",
                "POPULAR_DESTINATIONS",
                "SUPER_HOSTS",
                "SAME_DESTINATION_TRIPS",
                "SAME_AGE_TRIPS"
            )
        }
    }

    private class StubMyPageRepository : MyPageRepository {
        override suspend fun getLikedPosts(cursor: Long?): Result<MyPagePostPage> = error("Not used")
        override suspend fun getMyPosts(
            cursor: Long?,
            filter: MyPagePostFilter
        ): Result<MyPagePostPage> = error("Not used")

        override suspend fun getTravelPreference(): Result<TravelPreference> =
            Result.success(TravelPreference(destinations = emptyList(), availableDates = emptyList()))

        override suspend fun updateTravelPreference(preference: TravelPreference): Result<Unit> =
            error("Not used")

        override suspend fun searchPreferenceDestinations(
            keyword: String
        ): Result<List<DestinationPreference>> = error("Not used")

        override suspend fun getMyProfile(): Result<MyProfile> = error("Not used")
    }

    private class StubOnboardingStatusRepository(
        private val result: Result<SurveyStatus>
    ) : OnboardingStatusRepository {
        override suspend fun getSurveyStatus(): Result<SurveyStatus> = result
    }
}

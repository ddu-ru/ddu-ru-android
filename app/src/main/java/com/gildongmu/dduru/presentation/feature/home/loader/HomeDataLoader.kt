package com.gildongmu.dduru.presentation.feature.home.loader

import com.gildongmu.dduru.domain.entity.home.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.home.HomeMateRecommendations
import com.gildongmu.dduru.domain.register.model.SurveyStatus
import com.gildongmu.dduru.domain.register.repository.OnboardingStatusRepository
import com.gildongmu.dduru.domain.repository.MyPageRepository
import com.gildongmu.dduru.domain.repository.home.HomeRepository
import com.gildongmu.dduru.presentation.feature.home.DAILY_AI_MATCHING_LIMIT
import com.gildongmu.dduru.presentation.feature.home.mapper.toAiMatchingCardData
import com.gildongmu.dduru.presentation.feature.home.mapper.toTravelCompanionUiModel
import com.gildongmu.dduru.presentation.feature.home.mapper.toUiModel
import com.gildongmu.dduru.presentation.feature.home.mapper.toUiState
import com.gildongmu.dduru.presentation.feature.home.mapper.toUpdatedText
import com.gildongmu.dduru.presentation.feature.home.state.HomeDestinationPreferenceState
import com.gildongmu.dduru.presentation.feature.home.state.HomeSurveyState
import com.gildongmu.dduru.presentation.feature.home.state.HomeUiState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@ViewModelScoped
class HomeDataLoader @Inject constructor(
    private val homeRepository: HomeRepository,
    private val myPageRepository: MyPageRepository,
    private val onboardingStatusRepository: OnboardingStatusRepository
) {
    suspend operator fun invoke(): Result<HomeUiState> {
        val config = try {
            homeRepository.getHomeInitialConfig().getOrElse {
                if (it is CancellationException) throw it
                return Result.failure(it)
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            return Result.failure(exception)
        }

        return try {
            Result.success(coroutineScope {
                val upcomingTrip = async {
                    if (config.isSectionEnabled(SECTION_UPCOMING_TRIP)) {
                        safeRequest { homeRepository.getHomeUpcomingTrip() }
                            .getOrNull()
                            ?.toUiState()
                    } else {
                        null
                    }
                }
                val mateRecommendations = async {
                    if (config.isSectionEnabled(SECTION_MATE_RECOMMENDATIONS)) {
                        safeRequest { homeRepository.getHomeMateRecommendations() }.getOrNull()
                    } else {
                        null
                    }
                }
                val popularDestinations = async {
                    if (config.isSectionEnabled(SECTION_POPULAR_DESTINATIONS)) {
                        safeRequest { homeRepository.getHomePopularDestinations() }.getOrNull()
                    } else {
                        null
                    }
                }
                val superHosts = async {
                    if (config.isSectionEnabled(SECTION_SUPER_HOSTS)) {
                        safeRequest { homeRepository.getHomeSuperHosts() }
                            .getOrNull()
                            .orEmpty()
                            .map { it.toUiModel() }
                    } else {
                        emptyList()
                    }
                }
                val sameDestinationTrips = async {
                    if (config.isSectionEnabled(SECTION_SAME_DESTINATION_TRIPS)) {
                        safeRequest { homeRepository.getHomeSameDestinationTrips() }
                            .getOrNull()
                            ?.map { it.toTravelCompanionUiModel() }
                            .orEmpty()
                    } else {
                        emptyList()
                    }
                }
                val sameAgeTrips = async {
                    if (config.isSectionEnabled(SECTION_SAME_AGE_TRIPS)) {
                        safeRequest { homeRepository.getHomeSameAgeTrips() }
                            .getOrNull()
                            ?.map { it.toTravelCompanionUiModel() }
                            .orEmpty()
                    } else {
                        emptyList()
                    }
                }
                val surveyState = async { loadSurveyState() }
                val destinationPreferenceState = async { loadDestinationPreferenceState() }

                val recommendations = mateRecommendations.await()
                val popular = popularDestinations.await()
                HomeUiState(
                    upcomingTrip = upcomingTrip.await() ?: HomeUiState().upcomingTrip,
                    popularDestinations = popular?.items
                        ?.map { it.toUiModel() }
                        ?.toImmutableList()
                        ?: persistentListOf(),
                    popularDestinationUpdatedAt = popular?.updatedAt,
                    popularDestinationUpdatedText = popular?.updatedAt?.toUpdatedText() ?: "방금 전",
                    aiMatchingUsedCount = recommendations?.usedCount() ?: 0,
                    aiMatchingCard = recommendations?.toCardOrNull(),
                    surveyState = surveyState.await(),
                    destinationPreferenceState = destinationPreferenceState.await(),
                    superHosts = superHosts.await(),
                    sameDestinationCompanions = sameDestinationTrips.await(),
                    sameAgeCompanions = sameAgeTrips.await()
                )
            })
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun loadSurveyState(): HomeSurveyState =
        safeRequest { onboardingStatusRepository.getSurveyStatus() }
            .fold({ it.toHomeSurveyState() }, { HomeSurveyState.UNAVAILABLE })

    suspend fun loadDestinationPreferenceState(): HomeDestinationPreferenceState =
        safeRequest { myPageRepository.getTravelPreference() }
            .fold(
                { preference ->
                    if (preference.destinations.isEmpty()) {
                        HomeDestinationPreferenceState.EMPTY
                    } else {
                        HomeDestinationPreferenceState.AVAILABLE
                    }
                },
                { HomeDestinationPreferenceState.UNAVAILABLE }
            )

    private suspend fun <T> safeRequest(request: suspend () -> Result<T>): Result<T> =
        try {
            request()
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    private fun HomeInitialConfig.isSectionEnabled(key: String): Boolean =
        sections.firstOrNull { it.key == key }?.enabled ?: true

    private fun HomeMateRecommendations.usedCount(): Int =
        (if (isAvailable) DAILY_AI_MATCHING_LIMIT - remainingFreeCount else DAILY_AI_MATCHING_LIMIT)
            .coerceIn(0, DAILY_AI_MATCHING_LIMIT)

    private fun HomeMateRecommendations.toCardOrNull() =
        recommendations.firstOrNull()
            ?.toAiMatchingCardData()
            .takeIf { isAvailable }

    private fun SurveyStatus.toHomeSurveyState(): HomeSurveyState =
        if (this == SurveyStatus.COMPLETED) HomeSurveyState.COMPLETED else HomeSurveyState.NOT_COMPLETED

    private companion object {
        const val SECTION_UPCOMING_TRIP = "UPCOMING_TRIP"
        const val SECTION_MATE_RECOMMENDATIONS = "MATE_RECOMMENDATIONS"
        const val SECTION_POPULAR_DESTINATIONS = "POPULAR_DESTINATIONS"
        const val SECTION_SUPER_HOSTS = "SUPER_HOSTS"
        const val SECTION_SAME_DESTINATION_TRIPS = "SAME_DESTINATION_TRIPS"
        const val SECTION_SAME_AGE_TRIPS = "SAME_AGE_TRIPS"
    }
}

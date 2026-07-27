package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.createPost.model.CreatePostRequest
import com.gildongmu.dduru.domain.createPost.entity.PopularDestination
import com.gildongmu.dduru.domain.createPost.repository.CreatePostRepository
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
import com.gildongmu.dduru.domain.createPost.repository.PopularTagRepository
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiEvent
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.util.TagValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MatchingCreatePostViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository,
    private val createPostRepository: CreatePostRepository,
    private val popularTagRepository: PopularTagRepository
) : ViewModel() {
    private companion object {
        const val POPULAR_DESTINATIONS_ERROR_MESSAGE =
            "인기 여행지를 불러올 수 없어요 잠시후 다시 시도해주세요"
        const val SEARCH_DESTINATIONS_ERROR_MESSAGE =
            "여행지를 검색할 수 없어요 잠시후 다시 시도해주세요"
        const val SEARCH_DEBOUNCE_MILLIS = 1_200L
        const val MAX_TAG_COUNT = 10
        const val TAG_LIMIT_ERROR_MESSAGE = "태그는 최대 10개까지 추가할 수 있어요."
    }

    private val _uiState = MutableStateFlow(
        MatchingCreatePostUiState()
    )
    val uiState: StateFlow<MatchingCreatePostUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MatchingCreatePostUiEvent>()
    val uiEvent: SharedFlow<MatchingCreatePostUiEvent> = _uiEvent.asSharedFlow()
    private var searchDebounceJob: Job? = null
    private var searchRequestJob: Job? = null

    init {
        fetchPopularDestinations()
        fetchPopularTags()
    }

    fun onDismissClick() {
        emitUiEvent(MatchingCreatePostUiEvent.Close)
    }

    fun onBackClick() {
        movePreviousStep()
    }

    fun onNextClick() {
        moveNextStep()
    }

    fun onLocationSearchUiStateChange(locationSearchUiState: LocationSearchUiState) {
        val previousQuery = _uiState.value.locationSearchUiState.searchQuery
        _uiState.update { currentState ->
            currentState.copy(locationSearchUiState = locationSearchUiState)
        }

        if (previousQuery != locationSearchUiState.searchQuery) {
            debounceSearchDestinations(locationSearchUiState.searchQuery)
        }
    }

    fun onLocationSelected(location: LocationSearchItemUiState) {
        _uiState.update { currentState ->
            currentState.copy(
                locationSearchUiState = currentState.locationSearchUiState.copy(
                    selectedLocation = location,
                    searchQuery = location.city
                ),
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                    regionName = location.city,
                    countryName = location.countryName,
                    previewImageUrl = location.image
                )
            )
        }

        moveToStep(MatchingCreatePostStep.DATE)
    }

    fun onLocationSearchClick() {
        searchDebounceJob?.cancel()
        searchDestinations(_uiState.value.locationSearchUiState.searchQuery)
    }

    fun onDateSectionUiStateChange(dateSectionUiState: DateSectionUiState) {
        _uiState.update { currentState ->
            currentState.copy(dateSectionUiState = dateSectionUiState)
        }
    }

    fun onRecruitmentSectionUiStateChange(recruitmentSectionUiState: RecruitmentScreenUiState) {
        _uiState.update { currentState ->
            currentState.copy(recruitmentSectionUiState = recruitmentSectionUiState)
        }
    }

    fun onAgeSectionUiStateChange(ageSectionUiState: AgeSectionUiState) {
        _uiState.update { currentState ->
            currentState.copy(ageSectionUiState = ageSectionUiState)
        }
    }

    fun onPostWritingUiStateChange(postWritingUiState: PostWritingUiState) {
        _uiState.update { currentState ->
            currentState.copy(postWritingUiState = postWritingUiState)
        }
    }

    fun onThumbnailAndTagUiStateChange(thumbnailAndTagUiState: ThumbnailAndTagUiState) {
        _uiState.update { currentState ->
            currentState.copy(thumbnailAndTagUiState = thumbnailAndTagUiState)
        }
    }

    fun onTagInputChange(input: String) {
        _uiState.update { currentState ->
            val thumbnailState = currentState.thumbnailAndTagUiState
            currentState.copy(
                thumbnailAndTagUiState = thumbnailState.copy(
                    tagInput = input,
                    tagValidationMessage = resolveTagValidationMessage(
                        tag = input.trim(),
                        addedTagCount = thumbnailState.addedTags.size
                    )
                )
            )
        }
    }

    fun onAddTag() {
        _uiState.update { currentState ->
            val thumbnailState = currentState.thumbnailAndTagUiState
            val trimmedTag = thumbnailState.tagInput.trim()
            val validationMessage = resolveTagValidationMessage(
                tag = trimmedTag,
                addedTagCount = thumbnailState.addedTags.size
            )

            if (trimmedTag.isBlank() || validationMessage != null) {
                return@update currentState.copy(
                    thumbnailAndTagUiState = thumbnailState.copy(
                        tagValidationMessage = validationMessage
                    )
                )
            }

            currentState.copy(
                thumbnailAndTagUiState = thumbnailState.copy(
                    tagInput = "",
                    tagValidationMessage = null,
                    addedTags = thumbnailState.addedTags + trimmedTag
                )
            )
        }
    }

    fun onRemoveTag(tag: String) {
        _uiState.update { currentState ->
            val thumbnailState = currentState.thumbnailAndTagUiState
            val updatedTags = thumbnailState.addedTags.filterNot { it == tag }
            currentState.copy(
                thumbnailAndTagUiState = thumbnailState.copy(
                    addedTags = updatedTags,
                    tagValidationMessage = resolveTagValidationMessage(
                        tag = thumbnailState.tagInput.trim(),
                        addedTagCount = updatedTags.size
                    )
                )
            )
        }
    }

    private fun resolveTagValidationMessage(tag: String, addedTagCount: Int): String? {
        if (tag.isBlank()) return null
        if (addedTagCount >= MAX_TAG_COUNT) return TAG_LIMIT_ERROR_MESSAGE
        return TagValidator.validate(tag)
    }

    private fun moveToStep(step: MatchingCreatePostStep) {
        _uiState.update { currentState ->
            currentState.copy(currentStep = step)
        }
    }

    private fun moveNextStep() {
        val currentState = _uiState.value
        if (!currentState.isNextEnabled) return

        val nextStep = currentState.currentStep.nextOrNull()
        if (nextStep == null) {
            submitPost()
            return
        }

        moveToStep(nextStep)
    }

    private fun movePreviousStep() {
        val previousStep = _uiState.value.currentStep.previousOrNull() ?: return
        moveToStep(previousStep)
    }

    private fun emitUiEvent(event: MatchingCreatePostUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun fetchPopularDestinations() {
        viewModelScope.launch {
            destinationRepository.getPopularDestinations()
                .onSuccess { destinations ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            locationSearchUiState = currentState.locationSearchUiState.copy(
                                popularLocations = destinations.map { it.toUiState() },
                                popularLocationsErrorMessage = null
                            )
                        )
                    }
                }
                .onFailure {
                    _uiState.update { currentState ->
                        currentState.copy(
                            locationSearchUiState = currentState.locationSearchUiState.copy(
                                popularLocations = emptyList(),
                                popularLocationsErrorMessage =
                                POPULAR_DESTINATIONS_ERROR_MESSAGE
                            )
                        )
                    }
                }
        }
    }

    private fun fetchPopularTags() {
        viewModelScope.launch {
            popularTagRepository.getPopularTags()
                .onSuccess { popularTags ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                                popularTags = popularTags
                            )
                        )
                    }
                }
                .onFailure {
                    _uiState.update { currentState ->
                        currentState.copy(
                            thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                                popularTags = emptyList()
                            )
                        )
                    }
                }
        }
    }

    private fun searchDestinations(keyword: String) {
        val trimmedKeyword = keyword.trim()

        if (trimmedKeyword.isEmpty()) {
            searchDebounceJob?.cancel()
            searchRequestJob?.cancel()
            _uiState.update { currentState ->
                currentState.copy(
                    locationSearchUiState = currentState.locationSearchUiState.copy(
                        searchQuery = "",
                        searchedLocations = emptyList(),
                        isSearchLoading = false,
                        hasSearchResult = false,
                        popularLocationsErrorMessage = null
                    )
                )
            }
            return
        }

        _uiState.update { currentState ->
            currentState.copy(
                locationSearchUiState = currentState.locationSearchUiState.copy(
                    searchQuery = trimmedKeyword,
                    isSearchLoading = true,
                    hasSearchResult = false,
                    popularLocationsErrorMessage = null
                )
            )
        }

        searchRequestJob?.cancel()
        searchRequestJob = viewModelScope.launch {
            destinationRepository.searchDestinations(trimmedKeyword)
                .onSuccess { destinations ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            locationSearchUiState = currentState.locationSearchUiState.copy(
                                searchedLocations = destinations.map { it.toUiState() },
                                isSearchLoading = false,
                                hasSearchResult = true,
                                popularLocationsErrorMessage = null
                            )
                        )
                    }
                }
                .onFailure {
                    _uiState.update { currentState ->
                        currentState.copy(
                            locationSearchUiState = currentState.locationSearchUiState.copy(
                                searchedLocations = emptyList(),
                                isSearchLoading = false,
                                hasSearchResult = true,
                                popularLocationsErrorMessage = SEARCH_DESTINATIONS_ERROR_MESSAGE
                            )
                        )
                    }
                }
        }
    }

    private fun debounceSearchDestinations(keyword: String) {
        searchDebounceJob?.cancel()
        searchDebounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            searchDestinations(keyword)
        }
    }

    private fun submitPost() {
        val currentState = _uiState.value
        val selectedLocation = currentState.locationSearchUiState.selectedLocation ?: return
        val startDate = currentState.dateSectionUiState.startDate ?: return
        val endDate = currentState.dateSectionUiState.endDate ?: return

        val request = CreatePostRequest(
            destinationId = selectedLocation.id,
            title = currentState.postWritingUiState.title,
            content = currentState.postWritingUiState.content,
            startDate = startDate,
            endDate = endDate,
            recruitCapacity = currentState.recruitmentSectionUiState.recruitCount,
            preferredGender = currentState.ageSectionUiState.preferredGender.code,
            isAgeAny = currentState.ageSectionUiState.isAgeAny,
            minAge = currentState.ageSectionUiState.minAge,
            maxAge = currentState.ageSectionUiState.maxAge,
            photoUrl = currentState.thumbnailAndTagUiState.photoUrl,
            tags = currentState.thumbnailAndTagUiState.addedTags,
            companionType = currentState.recruitmentSectionUiState.companionType.code
        )

        viewModelScope.launch {
            createPostRepository.createPost(request)
                .onSuccess {
                    _uiEvent.emit(MatchingCreatePostUiEvent.Complete)
                }
                .onFailure { throwable ->
                    Log.e("MatchingCreatePostViewModel", "submitPost failed", throwable)
                }
        }
    }
}

private fun PopularDestination.toUiState(): LocationSearchItemUiState {
    return LocationSearchItemUiState(
        id = id,
        countryCode = countryCode,
        countryName = countryName,
        city = city,
        image = image
    )
}

package com.gildongmu.dduru.presentation.feature.mypage.model

import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class TravelPreferenceDraft @Inject constructor() {
    private val _destinations = MutableStateFlow<List<DestinationPreference>>(emptyList())
    val destinations: StateFlow<List<DestinationPreference>> = _destinations.asStateFlow()

    fun updateDestinations(destinations: List<DestinationPreference>) {
        _destinations.value = destinations
    }
}

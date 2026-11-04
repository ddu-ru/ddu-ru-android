package com.gildongmu.dduru.presentation.feature.matching.model

data class MatchingFilterOptions(
    val destinationId: Long? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val recruitmentStatus: String? = null,
    val preferredGender: String? = null,
    val minRecruitCapacity: Int? = null,
    val maxRecruitCapacity: Int? = null,
    val minAge: Int? = null,
    val maxAge: Int? = null,
    val companionType: String? = null
) {
    val hasAppliedFilter: Boolean
        get() = destinationId != null ||
            startDate != null ||
            endDate != null ||
            recruitmentStatus != null ||
            preferredGender != null ||
            minRecruitCapacity != null ||
            maxRecruitCapacity != null ||
            minAge != null ||
            maxAge != null ||
            companionType != null
}

data class MatchingDestinationOption(
    val id: Long,
    val name: String
)

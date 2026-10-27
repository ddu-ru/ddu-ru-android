package com.gildongmu.dduru.presentation.feature.matching.model

data class MatchingFilterOptions(
    val startDate: String? = null,
    val endDate: String? = null,
    val recruitmentStatus: String? = null,
    val preferredGender: String? = null,
    val minRecruitCapacity: Int? = null,
    val maxRecruitCapacity: Int? = null,
    val minAge: Int? = null,
    val maxAge: Int? = null,
    val companionType: String? = null
)

package com.gildongmu.dduru.domain.entity.home

data class HomeUpcomingTrip(
    val journeyId: Long,
    val title: String,
    val dDay: Int,
    val startDate: String,
    val endDate: String,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val pendingTaskCount: Int
)

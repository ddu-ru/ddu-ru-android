package com.gildongmu.dduru.domain.entity

data class SurveyAnswers(
    val rhythmQ1: Int,
    val rhythmQ2: Int,
    val rhythmQ3: Int,
    val activityTags: List<Int>,
    val consumptionQ1: Int,
    val consumptionQ2: Int,
    val consumptionQ3: Int,
    val energyQ1: Int,
    val energyQ2: Int,
    val energyQ3: Int,
    val decisionQ1: Int,
    val decisionQ2: Int,
    val decisionQ3: Int,
    val recordStyle: Int
)

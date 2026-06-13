package com.gildongmu.dduru.data.model

import com.gildongmu.dduru.domain.entity.SurveyAnswers

data class SurveyState(
    val rhythmQ1: Int? = null,
    val rhythmQ2: Int? = null,
    val rhythmQ3: Int? = null,
    val activityTags: Set<Int> = emptySet(),
    val consumptionQ1: Int? = null,
    val consumptionQ2: Int? = null,
    val consumptionQ3: Int? = null,
    val energyQ1: Int? = null,
    val energyQ2: Int? = null,
    val energyQ3: Int? = null,
    val decisionQ1: Int? = null,
    val decisionQ2: Int? = null,
    val decisionQ3: Int? = null,
    val recordStyle: Int? = null
) {
    fun toAnswers(): SurveyAnswers? {
        val singleAnswers = listOf(
            rhythmQ1, rhythmQ2, rhythmQ3,
            consumptionQ1, consumptionQ2, consumptionQ3,
            energyQ1, energyQ2, energyQ3,
            decisionQ1, decisionQ2, decisionQ3,
            recordStyle
        )

        if (singleAnswers.any { it == null } || activityTags.isEmpty() || activityTags.size > 3) {
            return null
        }

        return SurveyAnswers(
            rhythmQ1 = rhythmQ1!!,
            rhythmQ2 = rhythmQ2!!,
            rhythmQ3 = rhythmQ3!!,
            activityTags = activityTags.toList(),
            consumptionQ1 = consumptionQ1!!,
            consumptionQ2 = consumptionQ2!!,
            consumptionQ3 = consumptionQ3!!,
            energyQ1 = energyQ1!!,
            energyQ2 = energyQ2!!,
            energyQ3 = energyQ3!!,
            decisionQ1 = decisionQ1!!,
            decisionQ2 = decisionQ2!!,
            decisionQ3 = decisionQ3!!,
            recordStyle = recordStyle!!
        )
    }
}

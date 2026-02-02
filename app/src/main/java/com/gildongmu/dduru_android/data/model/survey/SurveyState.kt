package com.gildongmu.dduru_android.data.model.survey

import com.gildongmu.dduru_android.data.remote.dto.request.SurveyRequest

data class SurveyState(
    val q1: Int? = null,
    val q2: Int? = null,
    val q3: Int? = null,
    val q4: Int? = null,
    val q5: Int? = null,
    val q6: Int? = null,
    val q7: Set<Int> = emptySet(),
    val q8: Int? = null,
    val q9: Int? = null,
    val q10: Int? = null,
    val q11: Int? = null
) {
    fun toRequest(): SurveyRequest? {
        val singleAnswers = listOf(q1, q2, q3, q4, q5, q6, q8, q9, q10, q11)

        if (singleAnswers.any { it == null } || q7.isEmpty()) {
            return null
        }

        return SurveyRequest(
            q1 = q1!!,
            q2 = q2!!,
            q3 = q3!!,
            q4 = q4!!,
            q5 = q5!!,
            q6 = q6!!,
            q7 = q7.toList(),
            q8 = q8!!,
            q9 = q9!!,
            q10 = q10!!,
            q11 = q11!!
        )
    }
}

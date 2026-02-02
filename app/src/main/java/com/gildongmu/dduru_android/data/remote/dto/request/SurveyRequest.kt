package com.gildongmu.dduru_android.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurveyRequest(
    @SerialName("q1")
    val q1: Int,

    @SerialName("q2")
    val q2: Int,

    @SerialName("q3")
    val q3: Int,

    @SerialName("q4")
    val q4: Int,

    @SerialName("q5")
    val q5: Int,

    @SerialName("q6")
    val q6: Int,

    @SerialName("q7")
    val q7: List<Int>,

    @SerialName("q8")
    val q8: Int,

    @SerialName("q9")
    val q9: Int,

    @SerialName("q10")
    val q10: Int,

    @SerialName("q11")
    val q11: Int
)

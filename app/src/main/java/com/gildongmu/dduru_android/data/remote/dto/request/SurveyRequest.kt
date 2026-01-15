package com.gildongmu.dduru_android.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class SurveyRequest(
    val q1: Int,
    val q2: Int,
    val q3: Int,
    val q4: Int,
    val q5: Int,
    val q6: Int,
    @SerializedName("q7")
    val q7: List<Int>,
    val q8: Int,
    val q9: Int,
    val q10: Int,
    val q11: Int
)

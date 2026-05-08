package com.gildongmu.dduru.domain.register.model

enum class SurveyStatus {
    NOT_STARTED,
    COMPLETED,
    SKIPPED;

    companion object {
        fun fromApi(value: String?): SurveyStatus {
            return when (value?.uppercase()) {
                "NOT_STARTED", "NOTSTARTED", "PENDING" -> NOT_STARTED
                "COMPLETED", "COMPLETE", "DONE" -> COMPLETED
                "SKIPPED", "SKIP" -> SKIPPED
                else -> NOT_STARTED
            }
        }
    }
}

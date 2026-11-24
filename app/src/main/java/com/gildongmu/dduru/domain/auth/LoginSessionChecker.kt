package com.gildongmu.dduru.domain.auth

enum class LoginSessionState {
    VALID,
    EXPIRED,
    UNKNOWN
}

interface LoginSessionChecker {
    suspend fun check(): LoginSessionState
}

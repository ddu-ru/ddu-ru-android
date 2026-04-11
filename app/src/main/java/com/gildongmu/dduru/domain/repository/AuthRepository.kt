package com.gildongmu.dduru.domain.repository

import android.content.Context

interface AuthRepository {
    suspend fun loginWithKakao(idToken: String): Result<Boolean>
    suspend fun loginWithGoogle(context: Context): Result<Boolean>
}

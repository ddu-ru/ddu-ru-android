package com.gildongmu.dduru_android.domain.repository

import android.content.Intent

interface AuthRepository {
    // 성공하면 true(신규유저 여부)를 반환, 실패하면 Error를 반환하는 Result 타입 사용
    suspend fun loginWithKakao(idToken: String): Result<Boolean>
    fun getGoogleSignInIntent(): Intent
    suspend fun loginWithGoogle(intent: Intent): Result<Boolean>
}

package com.gildongmu.dduru_android.core.common.util

import android.content.Context
import android.content.Intent
import android.util.Log
import com.gildongmu.dduru_android.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

object GoogleLoginHelper : SocialLoginHelper("GoogleLogin") {

     //구글 로그인 화면을 띄우기 위한 Intent를 생성
    fun getSignInIntent(context: Context): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        googleSignInClient.signOut()

        return googleSignInClient.signInIntent
    }

    /**
     * 로그인 결과(Intent)를 처리하여 ID Token을 추출합니다.
     */
    fun handleActivityResult(data: Intent?, callback: SocialLoginCallback) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task, callback)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, callback: SocialLoginCallback) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
                // 성공 로그
                Log.d(tag, "구글 로그인 성공 ID Token: $idToken")
                callback.onSuccess(idToken)
            } else {
                callback.onFailure("ID Token이 없습니다.")
            }
        } catch (e: ApiException) {
            //사용자가 창을 닫거나 취소한 경우 (StatusCode 12501)
            if (e.statusCode == 12501) {
                Log.d(tag, "사용자가 로그인을 취소했습니다.")
                callback.onFailure("CANCELED")
                return
            }

            Log.e(tag, "구글 로그인 실패: ${e.statusCode}", e)
            callback.onFailure("구글 로그인 실패 (코드: ${e.statusCode})")
        }
    }

    override fun login(context: Context, callback: SocialLoginCallback) {
        // Empty implementation
    }
}

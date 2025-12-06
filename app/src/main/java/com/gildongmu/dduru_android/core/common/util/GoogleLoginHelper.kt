package com.gildongmu.dduru_android.core.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.gildongmu.dduru_android.BuildConfig
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException

object GoogleLoginHelper : SocialLoginHelper("GoogleLogin") {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    fun initGoogleLogin(activity: Activity, launcher: ActivityResultLauncher<IntentSenderRequest>) {
        oneTapClient = Identity.getSignInClient(activity)
        val webClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(webClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                launcher.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
            }
            .addOnFailureListener { e ->
                Log.e(tag, "로그인 UI 표시 실패", e)
            }
    }

    fun handleResult(context: Activity, data: Intent?, callback: SocialLoginCallback) {
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                callback.onSuccess(idToken)
            } else {
                callback.onFailure("ID Token이 null입니다.")
            }
        } catch (e: ApiException) {
            Log.e(tag, "Google Sign-In 결과 파싱 실패", e)
            callback.onFailure(e.statusMessage)
        }
    }

    override fun login(context: Context, callback: SocialLoginCallback) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, "login() 호출됨")
            callback.onFailure("Google 로그인은 initGoogleLogin()으로 시작해야 해서 실패.")
        }
    }
}

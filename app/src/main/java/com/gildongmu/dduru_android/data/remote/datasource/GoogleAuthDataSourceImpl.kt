package com.gildongmu.dduru_android.data.remote.datasource

import android.content.Context
import android.content.Intent
import com.gildongmu.dduru_android.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleAuthDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GoogleAuthDataSource {

    override fun getSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(context, gso)
        client.signOut()
        return client.signInIntent
    }

    override fun getIdTokenFromIntent(data: Intent?): String? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        return try {
            val account = task.getResult(ApiException::class.java)
            account.idToken
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

package com.gildongmu.dduru.data.remote.datasourceimpl

/* Legacy Credential Manager flow (kept for reference)
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.gildongmu.dduru.BuildConfig
import com.gildongmu.dduru.data.remote.datasource.GoogleAuthDataSource
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleAuthDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GoogleAuthDataSource {

    override suspend fun getGoogleIdToken(context: Context): String? {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetSignInWithGoogleOption.Builder(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                Log.d("GoogleLogin", "ID 토큰 수신 성공: $idToken")
                idToken
            } else {
                Log.e("GoogleLogin", "잘못된 크리덴셜 타입: ${credential.type}")
                null
            }
        } catch (e: Exception) {
            Log.e("GoogleLogin", "토큰 획득 실패 상세 원인: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}
*/

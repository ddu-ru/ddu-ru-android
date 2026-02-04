package com.gildongmu.dduru.data.remote.datasourceimpl

import android.content.Context
import com.gildongmu.dduru.data.remote.datasource.KakaoAuthDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class KakaoAuthDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KakaoAuthDataSource {

    override suspend fun getIdToken(): String? = suspendCancellableCoroutine { cont ->

        fun resumeSafely(value: String?) {
            if (cont.isActive) cont.resume(value)
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> {
                    resumeSafely(null)
                }

                else -> {
                    resumeSafely(token?.idToken)
                }
            }
        }

        if (UserApiClient.Companion.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.Companion.instance.loginWithKakaoTalk(context) { token, error ->
                val canceled = error is ClientError && error.reason == ClientErrorCause.Cancelled
                if (canceled) {
                    resumeSafely(null)
                    return@loginWithKakaoTalk
                }

                if (error != null) {
                    UserApiClient.Companion.instance.loginWithKakaoAccount(
                        context,
                        callback = callback
                    )
                } else {
                    resumeSafely(token?.idToken)
                }
            }
        } else {
            UserApiClient.Companion.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}

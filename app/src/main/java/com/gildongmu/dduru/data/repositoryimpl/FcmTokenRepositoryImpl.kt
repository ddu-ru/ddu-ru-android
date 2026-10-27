package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.datasource.FcmTokenRemoteDataSource
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.interceptor.TokenAuthenticator
import com.gildongmu.dduru.domain.repository.FcmTokenRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class FcmTokenRepositoryImpl @Inject constructor(
    private val fcmTokenRemoteDataSource: FcmTokenRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenAuthenticator: TokenAuthenticator
) : FcmTokenRepository {
    override suspend fun registerToken(token: String): Result<Unit> {
        if (!hasAccessToken()) return Result.success(Unit)

        return runCatchingFcmTokenApi {
            val response = fcmTokenRemoteDataSource.registerToken(token)

            if (!response.isSuccessful) {
                throw IllegalStateException("FCM 토큰 등록 실패: status=${response.code()}")
            }
        }
    }

    override suspend fun registerCurrentToken(): Result<Unit> {
        if (!hasAccessToken()) return Result.success(Unit)

        return runCatchingFcmTokenApi {
            val token = FirebaseMessaging.getInstance().token.await()
            val response = fcmTokenRemoteDataSource.registerToken(token)

            if (!response.isSuccessful) {
                throw IllegalStateException("FCM 토큰 등록 실패: status=${response.code()}")
            }
        }
    }

    override suspend fun deleteToken(token: String): Result<Unit> {
        if (!hasAccessToken()) return Result.success(Unit)

        return runCatchingFcmTokenApi {
            val response = fcmTokenRemoteDataSource.deleteToken(token)

            if (!response.isSuccessful) {
                throw IllegalStateException("FCM 토큰 삭제 실패: status=${response.code()}")
            }
        }
    }

    override suspend fun deleteCurrentToken(): Result<Unit> {
        if (!hasAccessToken()) return Result.success(Unit)

        return runCatchingFcmTokenApi {
            val token = FirebaseMessaging.getInstance().token.await()
            val response = fcmTokenRemoteDataSource.deleteToken(token)

            if (!response.isSuccessful) {
                throw IllegalStateException("FCM 토큰 삭제 실패: status=${response.code()}")
            }
        }
    }

    private suspend inline fun <T> runCatchingFcmTokenApi(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: HttpException) {
            Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }

    private suspend fun hasAccessToken(): Boolean {
        val accessToken = tokenLocalDataSource.accessToken.first()
        return !accessToken.isNullOrBlank() && !tokenAuthenticator.shouldRefresh(accessToken)
    }
}

private suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        addOnSuccessListener { result ->
            continuation.resume(result)
        }
        addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
        addOnCanceledListener {
            continuation.cancel()
        }
    }
}

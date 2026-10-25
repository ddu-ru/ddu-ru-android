package com.gildongmu.dduru.data.repositoryimpl.home

import com.gildongmu.dduru.data.mapper.home.toDomain
import com.gildongmu.dduru.data.remote.datasource.home.HomeNotificationRemoteDataSource
import com.gildongmu.dduru.domain.entity.home.HomeNotification
import com.gildongmu.dduru.domain.entity.home.HomeNotificationPage
import com.gildongmu.dduru.domain.repository.home.HomeNotificationRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class HomeNotificationRepositoryImpl @Inject constructor(
    private val homeNotificationRemoteDataSource: HomeNotificationRemoteDataSource
) : HomeNotificationRepository {
    override suspend fun getNotifications(cursor: Long?): Result<HomeNotificationPage> {
        return runCatchingNotificationApi {
            val response = homeNotificationRemoteDataSource.getNotifications(
                cursor = cursor,
                size = NOTIFICATION_PAGE_SIZE
            )

            if (response.status.isNotificationSuccessStatus() && response.data != null) {
                HomeNotificationPage(
                    notifications = response.data.notifications.map { it.toDomain() },
                    nextCursor = response.data.nextCursor,
                    hasNext = response.data.hasNext
                )
            } else {
                throw IllegalStateException("알림 목록 조회 실패: status=${response.status}")
            }
        }
    }

    override suspend fun markAsRead(notificationId: Long): Result<Unit> {
        return runCatchingNotificationApi {
            val response = homeNotificationRemoteDataSource.markAsRead(notificationId)

            if (response.status.isNotificationSuccessStatus() && response.data?.success == true) {
                Unit
            } else {
                throw IllegalStateException("알림 읽음 처리 실패: status=${response.status}")
            }
        }
    }

    override suspend fun getUnreadCount(): Result<Int> {
        return runCatchingNotificationApi {
            val response = homeNotificationRemoteDataSource.getUnreadCount()

            if (response.status.isNotificationSuccessStatus() && response.data != null) {
                response.data.unreadCount
            } else {
                throw IllegalStateException("안 읽은 알림 개수 조회 실패: status=${response.status}")
            }
        }
    }

    override suspend fun markAllAsRead(): Result<Unit> {
        return runCatchingNotificationApi {
            val response = homeNotificationRemoteDataSource.markAllAsRead()

            if (response.status.isNotificationSuccessStatus() && response.data?.success == true) {
                Unit
            } else {
                throw IllegalStateException("알림 전체 읽음 처리 실패: status=${response.status}")
            }
        }
    }

    override suspend fun updateSettings(enabled: Boolean): Result<Unit> {
        return runCatchingNotificationApi {
            val response = homeNotificationRemoteDataSource.updateSettings(enabled)

            if (response.isSuccessful) {
                Unit
            } else {
                throw IllegalStateException("알림 설정 변경 실패: status=${response.code()}")
            }
        }
    }

    private suspend inline fun <T> runCatchingNotificationApi(block: suspend () -> T): Result<T> {
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

    private fun Int.isNotificationSuccessStatus(): Boolean {
        return this == API_SUCCESS_STATUS || this == HTTP_SUCCESS_STATUS
    }

    private companion object {
        const val NOTIFICATION_PAGE_SIZE = 20
        const val API_SUCCESS_STATUS = 0
        const val HTTP_SUCCESS_STATUS = 200
    }
}

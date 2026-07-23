package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.toDomain
import com.gildongmu.dduru.data.remote.datasource.HomeRemoteDataSource
import com.gildongmu.dduru.domain.entity.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.HomePopularDestinations
import com.gildongmu.dduru.domain.repository.HomeRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getHomeInitialConfig(): Result<HomeInitialConfig> {
        return runCatchingApi {
            val response = homeRemoteDataSource.getHomeInitialConfig()

            if (response.status == HOME_SUCCESS_STATUS && response.data != null) {
                response.data.toDomain()
            } else {
                throw IllegalStateException("홈 초기 구성 조회 실패: status=${response.status}")
            }
        }
    }

    override suspend fun getHomeMateRecommendations(): Result<HomeMateRecommendations> {
        return runCatchingApi {
            val response = homeRemoteDataSource.getHomeMateRecommendations()

            if (response.status == HOME_SUCCESS_STATUS && response.data != null) {
                response.data.toDomain()
            } else {
                throw IllegalStateException("홈 메이트 추천 조회 실패: status=${response.status}")
            }
        }
    }

    override suspend fun getHomePopularDestinations(): Result<HomePopularDestinations> {
        return runCatchingApi {
            val response = homeRemoteDataSource.getHomePopularDestinations()

            if (response.status == HOME_SUCCESS_STATUS && response.data != null) {
                response.data.toDomain()
            } else {
                throw IllegalStateException("홈 인기 여행지 조회 실패: status=${response.status}")
            }
        }
    }

    private inline fun <T> runCatchingApi(block: () -> T): Result<T> {
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

    private companion object {
        const val HOME_SUCCESS_STATUS = 0
    }
}

package com.gildongmu.dduru.data.repositoryimpl.home

import com.gildongmu.dduru.data.mapper.home.toDomain
import com.gildongmu.dduru.data.remote.datasource.home.HomeRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.entity.home.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.home.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.home.HomePopularDestinations
import com.gildongmu.dduru.domain.entity.home.HomeSameAgeTrip
import com.gildongmu.dduru.domain.entity.home.HomeSameDestinationTrip
import com.gildongmu.dduru.domain.entity.home.HomeSuperHost
import com.gildongmu.dduru.domain.entity.home.HomeUpcomingTrip
import com.gildongmu.dduru.domain.repository.home.HomeRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getHomeInitialConfig(): Result<HomeInitialConfig> {
        return requestHomeApi(
            errorMessage = "홈 초기 구성 조회 실패",
            request = { homeRemoteDataSource.getHomeInitialConfig() },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun getHomeMateRecommendations(): Result<HomeMateRecommendations> {
        return requestHomeApi(
            errorMessage = "홈 메이트 추천 조회 실패",
            request = { homeRemoteDataSource.getHomeMateRecommendations() },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun getHomePopularDestinations(): Result<HomePopularDestinations> {
        return requestHomeApi(
            errorMessage = "홈 인기 여행지 조회 실패",
            request = { homeRemoteDataSource.getHomePopularDestinations() },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun getHomeSameAgeTrips(): Result<List<HomeSameAgeTrip>> {
        return requestHomeApi(
            errorMessage = "홈 동갑 여행 조회 실패",
            request = { homeRemoteDataSource.getHomeSameAgeTrips() },
            mapper = { response -> response.map { it.toDomain() } }
        )
    }

    override suspend fun getHomeSameDestinationTrips(): Result<List<HomeSameDestinationTrip>> {
        return requestHomeApi(
            errorMessage = "홈 같은 여행지 여행 조회 실패",
            request = { homeRemoteDataSource.getHomeSameDestinationTrips() },
            mapper = { response -> response.map { it.toDomain() } }
        )
    }

    override suspend fun getHomeSuperHosts(): Result<List<HomeSuperHost>> {
        return requestHomeApi(
            errorMessage = "홈 슈퍼호스트 조회 실패",
            request = { homeRemoteDataSource.getHomeSuperHosts() },
            mapper = { response -> response.map { it.toDomain() } }
        )
    }

    override suspend fun getHomeUpcomingTrip(): Result<HomeUpcomingTrip> {
        return requestHomeApi(
            errorMessage = "홈 다가오는 여행 조회 실패",
            request = { homeRemoteDataSource.getHomeUpcomingTrip() },
            mapper = { it.toDomain() }
        )
    }

    private suspend inline fun <Response, Domain> requestHomeApi(
        errorMessage: String,
        crossinline request: suspend () -> ApiResponse<Response>,
        crossinline mapper: (Response) -> Domain
    ): Result<Domain> {
        return runCatchingApi {
            val response = request()

            if (response.status.isHomeSuccessStatus() && response.data != null) {
                mapper(response.data)
            } else if (response.status.isHomeNoDataStatus()) {
                throw ExceptionResponse(response.status)
            } else {
                throw IllegalStateException("$errorMessage: status=${response.status}")
            }
        }
    }

    private suspend inline fun <T> runCatchingApi(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: ExceptionResponse) {
            Result.failure(e)
        } catch (e: HttpException) {
            if (e.code().isHomeNoDataStatus()) {
                Result.failure(ExceptionResponse(e.code()))
            } else {
                Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }

    private fun Int.isHomeSuccessStatus(): Boolean {
        return this == HTTP_SUCCESS_STATUS
    }

    private fun Int.isHomeNoDataStatus(): Boolean {
        return this == HTTP_NOT_FOUND_STATUS
    }

    private companion object {
        const val HTTP_SUCCESS_STATUS = 200
        const val HTTP_NOT_FOUND_STATUS = 404
    }
}

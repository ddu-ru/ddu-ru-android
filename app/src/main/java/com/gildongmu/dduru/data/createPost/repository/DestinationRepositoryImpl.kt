package com.gildongmu.dduru.data.createPost.repository

import com.gildongmu.dduru.data.createPost.api.DestinationApi
import com.gildongmu.dduru.data.createPost.mapper.toDomain
import com.gildongmu.dduru.domain.createPost.DestinationErrorMapper
import com.gildongmu.dduru.domain.createPost.entity.PopularDestination
import com.gildongmu.dduru.domain.createPost.repository.DestinationRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

class DestinationRepositoryImpl @Inject constructor(
    private val destinationApi: DestinationApi
) : DestinationRepository {
    override suspend fun getPopularDestinations(): Result<List<PopularDestination>> {
        return try {
            val response = destinationApi.getPopularDestinations()

            when (response.status) {
                200 -> {
                    if (response.data == null) {
                        Result.failure(Exception("서버 응답에 데이터가 없습니다"))
                    } else {
                        Result.success(response.data.map { it.toDomain() })
                    }
                }

                401 -> {
                    Result.failure(
                        Exception(DestinationErrorMapper.popularDestinationMessage(response.status))
                    )
                }

                500 -> {
                    Result.failure(
                        Exception(DestinationErrorMapper.popularDestinationMessage(response.status))
                    )
                }

                else -> {
                    Result.failure(
                        Exception(DestinationErrorMapper.popularDestinationMessage(response.status))
                    )
                }
            }
        } catch (e: HttpException) {
            Result.failure(Exception(DestinationErrorMapper.popularDestinationMessage(e.code())))
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }

    override suspend fun searchDestinations(keyword: String): Result<List<PopularDestination>> {
        return try {
            val response = destinationApi.searchDestinations(keyword)

            when (response.status) {
                200 -> {
                    if (response.data == null) {
                        Result.failure(Exception("서버 응답에 데이터가 없습니다"))
                    } else {
                        Result.success(response.data.map { it.toDomain() })
                    }
                }

                401 -> {
                    Result.failure(
                        Exception(DestinationErrorMapper.searchDestinationMessage(response.status))
                    )
                }

                500 -> {
                    Result.failure(
                        Exception(DestinationErrorMapper.searchDestinationMessage(response.status))
                    )
                }

                else -> {
                    Result.failure(
                        Exception(DestinationErrorMapper.searchDestinationMessage(response.status))
                    )
                }
            }
        } catch (e: HttpException) {
            Result.failure(Exception(DestinationErrorMapper.searchDestinationMessage(e.code())))
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }
}

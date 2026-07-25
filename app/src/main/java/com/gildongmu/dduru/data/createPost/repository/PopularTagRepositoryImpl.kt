package com.gildongmu.dduru.data.createPost.repository

import com.gildongmu.dduru.data.createPost.api.PopularTagApi
import com.gildongmu.dduru.domain.createPost.PopularTagErrorMapper
import com.gildongmu.dduru.domain.createPost.repository.PopularTagRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

class PopularTagRepositoryImpl @Inject constructor(
    private val popularTagApi: PopularTagApi
) : PopularTagRepository {
    override suspend fun getPopularTags(): Result<List<String>> {
        return try {
            val response = popularTagApi.getPopularTags()

            when (response.status) {
                200 -> {
                    if (response.data == null) {
                        Result.failure(Exception("서버 응답에 데이터가 없습니다"))
                    } else {
                        Result.success(response.data)
                    }
                }

                401 -> {
                    Result.failure(
                        Exception(PopularTagErrorMapper.popularTagMessage(response.status))
                    )
                }

                500 -> {
                    Result.failure(
                        Exception(PopularTagErrorMapper.popularTagMessage(response.status))
                    )
                }

                else -> {
                    Result.failure(
                        Exception(PopularTagErrorMapper.popularTagMessage(response.status))
                    )
                }
            }
        } catch (e: HttpException) {
            Result.failure(Exception(PopularTagErrorMapper.popularTagMessage(e.code()), e))
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요.", e))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }
}

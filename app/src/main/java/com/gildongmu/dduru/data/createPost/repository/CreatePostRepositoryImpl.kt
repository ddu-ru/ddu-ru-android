package com.gildongmu.dduru.data.createPost.repository

import com.gildongmu.dduru.data.createPost.api.CreatePostApi
import com.gildongmu.dduru.data.createPost.model.CreatePostRequest
import com.gildongmu.dduru.domain.createPost.repository.CreatePostRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

class CreatePostRepositoryImpl @Inject constructor(
    private val createPostApi: CreatePostApi
) : CreatePostRepository {
    override suspend fun createPost(request: CreatePostRequest): Result<Long> {
        return try {
            val response = createPostApi.createPost(request)

            when {
                response.status != 201 -> {
                    Result.failure(Exception("게시글 작성에 실패했습니다. status=${response.status}"))
                }
                response.data == null -> {
                    Result.failure(Exception("서버 응답에 데이터가 없습니다"))
                }
                else -> {
                    Result.success(response.data.id)
                }
            }
        } catch (e: HttpException) {
            Result.failure(Exception("게시글 작성에 실패했습니다.", e))
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }
}

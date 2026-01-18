package com.gildongmu.dduru_android.data.repositoryimpl

import com.gildongmu.dduru_android.data.remote.datasource.SurveyRemoteDataSource
import com.gildongmu.dduru_android.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru_android.domain.repository.SurveyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyRemoteDataSource: SurveyRemoteDataSource
) : SurveyRepository {

    override suspend fun submitSurvey(request: SurveyRequest): Result<SurveyResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = surveyRemoteDataSource.submitSurvey(request)

                // 성공 (201)
                if (response.status == 201 && response.data != null) {
                    Result.success(response.data)
                } else {
                    Result.failure(Exception("설문 제출 실패: status=${response.status}"))
                }
            } catch (e: retrofit2.HttpException) {
                // HTTP 오류 (400, 500 등)
                Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
            } catch (e: java.io.IOException) {
                // 네트워크 오류
                Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
            } catch (e: Exception) {
                // 기타 오류
                Result.failure(Exception("알 수 없는 오류: ${e.message}"))
            }
        }
    }

    override suspend fun getMySurveyResult(): Result<SurveyResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = surveyRemoteDataSource.getMySurveyResult()

                // 성공 (200)
                if (response.status == 200 && response.data != null) {
                    Result.success(response.data)
                } else if (response.status == 404) {
                    // 설문 결과 없음
                    Result.failure(Exception("설문 결과를 찾을 수 없습니다."))
                } else {
                    Result.failure(Exception("조회 실패: status=${response.status}"))
                }
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) {
                    Result.failure(Exception("설문 결과를 찾을 수 없습니다."))
                } else {
                    Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
                }
            } catch (e: java.io.IOException) {
                Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
            } catch (e: Exception) {
                Result.failure(Exception("알 수 없는 오류: ${e.message}"))
            }
        }
    }
}

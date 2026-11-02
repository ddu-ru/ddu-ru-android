package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.toDomain
import com.gildongmu.dduru.data.mapper.toRequest
import com.gildongmu.dduru.data.remote.datasource.SurveyRemoteDataSource
import com.gildongmu.dduru.domain.entity.SurveyAnswers
import com.gildongmu.dduru.domain.entity.SurveyQuestions
import com.gildongmu.dduru.domain.entity.SurveyResult
import com.gildongmu.dduru.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyRemoteDataSource: SurveyRemoteDataSource
) : SurveyRepository {

    override suspend fun submitSurvey(answers: SurveyAnswers): Result<SurveyResult> {
        return try {
            val request = answers.toRequest()
            val response = surveyRemoteDataSource.submitSurvey(request)

            when {
                response.status != 201 -> {
                    Result.failure(Exception("예상치 못한 상태 코드: ${response.status}"))
                }
                response.data == null -> {
                    Result.failure(Exception("서버 응답에 데이터가 없습니다"))
                }
                else -> {
                    try {
                        val domain = response.data.toDomain()
                        Result.success(domain)
                    } catch (e: Exception) {
                        Result.failure(Exception("데이터 변환 중 오류: ${e.message}", e))
                    }
                }
            }
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            android.util.Log.e("SurveyRepo", "submitSurvey 실패 - HTTP ${e.code()}: $errorBody")
            Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
        } catch (e: java.io.IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}", e))
        }
    }

    override suspend fun getMySurveyResult(): Result<SurveyResult> {
        return try {
            val response = surveyRemoteDataSource.getMySurveyResult()

            if (response.status == 200 && response.data != null) {
                Result.success(response.data.toDomain())
            } else if (response.status == 404) {
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

    override suspend fun getSurveyQuestions(): Result<SurveyQuestions> {
        return try {
            val response = surveyRemoteDataSource.getSurveyQuestions()

            if (response.status == 200 && response.data != null) {
                Result.success(response.data.toDomain())
            } else {
                Result.failure(Exception("질문 조회 실패: status=${response.status}"))
            }
        } catch (e: retrofit2.HttpException) {
            Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
        } catch (e: java.io.IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}"))
        }
    }

    override suspend fun skipSurvey(): Result<Unit> {
        return try {
            val response = surveyRemoteDataSource.skipSurvey()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("설문을 건너뛰지 못했어요."))
            }
        } catch (e: java.io.IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception("설문을 건너뛰지 못했어요."))
        }
    }

    override suspend fun updateSurvey(answers: SurveyAnswers): Result<SurveyResult> {
        return try {
            val response = surveyRemoteDataSource.updateSurvey(answers.toRequest())
            val data = response.data
            if (response.status == 200 && data != null) {
                Result.success(data.toDomain())
            } else {
                Result.failure(Exception("설문 수정에 실패했어요."))
            }
        } catch (e: retrofit2.HttpException) {
            val message = if (e.code() == 429) "아직 다시 테스트할 수 없어요." else "설문 수정에 실패했어요."
            Result.failure(Exception(message))
        } catch (e: java.io.IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception("설문 수정에 실패했어요."))
        }
    }
}

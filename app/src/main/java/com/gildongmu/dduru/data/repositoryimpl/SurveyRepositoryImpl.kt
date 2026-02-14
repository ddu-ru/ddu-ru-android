package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.datasource.SurveyRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.request.SurveyRequest
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.data.remote.dto.response.SurveyQuestionsResponse
import com.gildongmu.dduru.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyRemoteDataSource: SurveyRemoteDataSource
) : SurveyRepository {
    override suspend fun submitSurvey(request: SurveyRequest): Result<SurveyResponse> {
        return try {
            val response = surveyRemoteDataSource.submitSurvey(request)
            if (response.status == 201 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("설문 제출 실패: status=${response.status}"))
            }
        } catch (e: retrofit2.HttpException) {
            Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
        } catch (e: java.io.IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류: ${e.message}"))
        }
    }

    override suspend fun getMySurveyResult(): Result<SurveyResponse> {
        return try {
            val response = surveyRemoteDataSource.getMySurveyResult()
            if (response.status == 200 && response.data != null) {
                Result.success(response.data)
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

    override suspend fun getSurveyQuestions(): Result<SurveyQuestionsResponse> {
        return try {
            val response = surveyRemoteDataSource.getSurveyQuestions()
            if (response.status == 200 && response.data != null) {
                Result.success(response.data)
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
}

package com.gildongmu.dduru.data.register.nickname.repositoryimpl

import com.gildongmu.dduru.data.register.nickname.service.NicknameService
import com.gildongmu.dduru.domain.register.nickname.entity.NicknameErrorMapper
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import javax.inject.Inject
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

class NicknameRepositoryImpl @Inject constructor(
    private val nicknameService: NicknameService
) : NicknameRepository {

    override suspend fun getRandomNickname(): Result<String> {
        return try {
            val response = nicknameService.getRandomNickname()
            val nickname = response.data ?: return Result.failure(Exception("닉네임을 받아오지 못했습니다."))
            Result.success(nickname)
        } catch (e: HttpException) {
            Result.failure(Exception(NicknameErrorMapper.toMessage(e.code())))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "닉네임을 받아오지 못했습니다."))
        }
    }

    override suspend fun checkNicknameAvailability(nickname: String): Result<Boolean> {
        return try {
            val response = nicknameService.checkNicknameAvailability(nickname)
            val isAvailable = response.status == 200 && response.data != false
            Result.success(isAvailable)
        } catch (e: HttpException) {
            Result.failure(Exception(NicknameErrorMapper.toMessage(e.code())))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "이미 사용중인 닉네임입니다."))
        }
    }
}

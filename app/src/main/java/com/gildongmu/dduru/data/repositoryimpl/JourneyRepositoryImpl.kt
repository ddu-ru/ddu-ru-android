package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.journey.toDomain
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyMemberRoleRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyMemberRoleUpdateRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyUpdateRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.service.JourneyService
import com.gildongmu.dduru.domain.entity.journey.JourneyDetail
import com.gildongmu.dduru.domain.entity.journey.JourneyMember
import com.gildongmu.dduru.domain.entity.journey.JourneyMemberRole
import com.gildongmu.dduru.domain.entity.journey.JourneyMemberRoleUpdate
import com.gildongmu.dduru.domain.entity.journey.JourneyUpdate
import com.gildongmu.dduru.domain.entity.journey.JourneyUpdateCommand
import com.gildongmu.dduru.domain.entity.journey.MyJourneyList
import com.gildongmu.dduru.domain.repository.JourneyRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class JourneyRepositoryImpl @Inject constructor(
    private val journeyService: JourneyService,
    private val json: Json
) : JourneyRepository {
    override suspend fun getJourneyDetail(journeyId: Long): Result<JourneyDetail> = request {
        journeyService.getJourneyDetail(journeyId).requireData().toDomain()
    }

    override suspend fun updateJourney(
        journeyId: Long,
        command: JourneyUpdateCommand
    ): Result<JourneyUpdate> = request {
        val response = journeyService.updateJourney(
            journeyId = journeyId,
            request = JourneyUpdateRequest(
                title = command.title,
                photoUrl = command.photoUrl,
                startDate = command.startDate,
                endDate = command.endDate
            )
        )
        response.requireData().toDomain()
    }

    override suspend fun getJourneyMembers(journeyId: Long): Result<List<JourneyMember>> = request {
        journeyService.getJourneyMembers(journeyId).requireData().map { it.toDomain() }
    }

    override suspend fun removeJourneyMember(journeyId: Long, memberUserId: Long): Result<Unit> = request {
        val response = journeyService.removeJourneyMember(journeyId, memberUserId)
        if (response.code() != 204) throw response.toJourneyApiException(json)
    }

    override suspend fun replaceJourneyMemberRoles(
        journeyId: Long,
        memberUserId: Long,
        roles: List<JourneyMemberRole>
    ): Result<JourneyMemberRoleUpdate> = request {
        val response = journeyService.replaceJourneyMemberRoles(
            journeyId = journeyId,
            memberUserId = memberUserId,
            request = JourneyMemberRoleUpdateRequest(
                roles = roles.map {
                    JourneyMemberRoleRequest(
                        roleType = it.roleType,
                        customRoleLabel = it.customRoleLabel
                    )
                }
            )
        )
        response.requireData().toDomain()
    }

    override suspend fun getMyJourneys(): Result<MyJourneyList> = request {
        journeyService.getMyJourneys().requireData().toDomain()
    }

    private suspend fun <T> request(block: suspend () -> T): Result<T> = try {
        Result.success(block())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: HttpException) {
        Result.failure(exception.toJourneyApiException(json))
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    private fun <T> ApiResponse<T>.requireData(): T {
        if (status != 0 && status !in 200..299) throw toJourneyApiException()
        return data ?: throw IllegalStateException("Response data is null")
    }
}

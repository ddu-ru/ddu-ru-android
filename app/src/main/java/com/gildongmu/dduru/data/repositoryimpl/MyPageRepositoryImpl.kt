package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.request.mypage.AvailableDateRequest
import com.gildongmu.dduru.data.remote.dto.request.mypage.DestinationPreferenceRequest
import com.gildongmu.dduru.data.remote.dto.request.mypage.TravelPreferenceUpdateRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyPageLikedPostResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyPagePostResponse
import com.gildongmu.dduru.data.remote.service.MyPageService
import com.gildongmu.dduru.domain.entity.mypage.AvailableDate
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreferenceType
import com.gildongmu.dduru.domain.entity.mypage.MyPagePost
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostDisplayStatus
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostFilter
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostPage
import com.gildongmu.dduru.domain.entity.mypage.MyProfile
import com.gildongmu.dduru.domain.entity.mypage.TravelPreference
import com.gildongmu.dduru.domain.repository.MyPageRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

private const val PAGE_SIZE = 10

class MyPageRepositoryImpl @Inject constructor(
    private val myPageService: MyPageService
) : MyPageRepository {

    override suspend fun getLikedPosts(cursor: Long?): Result<MyPagePostPage> = request {
        val data = myPageService.getLikedPosts(cursor, PAGE_SIZE).requireData()
        MyPagePostPage(
            posts = data.posts.map { it.toEntity() },
            nextCursor = data.nextCursor,
            hasNext = data.hasNext
        )
    }

    override suspend fun getMyPosts(cursor: Long?, filter: MyPagePostFilter): Result<MyPagePostPage> = request {
        val data = myPageService.getMyPosts(cursor, PAGE_SIZE, filter.name).requireData()
        MyPagePostPage(
            posts = data.posts.map { it.toEntity() },
            nextCursor = data.nextCursor,
            hasNext = data.hasNext,
            totalCount = data.totalPostCount
        )
    }

    override suspend fun getTravelPreference(): Result<TravelPreference> = request {
        val data = myPageService.getTravelPreferences().requireData()
        TravelPreference(
            destinations = data.destinationPreferences
                .sortedBy { it.preferenceRank }
                .mapNotNull {
                    toDestinationPreference(
                        it.type,
                        it.countryCode,
                        it.countryName,
                        it.destinationId,
                        it.city
                    )
                },
            availableDates = data.availableDates.map {
                AvailableDate(LocalDate.parse(it.startDate), LocalDate.parse(it.endDate))
            }
        )
    }

    override suspend fun updateTravelPreference(preference: TravelPreference): Result<Unit> = request {
        val response = myPageService.updateTravelPreferences(
            TravelPreferenceUpdateRequest(
                destinationPreferences = preference.destinations.map {
                    when (it.type) {
                        DestinationPreferenceType.COUNTRY -> DestinationPreferenceRequest(
                            type = it.type.name,
                            countryCode = it.countryCode
                        )
                        DestinationPreferenceType.CITY -> DestinationPreferenceRequest(
                            type = it.type.name,
                            destinationId = it.destinationId
                        )
                    }
                },
                availableDates = preference.availableDates.map {
                    AvailableDateRequest(it.startDate.toString(), it.endDate.toString())
                }
            )
        )
        if (!response.isSuccessful) throw HttpException(response)
    }

    override suspend fun searchPreferenceDestinations(keyword: String): Result<List<DestinationPreference>> =
        request {
            myPageService.searchPreferenceDestinations(keyword).requireData()
                .mapNotNull {
                    toDestinationPreference(
                        it.type,
                        it.countryCode,
                        it.countryName,
                        it.destinationId,
                        it.city
                    )
                }
        }

    override suspend fun getMyProfile(): Result<MyProfile> = request {
        val data = myPageService.getMyProfile().requireData()
        MyProfile(
            nickname = data.nickname,
            bio = data.bio,
            profileImageType = data.profileImage?.type,
            profileImageUrl = data.profileImage?.url,
            bgColorId = data.profileImage?.bgColorId?.toInt(),
            gender = data.gender,
            birthday = data.birthday,
            phoneNumber = data.phoneNumber
        )
    }

    private suspend fun <T> request(block: suspend () -> T): Result<T> = try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun <T> ApiResponse<T>.requireData(): T {
        if (status !in 200..299) throw IllegalStateException("Status $status")
        return data ?: throw IllegalStateException("Response data is null")
    }
}

private fun toDestinationPreference(
    type: String,
    countryCode: String?,
    countryName: String?,
    destinationId: Long?,
    city: String?
): DestinationPreference? {
    val preferenceType = DestinationPreferenceType.entries.firstOrNull { it.name == type } ?: return null
    return DestinationPreference(
        type = preferenceType,
        countryCode = countryCode.orEmpty(),
        countryName = countryName.orEmpty(),
        destinationId = destinationId,
        city = city
    )
}

private fun MyPageLikedPostResponse.toEntity() = MyPagePost(
    id = id,
    title = title,
    destination = destination,
    startDate = startDate,
    endDate = endDate
)

private fun MyPagePostResponse.toEntity() = MyPagePost(
    id = id,
    title = title,
    destination = destination,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
    displayStatus = MyPagePostDisplayStatus.entries.firstOrNull { it.name == displayStatus }
)

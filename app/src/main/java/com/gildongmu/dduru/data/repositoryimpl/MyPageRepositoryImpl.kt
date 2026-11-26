package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.request.mypage.AvailableDateRequest
import com.gildongmu.dduru.data.remote.dto.request.mypage.DestinationPreferenceRequest
import com.gildongmu.dduru.data.remote.dto.request.mypage.TravelPreferenceUpdateRequest
import com.gildongmu.dduru.data.remote.dto.response.mypage.BlockedUserResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.DestinationPreferenceResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyPageLikedPostResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.MyPagePostResponse
import com.gildongmu.dduru.data.remote.dto.response.mypage.TravelPreferenceResponse
import com.gildongmu.dduru.data.remote.service.MyPageService
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.domain.entity.mypage.AvailableDate
import com.gildongmu.dduru.domain.entity.mypage.BlockedUser
import com.gildongmu.dduru.domain.entity.mypage.BlockedUserPage
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreferenceType
import com.gildongmu.dduru.domain.entity.mypage.MyPageOverview
import com.gildongmu.dduru.domain.entity.mypage.MyPagePost
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostDisplayStatus
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostFilter
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostPage
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostSort
import com.gildongmu.dduru.domain.entity.mypage.MyProfile
import com.gildongmu.dduru.domain.entity.mypage.TravelPreference
import com.gildongmu.dduru.domain.entity.mypage.TravelStyleSummary
import com.gildongmu.dduru.domain.repository.MyPageRepository
import java.time.LocalDate
import javax.inject.Inject

private const val PAGE_SIZE = 10
private const val BLOCKED_USER_PAGE_SIZE = 20

class MyPageRepositoryImpl @Inject constructor(
    private val myPageService: MyPageService,
    private val postService: PostService
) : MyPageRepository {

    override suspend fun getOverview(): Result<MyPageOverview> = apiRequest {
        val data = myPageService.getOverview().requireData()
        val preference = data.travelPreferences.toEntity()
        MyPageOverview(
            nickname = data.nickname,
            bio = data.bio,
            profileImageType = data.profileImage?.type,
            profileImageUrl = data.profileImage?.url,
            bgColorId = data.profileImage?.bgColorId?.toInt(),
            destinations = preference.destinations,
            availableDates = preference.availableDates,
            travelStyleSummary = data.travelStyleSummary?.let {
                TravelStyleSummary(name = it.name, description = it.description, tags = it.tags)
            }
        )
    }

    override suspend fun getLikedPosts(cursor: Long?): Result<MyPagePostPage> = apiRequest {
        val data = myPageService.getLikedPosts(cursor, PAGE_SIZE).requireData()
        MyPagePostPage(
            posts = data.posts.map { it.toEntity() },
            nextCursor = data.nextCursor,
            hasNext = data.hasNext
        )
    }

    override suspend fun getMyPosts(
        cursor: Long?,
        filter: MyPagePostFilter,
        sort: MyPagePostSort
    ): Result<MyPagePostPage> = apiRequest {
        val data = myPageService.getMyPosts(cursor, PAGE_SIZE, filter.name, sort.name).requireData()
        MyPagePostPage(
            posts = data.posts.map { it.toEntity() },
            nextCursor = data.nextCursor,
            hasNext = data.hasNext,
            totalCount = data.totalPostCount,
            unusedSuperHostTicketCount = data.unusedSuperHostTicketCount
        )
    }

    override suspend fun togglePostLike(postId: Long): Result<Unit> = apiRequest {
        postService.toggleLike(postId).requireSuccess()
    }

    override suspend fun getTravelPreference(): Result<TravelPreference> = apiRequest {
        myPageService.getTravelPreferences().requireData().toEntity()
    }

    override suspend fun updateTravelPreference(preference: TravelPreference): Result<Unit> = apiRequest {
        myPageService.updateTravelPreferences(
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
        ).requireSuccess()
    }

    override suspend fun searchPreferenceDestinations(keyword: String): Result<List<DestinationPreference>> =
        apiRequest {
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

    override suspend fun getMyProfile(): Result<MyProfile> = apiRequest {
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

    override suspend fun getBlockedUsers(cursor: Long?): Result<BlockedUserPage> = apiRequest {
        val data = myPageService.getBlockedUsers(cursor, BLOCKED_USER_PAGE_SIZE).requireData()
        BlockedUserPage(
            users = data.users.map { it.toEntity() },
            nextCursor = data.nextCursor,
            hasNext = data.hasNext
        )
    }

    override suspend fun unblockUser(userId: Long): Result<Unit> = apiRequest {
        myPageService.unblockUser(userId).requireSuccess()
    }
}

private fun TravelPreferenceResponse.toEntity() = TravelPreference(
    destinations = destinationPreferences
        .sortedBy { it.preferenceRank }
        .mapNotNull { it.toEntity() },
    availableDates = availableDates.map {
        AvailableDate(LocalDate.parse(it.startDate), LocalDate.parse(it.endDate))
    }
)

private fun DestinationPreferenceResponse.toEntity(): DestinationPreference? =
    toDestinationPreference(type, countryCode, countryName, destinationId, city)?.copy(imageUrl = imageUrl)

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
    endDate = endDate,
    recruitCount = recruitCount,
    recruitCapacity = recruitCapacity,
    photoUrl = photoUrl,
    preferredGender = preferredGender,
    companionType = companionType,
    authorNickname = author?.nickname.orEmpty(),
    isAuthorSuperHost = author?.isSuperHost ?: false
)

private fun MyPagePostResponse.toEntity() = MyPagePost(
    id = id,
    title = title,
    destination = destination,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
    displayStatus = MyPagePostDisplayStatus.entries.firstOrNull { it.name == displayStatus },
    recruitCount = recruitCount,
    recruitCapacity = recruitCapacity,
    photoUrl = photoUrl,
    viewCount = viewCount,
    likeCount = likeCount
)

private fun BlockedUserResponse.toEntity() = BlockedUser(
    userId = userId,
    nickname = nickname,
    bio = bio,
    profileImageUrl = profileImage?.url
)

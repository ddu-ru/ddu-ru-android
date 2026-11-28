package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.mypage.BlockedUserPage
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.MyPageOverview
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostFilter
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostPage
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostSort
import com.gildongmu.dduru.domain.entity.mypage.MyProfile
import com.gildongmu.dduru.domain.entity.mypage.TravelPreference

interface MyPageRepository {
    suspend fun getOverview(): Result<MyPageOverview>
    suspend fun getLikedPosts(cursor: Long?): Result<MyPagePostPage>
    suspend fun getMyPosts(
        cursor: Long?,
        filter: MyPagePostFilter,
        sort: MyPagePostSort = MyPagePostSort.LATEST
    ): Result<MyPagePostPage>
    suspend fun togglePostLike(postId: Long): Result<Unit>
    suspend fun getTravelPreference(): Result<TravelPreference>
    suspend fun updateTravelPreference(preference: TravelPreference): Result<Unit>
    suspend fun searchPreferenceDestinations(keyword: String): Result<List<DestinationPreference>>
    suspend fun getMyProfile(): Result<MyProfile>
    suspend fun getBlockedUsers(cursor: Long?): Result<BlockedUserPage>
    suspend fun unblockUser(userId: Long): Result<Unit>
}

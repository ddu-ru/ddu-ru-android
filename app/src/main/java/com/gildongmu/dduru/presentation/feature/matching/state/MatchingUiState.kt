package com.gildongmu.dduru.presentation.feature.matching.state

import androidx.compose.runtime.Immutable
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingDestinationOption
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingFilterOptions
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class MatchingUiState(
    val searchQuery: String = "",
    val selectedSort: String = "최신순",
    val filters: MatchingFilterOptions = MatchingFilterOptions(),
    val destinationOptions: List<MatchingDestinationOption> = emptyList(),
    val nextCursor: Long? = null,
    val nextCursorValue: Int? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
    val posts: ImmutableList<MatchingPostCardUiModel> = persistentListOf()
) {
    companion object {
        val fake = MatchingUiState(
            posts = persistentListOf(
                MatchingPostCardUiModel(
                    id = 1L,
                    title = "제주도 3박 4일 여행",
                    authorName = "알간모르간",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    gender = MatchingGender.MALE,
                    companionType = MatchingCompanionType.FULL,
                    thumbnailImageRes = R.drawable.fukuoka
                ),
                MatchingPostCardUiModel(
                    id = 2L,
                    title = "아자스!!! 저랑 같이 일본가요",
                    authorName = "일본조아",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    gender = MatchingGender.MALE,
                    companionType = MatchingCompanionType.PARTIAL,
                    isSuperHost = true,
                    thumbnailImageRes = R.drawable.haha
                ),
                MatchingPostCardUiModel(
                    id = 3L,
                    title = "야호!!! 이거보고 미국갔습니다",
                    authorName = "도원드림프",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    gender = MatchingGender.ANY,
                    companionType = MatchingCompanionType.FULL,
                    thumbnailImageRes = R.drawable.fukuoka
                ),
                MatchingPostCardUiModel(
                    id = 4L,
                    title = "안녕하세요 이거 뭔가요",
                    authorName = "생활트만",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    gender = MatchingGender.FEMALE,
                    companionType = MatchingCompanionType.MEAL,
                    thumbnailImageRes = R.drawable.haha
                ),
                MatchingPostCardUiModel(
                    id = 5L,
                    title = "Title",
                    authorName = "username",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    gender = MatchingGender.ANY,
                    companionType = MatchingCompanionType.FULL,
                    thumbnailImageRes = R.drawable.fukuoka
                ),
                MatchingPostCardUiModel(
                    id = 6L,
                    title = "후쿠오카 맛집 같이 가요",
                    authorName = "라멘러버",
                    location = "후쿠오카 텐진",
                    dateRange = "04.02 - 04.05",
                    currentMemberCount = 2,
                    maxMemberCount = 4,
                    gender = MatchingGender.ANY,
                    companionType = MatchingCompanionType.MEAL,
                    thumbnailImageRes = R.drawable.haha
                ),
                MatchingPostCardUiModel(
                    id = 7L,
                    title = "도쿄 벚꽃 여행 동행 구해요",
                    authorName = "봄여행자",
                    location = "도쿄 우에노",
                    dateRange = "04.10 - 04.14",
                    currentMemberCount = 1,
                    maxMemberCount = 3,
                    gender = MatchingGender.FEMALE,
                    companionType = MatchingCompanionType.PARTIAL,
                    isSuperHost = true,
                    thumbnailImageRes = R.drawable.fukuoka
                ),
                MatchingPostCardUiModel(
                    id = 8L,
                    title = "부산 주말 바다 보러 가실 분",
                    authorName = "해운대러",
                    location = "부산 해운대",
                    dateRange = "05.03 - 05.04",
                    currentMemberCount = 3,
                    maxMemberCount = 5,
                    gender = MatchingGender.MALE,
                    companionType = MatchingCompanionType.FULL,
                    thumbnailImageRes = R.drawable.haha
                ),
                MatchingPostCardUiModel(
                    id = 9L,
                    title = "대만 야시장 투어 같이해요",
                    authorName = "야시장조아",
                    location = "타이베이",
                    dateRange = "05.15 - 05.18",
                    currentMemberCount = 2,
                    maxMemberCount = 4,
                    gender = MatchingGender.ANY,
                    companionType = MatchingCompanionType.MEAL,
                    thumbnailImageRes = R.drawable.fukuoka
                ),
                MatchingPostCardUiModel(
                    id = 10L,
                    title = "방콕 카페 투어 동행",
                    authorName = "카페인충전",
                    location = "방콕 시암",
                    dateRange = "06.01 - 06.06",
                    currentMemberCount = 1,
                    maxMemberCount = 4,
                    gender = MatchingGender.FEMALE,
                    companionType = MatchingCompanionType.PARTIAL,
                    thumbnailImageRes = R.drawable.haha
                )
            )
        )
    }
}

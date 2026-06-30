package com.gildongmu.dduru.presentation.feature.matching.post.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.post.compoenet.MatchingDetailParticipantComponent
import com.gildongmu.dduru.presentation.feature.matching.post.compoenet.MatchingDetailParticipantUiModel
import com.gildongmu.dduru.presentation.feature.matching.post.component.CommonBoxComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailBottomActionComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailInfoComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingHostInfoComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingHostProfileImage
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingPostParticipationStatus
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingPostRecruitStatus
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingPostStatus
import com.gildongmu.dduru.presentation.feature.matching.post.component.PostStatusDropdown
import dev.chrisbanes.haze.HazeState

data class MatchingDetailUiState(
    val id: Long = 101,
    val title: String = "제주도 2박 3일 동행 구해요",
    val content: String = "제주도 서쪽 위주로 천천히 여행하실 분을 찾습니다. 카페와 맛집도 들르고, 사진도 여유롭게 찍는 일정으로 생각하고 있어요. 일정이 비슷하신 분이면 편하게 같이 다녀요.",
    val status: MatchingPostRecruitStatus = MatchingPostRecruitStatus.OPEN,
    val isFull: Boolean = false,
    val daysUntilRecruitDeadline: Int = 3,
    val daysUntilTravelStart: Int = 7,
    val startDate: String = "2026-07-10",
    val endDate: String = "2026-07-12",
    val destination: String = "제주",
    val recruitCapacity: Int = 4,
    val recruitCount: Int = 2,
    val recruitDeadline: String = "2026-07-09",
    val preferredGender: String = "U",
    val isAgeAny: Boolean = false,
    val minAge: Int = 20,
    val maxAge: Int = 29,
    val photoUrl: String? = "https://cdn.example.com/posts/cover.jpg",
    val tags: List<String> = listOf("맛집", "힐링", "드라이브", "사진"),
    val viewCount: Int = 128,
    val likeCount: Int = 12,
    val createdAt: String = "2026-06-29T12:34:56",
    val author: MatchingDetailAuthorUiState = MatchingDetailAuthorUiState(),
    val isOwner: Boolean = false,
    val canEditPost: Boolean = false,
    val hasLiked: Boolean = true,
    val participants: List<MatchingDetailParticipantUiState> = listOf(
        MatchingDetailParticipantUiState(isHost = true),
        MatchingDetailParticipantUiState(
            userId = 44,
            nickname = "제주러버",
            gender = "M",
            ageGroup = 30
        )
    ),
    val myParticipationStatus: MatchingPostParticipationStatus = MatchingPostParticipationStatus.PENDING,
    val tripDurationText: String = "2박 3일",
    val recruitDeadlineDDay: String = "D-3",
    val companionType: String = "FULL"
) {
    val preferredGenderText: String
        get() = when (preferredGender) {
            "M" -> "남성"
            "F" -> "여성"
            "U" -> "무관"
            else -> "무관"
        }

    val companionTypeText: String
        get() = when (companionType) {
            "FULL" -> "전체동행"
            "PARTIAL" -> "부분동행"
            else -> "동행"
        }

    val ageConditionText: String
        get() = if (isAgeAny) {
            "연령 무관 · $preferredGenderText"
        } else {
            "$minAge-$maxAge 세 · $preferredGenderText"
        }

    val remainingSlots: Int
        get() = (recruitCapacity - recruitCount).coerceAtLeast(0)

    val travelPeriodText: String
        get() = "${startDate.toDotDate()} - ${endDate.toDotDate()} ($tripDurationText)"

    val createdDateText: String
        get() = createdAt.substringBefore("T").toDotDate()
}

data class MatchingDetailAuthorUiState(
    val userId: Long = 33,
    val nickname: String = "여행메이트",
    val profileImage: MatchingDetailProfileImageUiState = MatchingDetailProfileImageUiState(),
    val gender: String = "F",
    val ageGroup: Int = 20,
    val isSuperHost: Boolean = false
) {
    val genderText: String
        get() = when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            else -> "무관"
        }

    val ageText: String
        get() = "${ageGroup}대"
}

data class MatchingDetailParticipantUiState(
    val userId: Long = 33,
    val nickname: String = "여행메이트",
    val profileImage: MatchingDetailProfileImageUiState = MatchingDetailProfileImageUiState(),
    val isHost: Boolean = false,
    val gender: String = "F",
    val ageGroup: Int = 20
) {
    val genderText: String
        get() = when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            else -> "성별 무관"
        }

    val ageText: String
        get() = "${ageGroup}대"
}

data class MatchingDetailProfileImageUiState(
    val type: String = "AVATAR",
    val url: String? = "https://cdn.example.com/profiles/avatar.png",
    val bgColorId: Int = 3
)

private fun String.toDotDate(): String = replace("-", ".")

@Composable
fun MatchingDetailScreen(
    uiState: MatchingDetailUiState,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit,
    onOverflowClick: () -> Unit,
    onShareClick: () -> Unit,
    onProfileClick: () -> Unit,
    onContactClick: () -> Unit,
    onBottomActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hazeState = remember { HazeState() }
    var selectedStatus by remember { mutableStateOf(uiState.status.toDropdownStatus()) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DduruTheme.colors.gray0,
        topBar = {
            //채워야함
        },
        bottomBar = {
            MatchingDetailBottomActionComponent(
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                recruitCapacity = uiState.recruitCapacity,
                recruitCount = uiState.recruitCount,
                tripDurationText = uiState.tripDurationText,
                status = uiState.status,
                isFull = uiState.isFull,
                isOwner = uiState.isOwner,
                myParticipationStatus = uiState.myParticipationStatus,
                onButtonClick = onBottomActionClick,
                hazeState = hazeState,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                MatchingDetailHeaderImage(imageUrl = uiState.photoUrl)
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    if (uiState.isOwner) {
                        PostStatusDropdown(
                            selectedStatus = selectedStatus,
                            onStatusSelected = { selectedStatus = it }
                        )
                    }

                    MatchingDetailSummarySection(uiState = uiState)

                    CommonBoxComponent(
                        recruitmentType = uiState.companionTypeText,
                        recruitmentCount = uiState.recruitCount.toString(),
                        recruitCapacity = uiState.recruitCapacity.toString(),
                        minAge = uiState.minAge,
                        maxAge = uiState.maxAge,
                        preferredGender = uiState.preferredGenderText
                    )

                    MatchingDetailDescriptionSection(uiState = uiState)

                    MatchingDetailMetaRow(uiState = uiState)

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = DduruTheme.colors.gray200
                    )

                    MatchingDetailInfoGrid(uiState = uiState)

                    MatchingDetailTagSection(tags = uiState.tags)

                    MatchingHostInfoComponent(
                        username = uiState.author.nickname,
                        age = uiState.author.ageText,
                        gender = uiState.author.genderText,
                        profileImage = uiState.author.profileImage.toHostProfileImage(),
                        onProfileClick = onProfileClick,
                        onContactClick = onContactClick
                    )

                    MatchingDetailParticipantComponent(
                        participants = uiState.participants.map { participant ->
                            MatchingDetailParticipantUiModel(
                                userId = participant.userId,
                                nickname = participant.nickname,
                                ageText = participant.ageText,
                                genderText = participant.genderText,
                                profileImage = participant.profileImage.url,
                                isHost = participant.isHost
                            )
                        },
                        recruitCapacity = uiState.recruitCapacity
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchingDetailSummarySection(
    uiState: MatchingDetailUiState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = uiState.title,
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.gray900
        )

        Text(
            text = uiState.destination,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )

        Text(
            text = uiState.travelPeriodText,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )
    }
}

@Composable
private fun MatchingDetailDescriptionSection(
    uiState: MatchingDetailUiState
) {
    Column(
    ) {
        SectionTitle(text = "여행 소개")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = uiState.content,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )
    }
}

@Composable
private fun MatchingDetailMetaRow(
    uiState: MatchingDetailUiState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "작성일자 ${uiState.createdDateText}",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray400
        )

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(4.dp)
                .background(DduruTheme.colors.gray400, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "조회수 ${uiState.viewCount}",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray400
        )
    }
}

@Composable
private fun MatchingDetailInfoGrid(
    uiState: MatchingDetailUiState
) {
    Column(
    ) {
        SectionTitle(text = "핵심 정보")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchingDetailInfoComponent(
                iconRes = R.drawable.ic_calendar_filled,
                title = "여행 일정",
                content = uiState.travelPeriodText,
                modifier = Modifier.weight(1f)
            )

            MatchingDetailInfoComponent(
                iconRes = R.drawable.ic_group_filled,
                title = "모집인원",
                content = uiState.recruitCount.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchingDetailInfoComponent(
                iconRes = R.drawable.ic_calendar_filled,
                title = "모집 조건",
                content = uiState.ageConditionText,
                modifier = Modifier.weight(1f)
            )

            MatchingDetailInfoComponent(
                iconRes = R.drawable.ic_calendar_filled,
                title = "모집 유형",
                content = uiState.companionTypeText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MatchingDetailTagSection(
    tags: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle(text = "해시태그")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.take(4).forEach { tag ->
                MatchingDetailTagChip(tag = tag)
            }
        }
    }
}

@Composable
private fun SectionTitle(
    text: String
) {
    Text(
        text = text,
        style = DduruTheme.typography.Title3,
        color = DduruTheme.colors.gray600
    )
}

@Composable
private fun MatchingDetailBadge(
    text: String
) {
    Box(
        modifier = Modifier
            .background(
                color = DduruTheme.colors.gray100,
                shape = CircleShape
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray700
        )
    }
}

@Composable
private fun MatchingDetailTagChip(
    tag: String
) {
    Box(
        modifier = Modifier
            .background(
                color = DduruTheme.colors.secondary100,
                shape = CircleShape
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "#$tag",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun MatchingDetailProfileImageUiState.toHostProfileImage(): MatchingHostProfileImage? {
    return url?.let { MatchingHostProfileImage.Url(it) }
}

private fun MatchingPostRecruitStatus.toDropdownStatus(): MatchingPostStatus {
    return when (this) {
        MatchingPostRecruitStatus.OPEN -> MatchingPostStatus.RECRUITING
        MatchingPostRecruitStatus.CLOSED -> MatchingPostStatus.CLOSED
        MatchingPostRecruitStatus.HIDDEN -> MatchingPostStatus.HIDDEN
    }
}

@Composable
fun MatchingDetailHeaderImage(
    imageUrl: String?
) {
    AsyncImage(
        model = imageUrl ?: R.drawable.fukuoka,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailScreenPreview() {
    DduruTheme {
        Surface(color = DduruTheme.colors.gray0) {
            MatchingDetailScreen(
                uiState = MatchingDetailUiState(),
                onBackClick = {},
                onLikeClick = {},
                onOverflowClick = {},
                onShareClick = {},
                onProfileClick = {},
                onContactClick = {},
                onBottomActionClick = {}
            )
        }
    }
}

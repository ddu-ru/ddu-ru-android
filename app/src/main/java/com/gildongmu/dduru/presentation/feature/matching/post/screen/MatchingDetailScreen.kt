package com.gildongmu.dduru.presentation.feature.matching.post.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.component.HomeHashtag
import com.gildongmu.dduru.presentation.feature.matching.post.component.CommonBoxComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailBackButton
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailBottomActionComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailInfoComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailParticipantComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailParticipantUiModel
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingDetailTopActionButton
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingHostInfoComponent
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingHostProfileImage
import com.gildongmu.dduru.presentation.feature.matching.post.component.PostStatusDropdown
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailProfileImageUiState
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailUiState
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostRecruitStatus
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostStatus
import com.gildongmu.dduru.presentation.feature.matching.post.viewmodel.MatchingDetailViewModel
import dev.chrisbanes.haze.HazeState
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MatchingDetailRoute(
    postId: Long,
    onBackClick: () -> Unit,
    onOverflowClick: () -> Unit,
    onShareClick: () -> Unit,
    onProfileClick: () -> Unit,
    onContactClick: () -> Unit,
    onBottomActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchingDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(postId) {
        viewModel.loadMatchingDetail(postId)
    }

    when (val state = uiState) {
        UiState.Empty -> Unit
        UiState.Loading -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "동행 글을 불러오지 못했어요.")
        is UiState.Success -> {
            MatchingDetailScreen(
                uiState = state.data,
                onBackClick = onBackClick,
                onLikeClick = viewModel::onLikeClick,
                onOverflowClick = onOverflowClick,
                onShareClick = onShareClick,
                onProfileClick = onProfileClick,
                onContactClick = onContactClick,
                onBottomActionClick = onBottomActionClick,
                modifier = modifier
            )
        }
    }
}

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MatchingDetailBackButton(onClick = onBackClick)

                MatchingDetailTopActionButton(
                    likeCount = uiState.likeCount,
                    hasLiked = uiState.hasLiked,
                    isOwner = uiState.isOwner,
                    onLikeClick = onLikeClick,
                    onMoreClick = onOverflowClick,
                    onShareClick = onShareClick
                )
            }
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
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-16).dp),
                    color = DduruTheme.colors.gray0,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        var isExpanded by remember { mutableStateOf(false) }

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

                        if (!isExpanded) {
                            Text(
                                text = "더보기",
                                modifier = Modifier.clickable { isExpanded = true },
                                style = DduruTheme.typography.Body2,
                                color = DduruTheme.colors.primary
                            )
                        }

                        MatchingDetailMetaRow(uiState = uiState)

                        if (isExpanded) {
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
                                }.toPersistentList(),
                                recruitCount = uiState.recruitCount,
                                recruitCapacity = uiState.recruitCapacity,
                                remainingSlots = uiState.remainingSlots
                            )
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
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
    Column() {
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
    Column() {
        SectionTitle(text = "핵심 정보")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
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
                content = "${uiState.recruitCount} / ${uiState.recruitCapacity}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
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
                HomeHashtag(
                    text = tag,
                    textColor = DduruTheme.colors.gray700,
                    textStyle = DduruTheme.typography.Caption2,
                    backgroundColor = DduruTheme.colors.gray100,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                )
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(375f / 300f)
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.fukuoka,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = DduruTheme.colors.gray0.copy(alpha = 0.89f)
                    )
                    .aspectRatio(375f / 52f)
                    .blur(24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(375f / 48f)
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to DduruTheme.colors.gray0.copy(alpha = 0.88f),
                                0.25f to DduruTheme.colors.gray0.copy(alpha = 0.7f),
                                0.75f to DduruTheme.colors.gray0.copy(alpha = 0.18f),
                                1f to Color.Transparent
                            )
                        )
                    )
            )
        }
    }
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

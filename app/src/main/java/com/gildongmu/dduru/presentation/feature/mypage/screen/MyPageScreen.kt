package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.SurveyResult
import com.gildongmu.dduru.domain.entity.mypage.AvailableDate
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreference
import com.gildongmu.dduru.domain.entity.mypage.DestinationPreferenceType
import com.gildongmu.dduru.presentation.feature.mypage.component.ActionMenuItem
import com.gildongmu.dduru.presentation.feature.mypage.component.ActionMenuSection
import com.gildongmu.dduru.presentation.feature.mypage.component.MyPageHeader
import com.gildongmu.dduru.presentation.feature.mypage.component.SettingsMenuItem
import com.gildongmu.dduru.presentation.feature.mypage.component.SettingsMenuSection
import com.gildongmu.dduru.presentation.feature.mypage.component.StatusTagXs
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelDatesSection
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelDestination
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelDestinationsSection
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleSection
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleTag
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleTestSection
import com.gildongmu.dduru.presentation.feature.mypage.component.UserInfoRow
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.MyPageUiState
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.MyPageViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val MY_PAGE_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
private const val EMPTY_BIO_TEXT = "한줄소개를 입력해주세요"

@Composable
fun MyPageRoute(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAddDestinationClick: () -> Unit = onEditProfileClick,
    onWishlistClick: () -> Unit = {},
    onMyPostsClick: () -> Unit = {},
    onBlockedListClick: () -> Unit = {},
    onTravelStyleClick: () -> Unit = {},
    onStartTravelStyleTestClick: () -> Unit = {},
    onCustomerCenterClick: () -> Unit = {},
    onAccountClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onCompanyInfoClick: () -> Unit = {},
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.loadMyPage()
    }

    when (val state = uiState) {
        UiState.Loading, UiState.Empty -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "마이페이지 정보를 불러오지 못했어요.")
        is UiState.Success -> MyPageScreen(
            uiState = state.data,
            onNotificationClick = onNotificationClick,
            onMenuClick = onMenuClick,
            onEditProfileClick = onEditProfileClick,
            onAddDestinationClick = onAddDestinationClick,
            onWishlistClick = onWishlistClick,
            onMyPostsClick = onMyPostsClick,
            onBlockedListClick = onBlockedListClick,
            onTravelStyleClick = onTravelStyleClick,
            onStartTravelStyleTestClick = onStartTravelStyleTestClick,
            onCustomerCenterClick = onCustomerCenterClick,
            onAccountClick = onAccountClick,
            onTermsClick = onTermsClick,
            onCompanyInfoClick = onCompanyInfoClick,
            modifier = modifier
        )
    }
}

@Composable
private fun MyPageScreen(
    uiState: MyPageUiState,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onAddDestinationClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onMyPostsClick: () -> Unit = {},
    onBlockedListClick: () -> Unit = {},
    onTravelStyleClick: () -> Unit = {},
    onStartTravelStyleTestClick: () -> Unit = {},
    onCustomerCenterClick: () -> Unit = {},
    onAccountClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onCompanyInfoClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { MyPageHeader(onNotificationClick, onMenuClick) }
        item {
            MyProfileCard(
                uiState = uiState,
                onEditProfileClick = onEditProfileClick,
                onAddDestinationClick = onAddDestinationClick,
                onTravelStyleClick = onTravelStyleClick,
                onStartTravelStyleTestClick = onStartTravelStyleTestClick
            )
        }
        item {
            ActionMenuSection(
                items = listOf(
                    ActionMenuItem(R.drawable.ic_heart_rounded, "찜한 여행", onWishlistClick),
                    ActionMenuItem(R.drawable.ic_pencil, "작성글 관리", onMyPostsClick),
                    ActionMenuItem(R.drawable.ic_prohibit, "차단 목록", onBlockedListClick)
                )
            )
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                SettingsMenuSection(
                    items = listOf(SettingsMenuItem("고객센터", onCustomerCenterClick)),
                    label = "고객 지원"
                )
                SettingsMenuSection(
                    items = listOf(SettingsMenuItem("계정 관리", onAccountClick)),
                    label = "계정"
                )
                SettingsMenuSection(
                    items = listOf(
                        SettingsMenuItem("이용약관", onTermsClick),
                        SettingsMenuItem("회사정보", onCompanyInfoClick)
                    ),
                    label = "서비스 정보"
                )
            }
        }
    }
}

@Composable
private fun MyProfileCard(
    uiState: MyPageUiState,
    onEditProfileClick: () -> Unit,
    onAddDestinationClick: () -> Unit,
    onTravelStyleClick: () -> Unit,
    onStartTravelStyleTestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .padding(16.dp)
    ) {
        UserInfoRow(
            nickname = uiState.nickname,
            greeting = uiState.bio?.takeIf { it.isNotBlank() } ?: EMPTY_BIO_TEXT,
            onEditClick = onEditProfileClick,
            profileImage = uiState.profileImageUrl
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.destinations.isEmpty()) {
            DestinationAddSection(onAddClick = onAddDestinationClick)
        } else {
            TravelDestinationsSection(
                destinations = uiState.destinations.map {
                    TravelDestination(
                        name = it.city ?: it.countryName,
                        imageUrl = R.drawable.ic_home_location
                    )
                }
            )
        }

        if (uiState.dateRanges.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            TravelDatesSection(
                dateRanges = uiState.dateRanges.map {
                    "${it.startDate.format(MY_PAGE_DATE_FORMATTER)} ~ ${it.endDate.format(MY_PAGE_DATE_FORMATTER)}"
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val travelStyle = uiState.travelStyle
        if (travelStyle != null) {
            TravelStyleSection(
                title = travelStyle.characterName,
                description = travelStyle.descriptionLine1,
                tags = travelStyle.toTravelStyleTags(),
                onExpandClick = onTravelStyleClick
            )
        } else {
            TravelStyleTestSection(onStartTestClick = onStartTravelStyleTestClick)
        }
    }
}

@Composable
private fun DestinationAddSection(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "선호 여행지",
            color = DduruTheme.colors.gray400,
            style = DduruTheme.typography.Caption2
        )

        StatusTagXs(
            text = "여행지 추가",
            textColor = DduruTheme.colors.gray600,
            textStyle = DduruTheme.typography.Caption1,
            contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            contentSpacing = 8.dp,
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_gray),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray600,
                    modifier = Modifier.size(12.dp)
                )
            },
            onClick = onAddClick
        )
    }
}

private fun SurveyResult.toTravelStyleTags(): List<TravelStyleTag> =
    tags.map { TravelStyleTag(emoji = "", label = it) }

private val PreviewCompleteUiState = MyPageUiState(
    nickname = "차한잔마시고해",
    bio = "야호~반갑습니다~~",
    destinations = listOf(
        DestinationPreference(DestinationPreferenceType.CITY, "JP", "일본", 1L, "도쿄"),
        DestinationPreference(DestinationPreferenceType.CITY, "FR", "프랑스", 2L, "파리"),
        DestinationPreference(DestinationPreferenceType.CITY, "KR", "대한민국", 3L, "제주도")
    ),
    dateRanges = listOf(
        AvailableDate(LocalDate.of(2026, 3, 15), LocalDate.of(2026, 3, 20)),
        AvailableDate(LocalDate.of(2026, 5, 2), LocalDate.of(2026, 5, 5))
    ),
    travelStyle = SurveyResult(
        rhythmScore = 0.0,
        energyScore = 0.0,
        consumptionScore = 0.0,
        decisionScore = 0.0,
        avatarCode = 0,
        avatarType = "",
        recordStyleType = "",
        avatarLabel = "",
        characterName = "스윗",
        speechBubbleText = "",
        tags = listOf("맛집러", "기록파", "야경 좋아함"),
        descriptionLine1 = "도시를 걸으며 새로운 장소를 발견해요!",
        descriptionLine2 = "",
        descriptionLine3 = "",
        imageUrl = null
    )
)

@Preview(showBackground = true, heightDp = 1345, name = "마이페이지 - 프로필 완성")
@Composable
private fun MyPageScreenCompletePreview() {
    DduruTheme { MyPageScreen(uiState = PreviewCompleteUiState) }
}

@Preview(showBackground = true, heightDp = 1345, name = "마이페이지 - 프로필 미완성")
@Composable
private fun MyPageScreenEmptyPreview() {
    DduruTheme { MyPageScreen(uiState = MyPageUiState(nickname = "username")) }
}

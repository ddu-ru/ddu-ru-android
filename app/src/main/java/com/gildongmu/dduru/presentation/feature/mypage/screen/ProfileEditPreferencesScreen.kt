package com.gildongmu.dduru.presentation.feature.mypage.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelDatePreferenceSection
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelDestinationPreferenceSection
import com.gildongmu.dduru.presentation.feature.mypage.model.MAX_PREFERRED_DATE_COUNT
import com.gildongmu.dduru.presentation.feature.mypage.model.PreferredDateRange
import com.gildongmu.dduru.presentation.feature.mypage.model.PreferredDestination
import com.gildongmu.dduru.presentation.feature.mypage.model.toPreferredDestination
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.ProfileEditPreferencesSideEffect
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.ProfileEditPreferencesViewModel
import java.time.LocalDate

private val PreferencesTabs = listOf("내 정보", "가고싶은 여행", "나의 여행 스타일")
private const val PREFERENCES_TAB_INDEX = 1

@Composable
fun ProfileEditPreferencesScreen(
    destinations: List<PreferredDestination>,
    dateRanges: List<PreferredDateRange>,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onTabClick: (Int) -> Unit,
    onDestinationAddClick: () -> Unit,
    onDateAddClick: () -> Unit,
    onDateRemoveClick: (Int) -> Unit,
    onDateSelected: (index: Int, date: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    isSaveEnabled: Boolean = true,
    initialEditingDateIndex: Int? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        PreferencesHeader(
            isSaveEnabled = isSaveEnabled,
            onBackClick = onBackClick,
            onConfirmClick = onConfirmClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            PreferencesTabRow(
                selectedIndex = PREFERENCES_TAB_INDEX,
                onTabClick = onTabClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            TravelDestinationPreferenceSection(
                destinations = destinations,
                onAddClick = onDestinationAddClick,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TravelDatePreferenceSection(
                dateRanges = dateRanges,
                onAddClick = onDateAddClick,
                onRemoveClick = onDateRemoveClick,
                onDateSelected = onDateSelected,
                modifier = Modifier.padding(vertical = 16.dp),
                initialEditingIndex = initialEditingDateIndex
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ProfileEditPreferencesRoute(
    onBackClick: () -> Unit,
    onNavigateToDestinationSelect: () -> Unit,
    onTabClick: (Int) -> Unit = {},
    viewModel: ProfileEditPreferencesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                ProfileEditPreferencesSideEffect.NavigateBack -> onBackClick()
                ProfileEditPreferencesSideEffect.NavigateToDestinationSelect -> onNavigateToDestinationSelect()
                is ProfileEditPreferencesSideEffect.ShowMessage ->
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    when (val state = uiState) {
        UiState.Loading, UiState.Empty -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "여행 선호 정보를 불러오지 못했어요.")
        is UiState.Success -> ProfileEditPreferencesScreen(
            destinations = state.data.destinations.map { it.toPreferredDestination() },
            dateRanges = state.data.dateRanges,
            onBackClick = viewModel::onBackClick,
            onConfirmClick = viewModel::onConfirmClick,
            onTabClick = onTabClick,
            onDestinationAddClick = viewModel::onDestinationAddClick,
            onDateAddClick = viewModel::onDateAddClick,
            onDateRemoveClick = viewModel::onDateRemoveClick,
            onDateSelected = viewModel::onDateSelected,
            isSaveEnabled = state.data.isSaveEnabled
        )
    }
}

@Composable
private fun PreferencesHeader(
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .height(48.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .clip(CircleShape)
                .background(DduruTheme.colors.gray0.copy(alpha = 0.5f))
                .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                .noRippleClickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = "뒤로가기",
                tint = DduruTheme.colors.gray900,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "프로필 수정",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp)
                .clip(CircleShape)
                .background(if (isSaveEnabled) DduruTheme.colors.primary else DduruTheme.colors.gray200)
                .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                .then(if (isSaveEnabled) Modifier.noRippleClickable(onClick = onConfirmClick) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "저장",
                tint = if (isSaveEnabled) DduruTheme.colors.gray0 else DduruTheme.colors.gray400,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun PreferencesTabRow(
    selectedIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PreferencesTabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isSelected) DduruTheme.colors.black else DduruTheme.colors.gray50)
                    .then(
                        if (isSelected) {
                            Modifier
                        } else {
                            Modifier.border(width = 1.dp, color = DduruTheme.colors.gray100, shape = CircleShape)
                        }
                    )
                    .noRippleClickable { onTabClick(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = DduruTheme.typography.Body2,
                    color = if (isSelected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
                )
            }
        }
    }
}

private val PreviewDestinations = listOf(
    PreferredDestination(name = "제주도", region = "국내"),
    PreferredDestination(name = "오사카", region = "일본"),
    PreferredDestination(name = "파리", region = "프랑스")
)

private val PreviewFilledDates = listOf(
    PreferredDateRange(LocalDate.of(2025, 10, 6), LocalDate.of(2025, 10, 6)),
    PreferredDateRange(LocalDate.of(2025, 11, 12), LocalDate.of(2025, 11, 14))
)

@Composable
private fun PreferencesPreviewHost(
    initialDestinations: List<PreferredDestination> = emptyList(),
    initialDateRanges: List<PreferredDateRange> = emptyList(),
    initialEditingDateIndex: Int? = null
) {
    var destinations by remember { mutableStateOf(initialDestinations) }
    var dateRanges by remember { mutableStateOf(initialDateRanges) }

    ProfileEditPreferencesScreen(
        destinations = destinations,
        dateRanges = dateRanges,
        onBackClick = {},
        onConfirmClick = {},
        onTabClick = {},
        onDestinationAddClick = { destinations = PreviewDestinations },
        onDateAddClick = {
            if (dateRanges.size < MAX_PREFERRED_DATE_COUNT) dateRanges = dateRanges + PreferredDateRange()
        },
        onDateRemoveClick = { index -> dateRanges = dateRanges.filterIndexed { i, _ -> i != index } },
        onDateSelected = { index, date ->
            dateRanges = dateRanges.mapIndexed { i, range ->
                if (i == index) range.withSelectedDate(date) else range
            }
        },
        initialEditingDateIndex = initialEditingDateIndex
    )
}

@Preview(showBackground = true, heightDp = 812, name = "비어 있음")
@Composable
private fun Preview35751() {
    DduruTheme { PreferencesPreviewHost() }
}

@Preview(showBackground = true, heightDp = 812, name = "날짜 행 추가")
@Composable
private fun Preview35851() {
    DduruTheme { PreferencesPreviewHost(initialDateRanges = listOf(PreferredDateRange())) }
}

@Preview(showBackground = true, heightDp = 1100, name = "캘린더 열림")
@Composable
private fun Preview35897() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDateRanges = listOf(PreferredDateRange()),
            initialEditingDateIndex = 0
        )
    }
}

@Preview(showBackground = true, heightDp = 1100, name = "시작일만 선택")
@Composable
private fun Preview35945() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDateRanges = listOf(PreferredDateRange(startDate = LocalDate.of(2026, 7, 17))),
            initialEditingDateIndex = 0
        )
    }
}

@Preview(showBackground = true, heightDp = 1100, name = "범위 선택 완료")
@Composable
private fun Preview36352() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDateRanges = listOf(
                PreferredDateRange(LocalDate.of(2026, 7, 17), LocalDate.of(2026, 7, 22))
            ),
            initialEditingDateIndex = 0
        )
    }
}

@Preview(showBackground = true, heightDp = 812, name = "여행지 없음 + 날짜 1개")
@Composable
private fun Preview36096() {
    DduruTheme { PreferencesPreviewHost(initialDateRanges = PreviewFilledDates.take(1)) }
}

@Preview(showBackground = true, heightDp = 812, name = "여행지 3개 + 날짜 없음")
@Composable
private fun Preview37406() {
    DduruTheme { PreferencesPreviewHost(initialDestinations = PreviewDestinations) }
}

@Preview(showBackground = true, heightDp = 1146, name = "여행지 + 날짜 3번째 비어 있음")
@Composable
private fun Preview35993() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDestinations = PreviewDestinations,
            initialDateRanges = PreviewFilledDates + PreferredDateRange()
        )
    }
}

@Preview(showBackground = true, heightDp = 1354, name = "3번째 캘린더 열림 (빈 칸)")
@Composable
private fun Preview36143() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDestinations = PreviewDestinations,
            initialDateRanges = PreviewFilledDates + PreferredDateRange(),
            initialEditingDateIndex = 2
        )
    }
}

@Preview(showBackground = true, heightDp = 1354, name = "3번째 캘린더 열림 (범위 선택)")
@Composable
private fun Preview36247() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDestinations = PreviewDestinations,
            initialDateRanges = PreviewFilledDates +
                PreferredDateRange(LocalDate.of(2026, 7, 17), LocalDate.of(2026, 7, 22)),
            initialEditingDateIndex = 2
        )
    }
}

@Preview(showBackground = true, heightDp = 1146, name = "여행지 + 날짜 3개")
@Composable
private fun Preview46735() {
    DduruTheme {
        PreferencesPreviewHost(
            initialDestinations = PreviewDestinations,
            initialDateRanges = PreviewFilledDates + PreviewFilledDates.last()
        )
    }
}

package com.gildongmu.dduru.presentation.feature.mypage.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.NoSearchResults
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.SearchTextField
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostMockData
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.mypage.uistate.MAX_PREFERRED_DESTINATION_COUNT
import com.gildongmu.dduru.presentation.feature.mypage.uistate.TravelDestinationSelectUiState

@Composable
fun TravelDestinationSelectScreen(
    uiState: TravelDestinationSelectUiState,
    onSearchQueryChange: (String) -> Unit,
    onLocationClick: (LocationSearchItemUiState) -> Unit,
    onResetClick: () -> Unit,
    onCloseClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        TravelDestinationSelectHeader(
            onCloseClick = onCloseClick,
            onConfirmClick = onConfirmClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            SearchTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                onSearchClick = {},
                placeholderMessage = "여행지 선택",
                showClearButton = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isSearching) {
                SearchResultContent(
                    uiState = uiState,
                    onLocationClick = onLocationClick
                )
            } else {
                DefaultContent(
                    uiState = uiState,
                    onLocationClick = onLocationClick,
                    onResetClick = onResetClick
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SearchResultContent(
    uiState: TravelDestinationSelectUiState,
    onLocationClick: (LocationSearchItemUiState) -> Unit
) {
    if (uiState.hasSearchResult && uiState.searchedLocations.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 160.dp),
            contentAlignment = Alignment.Center
        ) {
            NoSearchResults()
        }
    } else {
        LocationListSection(
            title = "검색결과",
            iconRes = R.drawable.ic_matching_searching,
            locations = uiState.searchedLocations,
            uiState = uiState,
            onLocationClick = onLocationClick
        )
    }
}

@Composable
private fun DefaultContent(
    uiState: TravelDestinationSelectUiState,
    onLocationClick: (LocationSearchItemUiState) -> Unit,
    onResetClick: () -> Unit
) {
    val pinnedLocations = uiState.pinnedLocations

    if (pinnedLocations.isNotEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${uiState.selectedLocations.size} / ${MAX_PREFERRED_DESTINATION_COUNT}개 선택됨",
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray600
            )
            Text(
                text = "초기화",
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.primary,
                modifier = Modifier.noRippleClickable(onClick = onResetClick)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            pinnedLocations.forEach { location ->
                DestinationSelectRow(
                    location = location,
                    selectionOrder = uiState.selectionOrderOf(location),
                    onClick = { onLocationClick(location) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    LocationListSection(
        title = "인기 여행지",
        iconRes = R.drawable.ic_chart_up,
        locations = uiState.popularLocations.filterNot { it in pinnedLocations },
        uiState = uiState,
        onLocationClick = onLocationClick
    )
}

@Composable
private fun LocationListSection(
    title: String,
    iconRes: Int,
    locations: List<LocationSearchItemUiState>,
    uiState: TravelDestinationSelectUiState,
    onLocationClick: (LocationSearchItemUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray600
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            locations.forEach { location ->
                DestinationSelectRow(
                    location = location,
                    selectionOrder = uiState.selectionOrderOf(location),
                    onClick = { onLocationClick(location) }
                )
            }
        }
    }
}

@Composable
private fun DestinationSelectRow(
    location: LocationSearchItemUiState,
    selectionOrder: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = selectionOrder != null

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) DduruTheme.colors.secondary100 else DduruTheme.colors.gray0)
            .border(
                width = if (isSelected) 1.dp else 0.6.dp,
                color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(all = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = DduruTheme.colors.gray100, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (location.image != null) {
                AsyncImage(
                    model = location.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home_location),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray600,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = location.city,
                style = DduruTheme.typography.Title2,
                color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.black
            )
            Text(
                text = location.countryName,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray600
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = DduruTheme.colors.primary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectionOrder.toString(),
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray0
                )
            }
        }
    }
}

@Composable
private fun TravelDestinationSelectHeader(
    onCloseClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .clip(CircleShape)
                .background(DduruTheme.colors.gray0.copy(alpha = 0.5f))
                .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                .noRippleClickable(onClick = onCloseClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "닫기",
                tint = DduruTheme.colors.gray900,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "선호 여행지",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp)
                .clip(CircleShape)
                .background(DduruTheme.colors.primary)
                .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                .noRippleClickable(onClick = onConfirmClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "완료",
                tint = DduruTheme.colors.gray0,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

private fun previewState(
    selectedIndexes: List<Int> = emptyList(),
    savedIndexes: List<Int> = emptyList(),
    query: String = "",
    searched: Boolean = false
): TravelDestinationSelectUiState {
    val popular = MatchingCreatePostMockData.popularLocations
    return TravelDestinationSelectUiState(
        searchQuery = query,
        popularLocations = popular,
        savedLocations = savedIndexes.map { popular[it] },
        selectedLocations = selectedIndexes.map { popular[it] },
        searchedLocations = if (searched) popular.take(4) else emptyList(),
        hasSearchResult = searched
    )
}

@Composable
private fun SelectPreviewHost(initial: TravelDestinationSelectUiState) {
    var uiState by remember { mutableStateOf(initial) }

    TravelDestinationSelectScreen(
        uiState = uiState,
        onSearchQueryChange = { uiState = uiState.copy(searchQuery = it) },
        onLocationClick = { uiState = uiState.toggle(it) },
        onResetClick = { uiState = uiState.reset() },
        onCloseClick = {},
        onConfirmClick = {}
    )
}

@Preview(showBackground = true, heightDp = 812, name = "인기 여행지")
@Composable
private fun Preview37468() {
    DduruTheme { SelectPreviewHost(previewState()) }
}

@Preview(showBackground = true, heightDp = 812, name = "검색 결과 + 선택 2개")
@Composable
private fun Preview37660() {
    DduruTheme {
        SelectPreviewHost(previewState(selectedIndexes = listOf(3, 0), query = "일본", searched = true))
    }
}

@Preview(showBackground = true, heightDp = 812, name = "검색 결과 없음")
@Composable
private fun Preview37454() {
    DduruTheme {
        SelectPreviewHost(
            TravelDestinationSelectUiState(searchQuery = "모나코", hasSearchResult = true)
        )
    }
}

@Preview(showBackground = true, heightDp = 812, name = "3/3 선택됨")
@Composable
private fun Preview37493() {
    DduruTheme {
        SelectPreviewHost(previewState(selectedIndexes = listOf(0, 1, 2)))
    }
}

@Preview(showBackground = true, heightDp = 812, name = "재선택 후 자동정렬")
@Composable
private fun Preview37548() {
    DduruTheme {
        SelectPreviewHost(
            previewState(selectedIndexes = listOf(0, 1, 3), savedIndexes = listOf(0, 1, 2))
        )
    }
}

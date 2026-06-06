package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostMockData
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep

@Composable
fun LocationSearchSection(
    uiState: LocationSearchUiState,
    onUiStateChange: (LocationSearchUiState) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onLocationClick: (LocationSearchItemUiState) -> Unit = {}
) {
    val sectionIconRes = if (uiState.isSearching) {
        R.drawable.ic_matching_searching
    } else {
        R.drawable.ic_chart_up
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CreatePostStepHeader(
            uiModel = MatchingCreatePostStep.LOCATION.headerUiModel,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(24f))

        SearchTextField(
            value = uiState.searchQuery,
            onValueChange = { onUiStateChange(uiState.copy(searchQuery = it)) },
            onSearchClick = onSearchClick,
            placeholderMessage = "여행지 선택",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(24f))

        if (uiState.isSearching && uiState.searchedLocations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(468f),
                contentAlignment = Alignment.Center
            ) {
                NoSearchResults(
                    modifier = Modifier.aspectRatio(266f / 123f)
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = sectionIconRes),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = uiState.sectionTitle,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray600
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(468f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.visibleLocations
                ) { location ->
                    LocationSearchCardComponent(
                        region = location.city,
                        country = location.countryName,
                        onClick = { onLocationClick(location) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationSearchNoResultsPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxWidth()
        ) {
            LocationSearchSection(
                uiState = LocationSearchUiState(
                    searchQuery = "없는 여행지",
                    searchedLocations = emptyList()
                ),
                onUiStateChange = {},
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationSearchSectionPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxWidth()
        ) {
            LocationSearchSection(
                uiState = LocationSearchUiState(
                    popularLocations = MatchingCreatePostMockData.popularLocations,
                    searchedLocations = MatchingCreatePostMockData.popularLocations
                ),
                onUiStateChange = {},
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

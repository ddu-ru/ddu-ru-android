package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingSearchFilterSection(
    searchQuery: String,
    selectedSort: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchFieldClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MatchingSearchField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            onClick = onSearchFieldClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchingFilterChip(
                text = "필터",
                leadingIconRes = R.drawable.ic_list,
                onClick = onFilterClick
            )

            MatchingFilterChip(
                text = selectedSort,
                trailingIconRes = R.drawable.ic_chevron_down,
                onClick = onSortClick
            )
        }
    }
}

@Composable
private fun MatchingSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray0)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(99.dp)
            )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = true,
            singleLine = true,
            textStyle = DduruTheme.typography.Body1.copy(
                color = DduruTheme.colors.gray900
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 13.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "도시, 국가 혹은 해시태그 검색",
                                style = DduruTheme.typography.Body1,
                                color = DduruTheme.colors.gray500
                            )
                        }

                        innerTextField()
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.ic_matching_searching),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray400,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .noRippleClickable(onClick = onClick)
        )
    }
}

@Composable
private fun MatchingFilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIconRes: Int? = null,
    trailingIconRes: Int? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray200)
            .noRippleClickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        leadingIconRes?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = DduruTheme.colors.gray700,
                modifier = Modifier.size(12.dp)
            )
        }

        Text(
            text = text,
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray700
        )

        trailingIconRes?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = DduruTheme.colors.gray700,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingSearchFilterSectionPreview() {
    DduruTheme {
        MatchingSearchFilterSection(
            searchQuery = "",
            selectedSort = "최신순",
            onSearchQueryChange = {},
            onSearchFieldClick = {},
            onFilterClick = {},
            onSortClick = {}
        )
    }
}

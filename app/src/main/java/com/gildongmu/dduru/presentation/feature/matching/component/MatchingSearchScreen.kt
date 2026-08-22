package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MatchingSearchScreen(
    searchQuery: String,
    recentKeywords: ImmutableList<String>,
    popularKeywords: ImmutableList<String>,
    onSearchQueryChange: (String) -> Unit,
    onSearchKeywordClick: (String) -> Unit,
    onRecentKeywordRemove: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        event.changes.forEach { it.consume() }
                    }
                }
            }
            .background(DduruTheme.colors.gray0.copy(alpha = 0.7f))
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        MatchingSearchTopBar(
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(22.dp))

        MatchingSearchInput(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            onSearchClick = { onSearchKeywordClick(searchQuery) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        MatchingKeywordSection(
            title = "최근 검색어",
            keywords = recentKeywords,
            removable = true,
            onKeywordClick = onSearchKeywordClick,
            onKeywordRemove = onRecentKeywordRemove
        )

        Spacer(modifier = Modifier.height(24.dp))

        MatchingKeywordSection(
            title = "인기 검색어",
            keywords = popularKeywords,
            removable = false,
            onKeywordClick = onSearchKeywordClick,
            onKeywordRemove = {}
        )
    }
}

@Composable
private fun MatchingSearchTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .dduruDualShadow(
                    firstAlpha = 0.10f,
                    secondAlpha = 0.08f,
                    shape = CircleShape,
                    firstElevation = 16.dp,
                    secondElevation = 8.dp
                )
                .clip(CircleShape)
                .background(DduruTheme.colors.gray0.copy(alpha = 0.5f))
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .size(48.dp)
                .noRippleClickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = null,
                tint = DduruTheme.colors.black,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "검색",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MatchingSearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = DduruTheme.typography.Body1.copy(
            color = DduruTheme.colors.gray900
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearchClick()
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray0)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(start = 16.dp, end = 12.dp),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            focusManager.clearFocus()
                            onSearchClick()
                        }
                )
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MatchingKeywordSection(
    title: String,
    keywords: ImmutableList<String>,
    removable: Boolean,
    onKeywordClick: (String) -> Unit,
    onKeywordRemove: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray700,
            modifier = Modifier.fillMaxWidth()
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            keywords.forEach { keyword ->
                MatchingKeywordChip(
                    text = keyword,
                    removable = removable,
                    onClick = { onKeywordClick(keyword) },
                    onRemoveClick = { onKeywordRemove(keyword) }
                )
            }
        }
    }
}

@Composable
private fun MatchingKeywordChip(
    text: String,
    removable: Boolean,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
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
        Text(
            text = text,
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray700
        )

        if (removable) {
            Icon(
                painter = painterResource(id = R.drawable.ic_x_gray),
                contentDescription = null,
                tint = DduruTheme.colors.gray700,
                modifier = Modifier
                    .size(12.dp)
                    .noRippleClickable(onClick = onRemoveClick)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun MatchingSearchScreenPreview() {
    DduruTheme {
        MatchingSearchScreen(
            searchQuery = "",
            recentKeywords = persistentListOf("가오슝", "제주도"),
            popularKeywords = persistentListOf(
                "일본",
                "이탈리아",
                "이탈리아",
                "밀라노",
                "일본",
                "이탈리아",
                "밀라노",
                "밀라노",
                "밀라노",
                "밀라노"
            ),
            onSearchQueryChange = {},
            onSearchKeywordClick = {},
            onRecentKeywordRemove = {},
            onBackClick = {}
        )
    }
}

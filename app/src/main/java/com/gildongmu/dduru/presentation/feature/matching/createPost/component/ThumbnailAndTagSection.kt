package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.component.HomeHashtag
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostMockData
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThumbnailAndTagSection(
    uiState: ThumbnailAndTagUiState,
    onUiStateChange: (ThumbnailAndTagUiState) -> Unit,
    modifier: Modifier = Modifier,
    popularTags: List<String> = emptyList()
) {
    val focusManager = LocalFocusManager.current

    val onAddTag = {
        if (uiState.canAddTag) {
            onUiStateChange(uiState.addTag())
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CreatePostStepHeader(
            uiModel = MatchingCreatePostStep.THUMBNAIL_AND_TAG.headerUiModel
        )

        Spacer(modifier = Modifier.weight(24f))

        Text(
            text = "대표사진",
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray868
        )

        Spacer(modifier = Modifier.height(16.dp))

        ThumbnailImageCard(
            regionName = uiState.regionName,
            countryName = uiState.countryName,
            imageResId = uiState.imageResId,
            onChangeClick = {}
        )

        Spacer(modifier = Modifier.weight(40f))

        Text(
            text = "해시태그",
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray868
        )

        Spacer(modifier = Modifier.height(12.dp))

        RoundedMatchingTextField(
            value = uiState.tagInput,
            onValueChange = { onUiStateChange(uiState.copy(tagInput = it)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hashtag),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray400
                )
            },
            placeholder = {
                Text(
                    text = "해시태그 입력...",
                    style = DduruTheme.typography.Body1,
                    color = DduruTheme.colors.gray500
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onAddTag() }
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "한글 4자 또는 영문/숫자 7자 이하로 입력해 주세요.",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray400
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "추가된 태그",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.weight(16f))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.addedTags.forEach { addedTag ->
                HomeHashtag(
                    text = addedTag,
                    textColor = DduruTheme.colors.gray700,
                    textStyle = DduruTheme.typography.Caption1,
                    backgroundColor = DduruTheme.colors.gray200,
                    contentPadding = PaddingValues(12.dp),
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clickable {
                                    onUiStateChange(uiState.removeTag(addedTag))
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxWidth(),
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null,
                                tint = DduruTheme.colors.gray700
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(16f))

        Text(
            text = "인기 태그",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            popularTags.forEach { popularTag ->
                HomeHashtag(
                    text = popularTag,
                    textColor = DduruTheme.colors.gray700,
                    textStyle = DduruTheme.typography.Caption1,
                    backgroundColor = DduruTheme.colors.gray200,
                    contentPadding = PaddingValues(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(33f))
    }
}

@Preview(showBackground = true)
@Composable
private fun ThumbnailAndTagSectionPreview() {
    DduruTheme {
        var uiState by remember {
            mutableStateOf(
                ThumbnailAndTagUiState(
                    addedTags = listOf()
                )
            )
        }

        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxWidth()
        ) {
            ThumbnailAndTagSection(
                uiState = uiState,
                onUiStateChange = { uiState = it },
                modifier = Modifier.padding(24.dp),
                popularTags = MatchingCreatePostMockData.popularTags
            )
        }
    }
}

package com.gildongmu.dduru.presentation.feature.matching.createPost.screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.DateSelectionSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.LocationSearchSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.PostWritingSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RecruitmentAgeSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RecruitmentCountSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.ThumbnailAndTagSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiEvent
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel.MatchingCreatePostViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MatchingCreatePostRoute(
    onClose: () -> Unit,
    onComplete: () -> Unit,
    viewModel: MatchingCreatePostViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                MatchingCreatePostUiEvent.Close -> onClose()
                MatchingCreatePostUiEvent.Complete -> onComplete()
            }
        }
    }

    MatchingCreatePostScreen(
        uiState = uiState,
        onDismissClick = viewModel::onDismissClick,
        onBackClick = viewModel::onBackClick,
        onNextClick = viewModel::onNextClick,
        onLocationSearchUiStateChange = viewModel::onLocationSearchUiStateChange,
        onLocationSelected = viewModel::onLocationSelected,
        onDateSectionUiStateChange = viewModel::onDateSectionUiStateChange,
        onRecruitmentSectionUiStateChange = viewModel::onRecruitmentSectionUiStateChange,
        onAgeSectionUiStateChange = viewModel::onAgeSectionUiStateChange,
        onPostWritingUiStateChange = viewModel::onPostWritingUiStateChange,
        onThumbnailAndTagUiStateChange = viewModel::onThumbnailAndTagUiStateChange,
        modifier = modifier
    )
}

@Composable
fun MatchingCreatePostScreen(
    uiState: MatchingCreatePostUiState,
    onDismissClick: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onLocationSearchUiStateChange: (LocationSearchUiState) -> Unit,
    onLocationSelected: (LocationSearchItemUiState) -> Unit,
    onDateSectionUiStateChange: (DateSectionUiState) -> Unit,
    onRecruitmentSectionUiStateChange: (RecruitmentScreenUiState) -> Unit,
    onAgeSectionUiStateChange: (AgeSectionUiState) -> Unit,
    onPostWritingUiStateChange: (PostWritingUiState) -> Unit,
    onThumbnailAndTagUiStateChange: (ThumbnailAndTagUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.statusBarsPadding()
    ) {
        MatchingCreatePostHeader(
            isNowStepOne = uiState.isFirstStep,
            region = uiState.region,
            country = uiState.country,
            onDismissClick = onDismissClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (uiState.currentStep) {
                MatchingCreatePostStep.LOCATION -> LocationSearchSection(
                    uiState = uiState.locationSearchUiState,
                    onUiStateChange = onLocationSearchUiStateChange,
                    onLocationClick = onLocationSelected,
                    modifier = Modifier
                )

                MatchingCreatePostStep.DATE -> DateSelectionSection(
                    uiState = uiState.dateSectionUiState,
                    onUiStateChange = onDateSectionUiStateChange,
                    modifier = Modifier
                )

                MatchingCreatePostStep.RECRUITMENT_COUNT -> RecruitmentCountSection(
                    uiState = uiState.recruitmentSectionUiState,
                    onUiStateChange = onRecruitmentSectionUiStateChange,
                    modifier = Modifier
                )

                MatchingCreatePostStep.RECRUITMENT_AGE -> RecruitmentAgeSection(
                    uiState = uiState.ageSectionUiState,
                    onUiStateChange = onAgeSectionUiStateChange,
                    modifier = Modifier
                )

                MatchingCreatePostStep.POST_WRITING -> PostWritingSection(
                    uiState = uiState.postWritingUiState,
                    onUiStateChange = onPostWritingUiStateChange,
                    modifier = Modifier
                )

                MatchingCreatePostStep.THUMBNAIL_AND_TAG -> ThumbnailAndTagSection(
                    uiState = uiState.thumbnailAndTagUiState,
                    onUiStateChange = onThumbnailAndTagUiStateChange,
                    popularTags = listOf("맛집", "힐링여행", "액티비티", "사진스팟"),
                    modifier = Modifier
                )
            }
        }

        if (uiState.isFirstStep) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = uiState.nextButtonText,
                    onClick = onNextClick,
                    enabled = uiState.isNextEnabled,
                    enabledColors = DduruButtonDefaults.filledEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "이전",
                    onClick = onBackClick,
                    enabledColors = DduruButtonDefaults.whiteEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    modifier = Modifier.weight(1f)
                )

                DduruButton(
                    text = uiState.nextButtonText,
                    onClick = onNextClick,
                    enabled = uiState.isNextEnabled,
                    enabledColors = DduruButtonDefaults.filledEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun MatchingCreatePostHeader(
    isNowStepOne: Boolean,
    region: String?,
    country: String?,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onDismissClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(99.dp),
                    spotColor = DduruTheme.colors.black.copy(alpha = 0.8f),
                    ambientColor = DduruTheme.colors.black.copy(alpha = 0.8f)
                )
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(99.dp),
                    spotColor = DduruTheme.colors.black.copy(alpha = 0.1f),
                    ambientColor = DduruTheme.colors.black.copy(alpha = 0.1f)
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(99.dp)
                )
                .background(
                    color = DduruTheme.colors.gray0,
                    shape = RoundedCornerShape(99.dp)
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = DduruTheme.colors.black
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "여행방 생성",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.black
            )

            if (!isNowStepOne && (region != null || country != null)) {
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    region?.let {
                        Text(
                            text = it,
                            style = DduruTheme.typography.Micro,
                            color = DduruTheme.colors.gray400
                        )
                    }

                    if (region != null && country != null) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(DduruTheme.colors.gray200, CircleShape)
                        )
                    }

                    country?.let {
                        Text(
                            text = it,
                            style = DduruTheme.typography.Micro,
                            color = DduruTheme.colors.gray400
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingCreatePostContentPreview() {
    DduruTheme {
        MatchingCreatePostScreen(
            uiState = MatchingCreatePostUiState(
                currentStep = MatchingCreatePostStep.DATE,
                locationSearchUiState = LocationSearchUiState(
                    selectedLocation = LocationSearchItemUiState(
                        countryName = "도쿄",
                        city = "일본"
                    )
                )
            ),
            onDismissClick = {},
            onBackClick = {},
            onNextClick = {},
            onLocationSearchUiStateChange = {},
            onLocationSelected = {},
            onDateSectionUiStateChange = {},
            onRecruitmentSectionUiStateChange = {},
            onAgeSectionUiStateChange = {},
            onPostWritingUiStateChange = {},
            onThumbnailAndTagUiStateChange = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

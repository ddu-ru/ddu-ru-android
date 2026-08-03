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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
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
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiAction
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiEvent
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.reduce
import com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel.MatchingCreatePostViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MatchingCreatePostRoute(
    onClose: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchingCreatePostViewModel = hiltViewModel()
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
        onLocationSearchClick = viewModel::onLocationSearchClick,
        onLocationSelected = viewModel::onLocationSelected,
        onDateSectionAction = viewModel::onDateSectionAction,
        onRecruitmentSectionUiStateChange = viewModel::onRecruitmentSectionUiStateChange,
        onAgeSectionUiStateChange = viewModel::onAgeSectionUiStateChange,
        onPostWritingUiStateChange = viewModel::onPostWritingUiStateChange,
        onThumbnailAndTagUiStateChange = viewModel::onThumbnailAndTagUiStateChange,
        onTagInputChange = viewModel::onTagInputChange,
        onAddTag = viewModel::onAddTag,
        onRemoveTag = viewModel::onRemoveTag,
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
    onLocationSearchClick: () -> Unit,
    onLocationSelected: (LocationSearchItemUiState) -> Unit,
    onDateSectionAction: (DateSectionUiAction) -> Unit,
    onRecruitmentSectionUiStateChange: (RecruitmentScreenUiState) -> Unit,
    onAgeSectionUiStateChange: (AgeSectionUiState) -> Unit,
    onPostWritingUiStateChange: (PostWritingUiState) -> Unit,
    onThumbnailAndTagUiStateChange: (ThumbnailAndTagUiState) -> Unit,
    onTagInputChange: (String) -> Unit,
    onAddTag: () -> Unit,
    onRemoveTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = DduruTheme.colors.gray0,
        topBar = {
            MatchingCreatePostHeader(
                isNowStepOne = uiState.isFirstStep,
                region = uiState.region,
                country = uiState.country,
                onDismissClick = onDismissClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
            )
        },
        bottomBar = {
            if (uiState.hasBottomButton) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (uiState.hasBackButton) {
                            DduruButton(
                                text = "이전",
                                onClick = onBackClick,
                                enabledColors = DduruButtonDefaults.whiteEnabledColors,
                                disabledColors = DduruButtonDefaults.whiteDisabledColors,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        DduruButton(
                            text = uiState.nextButtonText,
                            onClick = onNextClick,
                            enabled = uiState.isNextEnabled,
                            enabledColors = DduruButtonDefaults.filledEnabledColors,
                            disabledColors = DduruButtonDefaults.whiteDisabledColors,
                            modifier = Modifier.weight(uiState.nextButtonWeight)
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 40.dp)
        ) {
            when (uiState.currentStep) {
                MatchingCreatePostStep.LOCATION -> {
                    LocationSearchSection(
                        uiState = uiState.locationSearchUiState,
                        onUiStateChange = onLocationSearchUiStateChange,
                        onSearchClick = onLocationSearchClick,
                        onLocationClick = onLocationSelected
                    )
                }

                MatchingCreatePostStep.DATE -> {
                    DateSelectionSection(
                        uiState = uiState.dateSectionUiState,
                        onAction = onDateSectionAction
                    )
                }

                MatchingCreatePostStep.RECRUITMENT_COUNT -> {
                    RecruitmentCountSection(
                        uiState = uiState.recruitmentSectionUiState,
                        onUiStateChange = onRecruitmentSectionUiStateChange
                    )
                }

                MatchingCreatePostStep.RECRUITMENT_AGE -> {
                    RecruitmentAgeSection(
                        uiState = uiState.ageSectionUiState,
                        onUiStateChange = onAgeSectionUiStateChange
                    )
                }

                MatchingCreatePostStep.POST_WRITING -> {
                    PostWritingSection(
                        uiState = uiState.postWritingUiState,
                        onUiStateChange = onPostWritingUiStateChange
                    )
                }

                MatchingCreatePostStep.THUMBNAIL_AND_TAG -> {
                    ThumbnailAndTagSection(
                        uiState = uiState.thumbnailAndTagUiState,
                        onTagInputChange = onTagInputChange,
                        onAddTag = onAddTag,
                        onRemoveTag = onRemoveTag
                    )
                }
            }
        }
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
                .dduruDualShadow(
                    firstAlpha = 0.8f,
                    secondAlpha = 0.1f,
                    shape = RoundedCornerShape(99.dp)
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
    var uiState by remember {
        mutableStateOf(MatchingCreatePostMockData.screenUiState)
    }

    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxSize()
        ) {
            MatchingCreatePostScreen(
                uiState = uiState,
                onDismissClick = {},
                onBackClick = {
                    uiState.currentStep.previousOrNull()?.let { previousStep ->
                        uiState = uiState.copy(currentStep = previousStep)
                    }
                },
                onNextClick = {
                    if (uiState.isNextEnabled) {
                        uiState.currentStep.nextOrNull()?.let { nextStep ->
                            uiState = uiState.copy(currentStep = nextStep)
                        }
                    }
                },
                onLocationSearchUiStateChange = { locationSearchUiState ->
                    uiState = uiState.copy(locationSearchUiState = locationSearchUiState)
                },
                onLocationSearchClick = {},
                onLocationSelected = { location ->
                    uiState = uiState.copy(
                        locationSearchUiState = uiState.locationSearchUiState.copy(
                            selectedLocation = location,
                            searchQuery = location.city
                        ),
                        thumbnailAndTagUiState = uiState.thumbnailAndTagUiState.copy(
                            regionName = location.city,
                            countryName = location.countryName
                        ),
                        currentStep = MatchingCreatePostStep.DATE
                    )
                },
                onDateSectionAction = { action ->
                    uiState = uiState.copy(dateSectionUiState = uiState.dateSectionUiState.reduce(action))
                },
                onRecruitmentSectionUiStateChange = { recruitmentSectionUiState ->
                    uiState = uiState.copy(recruitmentSectionUiState = recruitmentSectionUiState)
                },
                onAgeSectionUiStateChange = { ageSectionUiState ->
                    uiState = uiState.copy(ageSectionUiState = ageSectionUiState)
                },
                onPostWritingUiStateChange = { postWritingUiState ->
                    uiState = uiState.copy(postWritingUiState = postWritingUiState)
                },
                onThumbnailAndTagUiStateChange = {},
                onTagInputChange = { input ->
                    uiState = uiState.copy(
                        thumbnailAndTagUiState = uiState.thumbnailAndTagUiState.copy(
                            tagInput = input
                        )
                    )
                },
                onAddTag = {},
                onRemoveTag = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

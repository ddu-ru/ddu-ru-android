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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.DateSelectionSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.PostWritingSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RecruitmentAgeSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RecruitmentCountSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.ThumbnailAndTagSection
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel.MatchingCreatePostViewModel

@Composable
fun MatchingCreatePostScreen(
    viewModel: MatchingCreatePostViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MatchingCreatePostContent(
        currentStep = uiState.currentStep,
        region = uiState.region,
        country = uiState.country,
        ageSectionUiState = uiState.ageSectionUiState,
        isNextEnabled = uiState.isNextEnabled,
        onDismissClick = viewModel::onDismissClick,
        onBackClick = viewModel::onBackClick,
        onNextClick = viewModel::onNextClick,
        onAgeSectionUiStateChange = viewModel::onAgeSectionUiStateChange,
        modifier = modifier
    )
}

@Composable
private fun MatchingCreatePostContent(
    currentStep: Int,
    region: String?,
    country: String?,
    ageSectionUiState: AgeSectionUiState,
    isNextEnabled: Boolean,
    onDismissClick: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onAgeSectionUiStateChange: (AgeSectionUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val isNowStepOne = currentStep == 1
    val nextButtonText = if (currentStep == 6) "작성 완료" else "다음"

    Column(
        modifier = modifier.statusBarsPadding()
    ) {
        MatchingCreatePostHeader(
            isNowStepOne = isNowStepOne,
            region = region,
            country = country,
            onDismissClick = onDismissClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (currentStep) {
//                1 -> LocationSearchSection(
//                    modifier = Modifier.padding(horizontal = 24.dp)
//                ) 머지 후 주석 없앨 예정입니다

                2 -> DateSelectionSection(
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                3 -> RecruitmentCountSection(
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                4 -> RecruitmentAgeSection(
                    uiState = ageSectionUiState,
                    onUiStateChange = onAgeSectionUiStateChange,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                5 -> PostWritingSection(
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                6 -> ThumbnailAndTagSection(
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        if (!isNowStepOne) {
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
                    text = nextButtonText,
                    onClick = onNextClick,
                    enabled = isNextEnabled,
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
        MatchingCreatePostContent(
            currentStep = 2,
            region = "일본",
            country = "도쿄",
            ageSectionUiState = AgeSectionUiState(),
            isNextEnabled = true,
            onDismissClick = {},
            onBackClick = {},
            onNextClick = {},
            onAgeSectionUiStateChange = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

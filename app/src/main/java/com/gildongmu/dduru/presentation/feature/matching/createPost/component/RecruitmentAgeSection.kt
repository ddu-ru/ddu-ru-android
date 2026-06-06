package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.model.StepHeaderUiModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentGenderOption
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RecruitmentAgeSection(
    uiStateFlow: StateFlow<MatchingCreatePostUiState>,
    onUiStateChange: (AgeSectionUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenUiState = uiStateFlow.collectAsStateWithLifecycle()
    val uiState = screenUiState.value.ageSectionUiState

    RecruitmentAgeContent(
        uiState = uiState,
        onUiStateChange = onUiStateChange,
        modifier = modifier
    )
}

@Composable
private fun RecruitmentAgeContent(
    uiState: AgeSectionUiState,
    onUiStateChange: (AgeSectionUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val stepHeaderAge = remember {
        StepHeaderUiModel(
            step = 4,
            title = "함께하고 싶은 상대를 알려주세요",
            subtitle = "성별과 연령대를 선택해주세요"
        )
    }

    val selectedButtonColors = DduruButtonEnabledColors(
        backgroundColor = DduruTheme.colors.secondary,
        textColor = DduruTheme.colors.primary,
        borderColor = DduruTheme.colors.primary
    )

    val unselectedButtonColors = DduruButtonEnabledColors(
        backgroundColor = DduruTheme.colors.gray0,
        textColor = DduruTheme.colors.gray500,
        borderColor = DduruTheme.colors.gray200
    )

    val disabledButtonColors = DduruButtonDisabledColors(
        backgroundColor = DduruTheme.colors.gray200,
        textColor = DduruTheme.colors.gray400,
        borderColor = DduruTheme.colors.gray300
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreatePostStepHeader(uiModel = stepHeaderAge)

        Spacer(modifier = Modifier.weight(24f))

        Text(
            text = "함께하고 싶은 성별",
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray800,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            RecruitmentGenderOption.entries.forEach { option ->
                DduruButton(
                    modifier = Modifier.weight(1f),
                    text = option.label,
                    onClick = {
                        onUiStateChange(
                            uiState.copy(selectedGender = option)
                        )
                    },
                    enabledColors = if (uiState.selectedGender == option) {
                        selectedButtonColors
                    } else {
                        unselectedButtonColors
                    },
                    disabledColors = disabledButtonColors
                )
            }
        }

        Spacer(modifier = Modifier.weight(32f))

        AgeRangeBar(
            selectedStart = if (uiState.isAgeIrrelevant) null else uiState.selectedStartAge,
            selectedEnd = if (uiState.isAgeIrrelevant) null else uiState.selectedEndAge,
            onRangeChanged = { start, end ->
                onUiStateChange(
                    uiState.copy(
                        selectedStartAge = start,
                        selectedEndAge = end,
                        isAgeIrrelevant = false
                    )
                )
            },
            enabled = !uiState.isAgeIrrelevant,
            isAgeIrrelevant = uiState.isAgeIrrelevant,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OptionalCheckbox(
            checked = uiState.isAgeIrrelevant,
            text = "나이는 상관 없어요",
            onCheckedChange = { checked ->
                onUiStateChange(
                    if (checked) {
                        uiState.copy(
                            selectedStartAge = null,
                            selectedEndAge = null,
                            isAgeIrrelevant = true
                        )
                    } else {
                        uiState.copy(isAgeIrrelevant = false)
                    }
                )
            }
        )

        Spacer(modifier = Modifier.weight(226f))
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentAgeSectionPreview() {
    DduruTheme {
        Surface(color = DduruTheme.colors.gray0) {
            var uiState by remember {
                mutableStateOf(
                    AgeSectionUiState(
                        selectedGender = RecruitmentGenderOption.ANY
                    )
                )
            }

            RecruitmentAgeContent(
                uiState = uiState,
                onUiStateChange = { uiState = it },
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 52.dp)
            )
        }
    }
}

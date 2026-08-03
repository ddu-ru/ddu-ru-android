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
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.defaults.MatchingOptionButtonDefaults
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender

@Composable
fun RecruitmentAgeSection(
    uiState: AgeSectionUiState,
    onUiStateChange: (AgeSectionUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedButtonColors = MatchingOptionButtonDefaults.selectedColors
    val unselectedButtonColors = MatchingOptionButtonDefaults.unselectedColors
    val disabledButtonColors = MatchingOptionButtonDefaults.disabledColors
    val ageRangeText = when {
        uiState.isAgeIrrelevant -> "연령무관"
        uiState.selectedStartAge != null && uiState.selectedEndAge != null -> {
            "${uiState.selectedStartAge}-${uiState.selectedEndAge}세"
        }
        else -> "선택해주세요"
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreatePostStepHeader(uiModel = MatchingCreatePostStep.RECRUITMENT_AGE.headerUiModel)

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
            MatchingGender.entries.forEach { option ->
                DduruButton(
                    modifier = Modifier.weight(1f),
                    text = option.selectionLabel,
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "함께하고 싶은 연령대",
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800
            )

            Text(
                text = ageRangeText,
                style = DduruTheme.typography.Title3,
                color = if (
                    uiState.isAgeIrrelevant ||
                    (uiState.selectedStartAge != null && uiState.selectedEndAge != null)
                ) {
                    DduruTheme.colors.primary
                } else {
                    DduruTheme.colors.gray500
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            modifier = Modifier.fillMaxWidth(),
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
                        selectedGender = MatchingGender.ANY
                    )
                )
            }

            RecruitmentAgeSection(
                uiState = uiState,
                onUiStateChange = { uiState = it },
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 52.dp)
            )
        }
    }
}

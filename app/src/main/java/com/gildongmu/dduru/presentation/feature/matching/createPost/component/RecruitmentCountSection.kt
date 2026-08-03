package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.defaults.MatchingOptionButtonDefaults
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentCountNum
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType

@Composable
fun RecruitmentCountSection(
    uiState: RecruitmentScreenUiState,
    onUiStateChange: (RecruitmentScreenUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedButtonColors = MatchingOptionButtonDefaults.selectedColors
    val unselectedButtonColors = MatchingOptionButtonDefaults.unselectedColors
    val disabledButtonColors = MatchingOptionButtonDefaults.disabledColors

    val isCompanionTypeSelectionEnabled = !uiState.isCompanionTypeOptional

    val isFullSelected =
        uiState.selectedCompanionType == MatchingCompanionType.FULL &&
            isCompanionTypeSelectionEnabled

    val isPartialSelected =
        uiState.selectedCompanionType == MatchingCompanionType.PARTIAL &&
            isCompanionTypeSelectionEnabled

    val isMealSelected =
        uiState.selectedCompanionType == MatchingCompanionType.MEAL &&
            isCompanionTypeSelectionEnabled

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreatePostStepHeader(
            uiModel = MatchingCreatePostStep.RECRUITMENT_COUNT.headerUiModel
        )

        Spacer(modifier = Modifier.weight(8f))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = "총 모집인원",
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray800
        )

        RecruitmentCount(
            uiState = uiState.recruitmentCount,
            onCountChange = { newCount ->
                onUiStateChange(
                    uiState.copy(
                        recruitmentCount = uiState.recruitmentCount.copy(
                            rawCount = newCount.coerceIn(
                                RecruitmentCountNum.MIN,
                                RecruitmentCountNum.MAX
                            )
                        )
                    )
                )
            }
        )

        Spacer(modifier = Modifier.weight(6f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "동행 형태",
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800
            )

            OptionalCheckbox(
                checked = uiState.isCompanionTypeOptional,
                onCheckedChange = { isChecked ->
                    onUiStateChange(
                        uiState.copy(
                            isCompanionTypeOptional = isChecked,
                            selectedCompanionType = if (isChecked) {
                                null
                            } else {
                                uiState.selectedCompanionType
                            }
                        )
                    )
                },
                text = "상관없어요"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            DduruButton(
                modifier = Modifier.weight(1f),
                text = MatchingCompanionType.FULL.selectionLabel,
                onClick = {
                    onUiStateChange(
                        uiState.copy(
                            selectedCompanionType = MatchingCompanionType.FULL,
                            isCompanionTypeOptional = false
                        )
                    )
                },
                enabled = isCompanionTypeSelectionEnabled,
                enabledColors = if (isFullSelected) {
                    selectedButtonColors
                } else {
                    unselectedButtonColors
                },
                disabledColors = disabledButtonColors
            )

            DduruButton(
                modifier = Modifier.weight(1f),
                text = MatchingCompanionType.PARTIAL.selectionLabel,
                onClick = {
                    onUiStateChange(
                        uiState.copy(
                            selectedCompanionType = MatchingCompanionType.PARTIAL,
                            isCompanionTypeOptional = false
                        )
                    )
                },
                enabled = isCompanionTypeSelectionEnabled,
                enabledColors = if (isPartialSelected) {
                    selectedButtonColors
                } else {
                    unselectedButtonColors
                },
                disabledColors = disabledButtonColors
            )

            DduruButton(
                modifier = Modifier.weight(1f),
                text = MatchingCompanionType.MEAL.selectionLabel,
                onClick = {
                    onUiStateChange(
                        uiState.copy(
                            selectedCompanionType = MatchingCompanionType.MEAL,
                            isCompanionTypeOptional = false
                        )
                    )
                },
                enabled = isCompanionTypeSelectionEnabled,
                enabledColors = if (isMealSelected) {
                    selectedButtonColors
                } else {
                    unselectedButtonColors
                },
                disabledColors = disabledButtonColors
            )
        }

        uiState.companionTypeGuideMessage?.let { guideMessage ->
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = guideMessage,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray400
            )
        }

        Spacer(modifier = Modifier.weight(130f))
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentCountSectionPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0
        ) {
            RecruitmentCountSection(
                uiState = RecruitmentScreenUiState(),
                onUiStateChange = {}
            )
        }
    }
}

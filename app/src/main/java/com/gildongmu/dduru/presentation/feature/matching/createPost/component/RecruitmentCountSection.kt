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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.model.StepHeaderUiModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.CompanionType
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentCountNum
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState

@Composable
fun RecruitmentCountSection(
    modifier: Modifier = Modifier
) {
    var uiState by remember { mutableStateOf(RecruitmentScreenUiState()) }

    val stepHeaderRecruitmentCount = remember {
        StepHeaderUiModel(
            step = 3,
            title = "모집 인원과 동행 형태를 설정해 주세요",
            subtitle = "몇 명이 함께할지, 어떤 방식으로 동행할지 선택해 주세요"
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

    val isCompanionTypeSelectionEnabled = !uiState.isCompanionTypeOptional

    val isFullSelected =
        uiState.selectedCompanionType == CompanionType.FULL &&
            isCompanionTypeSelectionEnabled

    val isPartialSelected =
        uiState.selectedCompanionType == CompanionType.PARTIAL &&
            isCompanionTypeSelectionEnabled

    val isMealSelected =
        uiState.selectedCompanionType == CompanionType.MEAL &&
            isCompanionTypeSelectionEnabled

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreatePostStepHeader(
            uiModel = stepHeaderRecruitmentCount
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
                uiState = uiState.copy(
                    recruitmentCount = uiState.recruitmentCount.copy(
                        rawCount = newCount.coerceIn(
                            RecruitmentCountNum.MIN,
                            RecruitmentCountNum.MAX
                        )
                    )
                )
            }
        )

        Spacer(modifier = Modifier.weight(6f))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = "동행 형태",
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray800
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            DduruButton(
                modifier = Modifier.weight(1f),
                text = "전체 동행",
                onClick = {
                    uiState = uiState.copy(
                        selectedCompanionType = CompanionType.FULL,
                        isCompanionTypeOptional = false
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
                text = "부분 동행",
                onClick = {
                    uiState = uiState.copy(
                        selectedCompanionType = CompanionType.PARTIAL,
                        isCompanionTypeOptional = false
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
                text = "식사 동행",
                onClick = {
                    uiState = uiState.copy(
                        selectedCompanionType = CompanionType.MEAL,
                        isCompanionTypeOptional = false
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

        Spacer(modifier = Modifier.weight(32f))

        OptionalCheckbox(
            checked = uiState.isCompanionTypeOptional,
            onCheckedChange = { isChecked ->
                uiState = uiState.copy(
                    isCompanionTypeOptional = isChecked,
                    selectedCompanionType = if (isChecked) {
                        null
                    } else {
                        uiState.selectedCompanionType
                    }
                )
            },
            text = "동행 형태는 상관없어요."
        )

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
            RecruitmentCountSection()
        }
    }
}

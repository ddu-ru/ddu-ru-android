package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.AgeRangeBar
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.DduruCalendar
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.TripDatePickerBox
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val DateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
private val RecruitmentStatusOptions = listOf("모집중", "마감임박", "모집 완료")
private val ParticipantQuickOptions = listOf("2명", "4명", "2-4명")

@Composable
fun MatchingDateFilterContent(
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TripDatePickerBox(
                label = "출발 날짜",
                selectedDate = selectedStartDate?.format(DateFormatter),
                onClick = {},
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(10.dp)
            )

            TripDatePickerBox(
                label = "도착 날짜",
                selectedDate = selectedEndDate?.format(DateFormatter),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }

        DduruCalendar(
            selectedStartDate = selectedStartDate,
            selectedEndDate = selectedEndDate,
            onDateSelected = onDateSelected,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MatchingDetailFilterContent(
    selectedRecruitmentStatus: String?,
    onRecruitmentStatusSelected: (String) -> Unit,
    selectedGender: MatchingGender?,
    onGenderSelected: (MatchingGender) -> Unit,
    selectedParticipantOption: String?,
    onParticipantOptionSelected: (String) -> Unit,
    selectedAgeStart: Int?,
    selectedAgeEnd: Int?,
    onAgeRangeChanged: (Int, Int) -> Unit,
    selectedCompanionType: MatchingCompanionType?,
    onCompanionTypeSelected: (MatchingCompanionType) -> Unit,
    onDestinationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MatchingFilterSection(title = "여행지") {
            MatchingDestinationField(onClick = onDestinationClick)
        }

        MatchingFilterSection(title = "모집 상태") {
            MatchingSegmentedOptionRow(
                options = RecruitmentStatusOptions,
                selectedOption = selectedRecruitmentStatus,
                onOptionSelected = onRecruitmentStatusSelected
            )
        }

        MatchingFilterSection(title = "성별") {
            MatchingSegmentedOptionRow(
                options = MatchingGender.entries.map { it.selectionLabel },
                selectedOption = selectedGender?.selectionLabel,
                onOptionSelected = { label ->
                    MatchingGender.entries.firstOrNull { it.selectionLabel == label }
                        ?.let(onGenderSelected)
                }
            )
        }

        MatchingFilterSection(title = "인원수") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MatchingUnderlineInput(
                    text = "최소인원",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "-",
                    style = DduruTheme.typography.Title2,
                    color = DduruTheme.colors.gray500
                )
                MatchingUnderlineInput(
                    text = "최대인원",
                    modifier = Modifier.weight(1f)
                )
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ParticipantQuickOptions.forEach { option ->
                    MatchingFilterSmallChip(
                        text = option,
                        selected = option == selectedParticipantOption,
                        onClick = { onParticipantOptionSelected(option) }
                    )
                }
            }
        }

        MatchingFilterSection {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "연령대",
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray800
                )
                Text(
                    text = if (selectedAgeStart != null && selectedAgeEnd != null) {
                        "$selectedAgeStart-${selectedAgeEnd}세"
                    } else {
                        "선택 안 함"
                    },
                    style = DduruTheme.typography.Title3,
                    color = if (selectedAgeStart != null && selectedAgeEnd != null) {
                        DduruTheme.colors.primary
                    } else {
                        DduruTheme.colors.gray400
                    },
                    textAlign = TextAlign.End
                )
            }

            AgeRangeBar(
                selectedStart = selectedAgeStart,
                selectedEnd = selectedAgeEnd,
                onRangeChanged = onAgeRangeChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }

        MatchingFilterSection(title = "동행 형태") {
            MatchingSegmentedOptionRow(
                options = MatchingCompanionType.entries.map { it.selectionLabel },
                selectedOption = selectedCompanionType?.selectionLabel,
                onOptionSelected = { label ->
                    MatchingCompanionType.entries.firstOrNull { it.selectionLabel == label }
                        ?.let(onCompanionTypeSelected)
                }
            )
        }
    }
}

@Composable
fun MatchingFilterSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (title != null) {
            Text(
                text = title,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800,
                modifier = Modifier.fillMaxWidth()
            )
        }
        content()
        MatchingFilterDivider()
    }
}

@Composable
private fun MatchingFilterDivider(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(DduruTheme.colors.gray200)
    )
}

@Composable
fun MatchingFilterFooter(
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DduruButton(
            text = "초기화",
            onClick = onResetClick,
            enabledColors = DduruButtonDefaults.whiteEnabledColors,
            disabledColors = DduruButtonDefaults.whiteDisabledColors,
            textStyle = DduruTheme.typography.Title2,
            modifier = Modifier.weight(1f)
        )

        DduruButton(
            text = "적용하기",
            onClick = onApplyClick,
            textStyle = DduruTheme.typography.Title2,
            modifier = Modifier.weight(1f)
        )
    }
}

package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
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
import kotlinx.coroutines.launch

private enum class MatchingFilterTab(val label: String) {
    DATE("일정"),
    DETAIL("상세")
}

private val DateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
private val RecruitmentStatusOptions = listOf("모집중", "마감임박", "모집 완료")
private val ParticipantQuickOptions = listOf("2명", "4명", "2-4명")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingFilterBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    fun dismissSheet() {
        scope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = DduruTheme.colors.gray0,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        MatchingFilterBottomSheetContent(
            onClose = ::dismissSheet,
            onApply = ::dismissSheet
        )
    }
}

@Composable
private fun MatchingFilterBottomSheetContent(
    onClose: () -> Unit,
    onApply: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(MatchingFilterTab.DETAIL) }
    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedRecruitmentStatus by remember { mutableStateOf<String?>(null) }
    var selectedGender by remember { mutableStateOf<MatchingGender?>(null) }
    var selectedParticipantOption by remember { mutableStateOf<String?>(null) }
    var selectedAgeStart by remember { mutableStateOf(20) }
    var selectedAgeEnd by remember { mutableStateOf(35) }
    var selectedCompanionType by remember { mutableStateOf<MatchingCompanionType?>(null) }

    fun resetFilters() {
        selectedStartDate = null
        selectedEndDate = null
        selectedRecruitmentStatus = null
        selectedGender = null
        selectedParticipantOption = null
        selectedAgeStart = 20
        selectedAgeEnd = 35
        selectedCompanionType = null
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp)
    ) {
        MatchingFilterHeader(onClose = onClose)

        MatchingFilterTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.padding(top = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                if (selectedTab == MatchingFilterTab.DATE) {
                    MatchingDateFilterContent(
                        selectedStartDate = selectedStartDate,
                        selectedEndDate = selectedEndDate,
                        onDateSelected = { date ->
                            if (selectedStartDate == null || selectedEndDate != null) {
                                selectedStartDate = date
                                selectedEndDate = null
                            } else if (date.isBefore(selectedStartDate)) {
                                selectedEndDate = selectedStartDate
                                selectedStartDate = date
                            } else {
                                selectedEndDate = date
                            }
                        }
                    )
                } else {
                    MatchingDetailFilterContent(
                        selectedRecruitmentStatus = selectedRecruitmentStatus,
                        onRecruitmentStatusSelected = {
                            selectedRecruitmentStatus = selectedRecruitmentStatus.toggle(it)
                        },
                        selectedGender = selectedGender,
                        onGenderSelected = {
                            selectedGender = selectedGender.toggle(it)
                        },
                        selectedParticipantOption = selectedParticipantOption,
                        onParticipantOptionSelected = {
                            selectedParticipantOption = selectedParticipantOption.toggle(it)
                        },
                        selectedAgeStart = selectedAgeStart,
                        selectedAgeEnd = selectedAgeEnd,
                        onAgeRangeChanged = { start, end ->
                            selectedAgeStart = start
                            selectedAgeEnd = end
                        },
                        selectedCompanionType = selectedCompanionType,
                        onCompanionTypeSelected = {
                            selectedCompanionType = selectedCompanionType.toggle(it)
                        }
                    )
                }
            }
        }

        MatchingFilterFooter(
            onResetClick = ::resetFilters,
            onApplyClick = onApply,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun MatchingFilterHeader(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "필터 설정",
            style = DduruTheme.typography.Title1,
            color = DduruTheme.colors.black
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_x_gray),
                contentDescription = "닫기",
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun MatchingFilterTabRow(
    selectedTab: MatchingFilterTab,
    onTabSelected: (MatchingFilterTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MatchingFilterPill(
            text = MatchingFilterTab.DATE.label,
            selected = selectedTab == MatchingFilterTab.DATE,
            onClick = { onTabSelected(MatchingFilterTab.DATE) }
        )
        MatchingFilterPill(
            text = MatchingFilterTab.DETAIL.label,
            selected = selectedTab == MatchingFilterTab.DETAIL,
            onClick = { onTabSelected(MatchingFilterTab.DETAIL) }
        )
    }
}

@Composable
private fun MatchingDateFilterContent(
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
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
private fun MatchingDetailFilterContent(
    selectedRecruitmentStatus: String?,
    onRecruitmentStatusSelected: (String) -> Unit,
    selectedGender: MatchingGender?,
    onGenderSelected: (MatchingGender) -> Unit,
    selectedParticipantOption: String?,
    onParticipantOptionSelected: (String) -> Unit,
    selectedAgeStart: Int,
    selectedAgeEnd: Int,
    onAgeRangeChanged: (Int, Int) -> Unit,
    selectedCompanionType: MatchingCompanionType?,
    onCompanionTypeSelected: (MatchingCompanionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MatchingFilterSection(title = "여행지") {
            MatchingDestinationField()
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
                    text = "$selectedAgeStart-${selectedAgeEnd}세",
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.primary,
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
private fun MatchingFilterSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
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
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(DduruTheme.colors.gray200)
    )
}

@Composable
private fun MatchingDestinationField(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray50)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "여행지 선택..",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray500
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_matching_searching),
            contentDescription = null,
            tint = DduruTheme.colors.gray500,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun MatchingSegmentedOptionRow(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            MatchingToggleButton(
                text = option,
                selected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MatchingToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) DduruTheme.colors.black else DduruTheme.colors.gray50)
            .border(
                width = 1.dp,
                color = if (selected) DduruTheme.colors.black else DduruTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Title2,
            color = if (selected) DduruTheme.colors.gray0 else DduruTheme.colors.gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MatchingFilterPill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(99.dp))
            .background(if (selected) DduruTheme.colors.black else DduruTheme.colors.gray50)
            .then(
                if (selected) {
                    Modifier
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray100,
                        shape = RoundedCornerShape(99.dp)
                    )
                }
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Body2,
            color = if (selected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
        )
    }
}

@Composable
private fun MatchingFilterSmallChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(if (selected) DduruTheme.colors.black else DduruTheme.colors.gray50)
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Caption1,
            color = if (selected) DduruTheme.colors.gray0 else DduruTheme.colors.gray500
        )
    }
}

@Composable
private fun MatchingUnderlineInput(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .border(
                width = 0.dp,
                color = DduruTheme.colors.gray100,
                shape = RoundedCornerShape(0.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray400,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(2.dp)
                .background(DduruTheme.colors.gray100)
        )
    }
}

@Composable
private fun MatchingFilterFooter(
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
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

private fun <T> T?.toggle(value: T): T? {
    return if (this == value) null else value
}

@Preview(showBackground = true)
@Composable
private fun MatchingFilterBottomSheetContentPreview() {
    DduruTheme {
        var visible by remember { mutableStateOf(true) }

        if (visible) {
            MatchingFilterBottomSheetContent(
                onClose = { visible = false },
                onApply = { visible = false },
                modifier = Modifier.background(DduruTheme.colors.gray0)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DduruTheme.colors.gray50)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                DduruButton(
                    text = "필터 다시 열기",
                    onClick = { visible = true }
                )
            }
        }
    }
}

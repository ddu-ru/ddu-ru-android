package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import java.time.LocalDate
import kotlinx.coroutines.launch

enum class MatchingFilterTab(val label: String) {
    DATE("일정"),
    DETAIL("상세")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingFilterBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    onDestinationClick: () -> Unit,
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
        modifier = modifier.padding(top = 106.dp),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = DduruTheme.colors.gray0,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        MatchingFilterBottomSheetContent(
            onClose = ::dismissSheet,
            onApply = ::dismissSheet,
            onDestinationClick = onDestinationClick
        )
    }
}

@Composable
private fun MatchingFilterBottomSheetContent(
    onClose: () -> Unit,
    onApply: () -> Unit,
    onDestinationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(MatchingFilterTab.DETAIL) }
    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedRecruitmentStatus by remember { mutableStateOf<String?>(null) }
    var selectedGender by remember { mutableStateOf<MatchingGender?>(null) }
    var selectedParticipantOption by remember { mutableStateOf<String?>(null) }
    var selectedAgeStart by remember { mutableStateOf<Int?>(null) }
    var selectedAgeEnd by remember { mutableStateOf<Int?>(null) }
    var selectedCompanionType by remember { mutableStateOf<MatchingCompanionType?>(null) }

    fun resetFilters() {
        selectedStartDate = null
        selectedEndDate = null
        selectedRecruitmentStatus = null
        selectedGender = null
        selectedParticipantOption = null
        selectedAgeStart = null
        selectedAgeEnd = null
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
                .weight(1f),
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
                        },
                        onDestinationClick = onDestinationClick
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
                onDestinationClick = {},
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

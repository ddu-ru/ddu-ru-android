package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleCategorySection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleComposeTopBar
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleDaySection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleMemoSection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleOptionSection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleTextField
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleTimeSection
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleCategory
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleComposeUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleDay
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyScheduleComposeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyScheduleComposeRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyScheduleComposeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var timePickerTarget by remember { mutableStateOf<TimePickerTarget?>(null) }
    val timePickerState = rememberTimePickerState(initialHour = 9, initialMinute = 0)

    JourneyScheduleComposeScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onTitleChange = viewModel::onTitleChange,
        onLocationChange = viewModel::onLocationChange,
        onDayClick = viewModel::onDayClick,
        onStartTimeClick = { timePickerTarget = TimePickerTarget.START },
        onAddEndTimeClick = {
            viewModel.onAddEndTimeClick()
            timePickerTarget = TimePickerTarget.END
        },
        onEndTimeClick = { timePickerTarget = TimePickerTarget.END },
        onMemoOptionClick = viewModel::onMemoOptionClick,
        onMemoChange = viewModel::onMemoChange,
        onRemoveMemoClick = viewModel::onRemoveMemoClick,
        onCategoryOptionClick = viewModel::onCategoryOptionClick,
        onCategoryClick = viewModel::onCategoryClick,
        onRemoveCategoryClick = viewModel::onRemoveCategoryClick,
        onSubmitClick = viewModel::onSubmitClick,
        modifier = modifier
    )

    if (timePickerTarget != null) {
        AlertDialog(
            onDismissRequest = { timePickerTarget = null },
            title = { Text(if (timePickerTarget == TimePickerTarget.START) "시작 시간" else "종료 시간") },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                Button(
                    onClick = {
                        val time = timePickerState.hour.toKoreanTime(timePickerState.minute)
                        if (timePickerTarget == TimePickerTarget.START) {
                            viewModel.onStartTimeChange(time)
                        } else {
                            viewModel.onEndTimeChange(time)
                        }
                        timePickerTarget = null
                    }
                ) { Text("확인") }
            },
            dismissButton = {
                Button(onClick = { timePickerTarget = null }) { Text("취소") }
            }
        )
    }
}

@Composable
fun JourneyScheduleComposeScreen(
    uiState: JourneyScheduleComposeUiState,
    onBackClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onDayClick: (Int) -> Unit,
    onStartTimeClick: () -> Unit,
    onAddEndTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit,
    onMemoOptionClick: () -> Unit,
    onMemoChange: (String) -> Unit,
    onRemoveMemoClick: () -> Unit,
    onCategoryOptionClick: () -> Unit,
    onCategoryClick: (JourneyScheduleCategory) -> Unit,
    onRemoveCategoryClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        JourneyScheduleComposeTopBar(
            enabled = uiState.canSubmit,
            onBackClick = onBackClick,
            onSubmitClick = onSubmitClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        JourneyScheduleTextField(
            label = "제목",
            value = uiState.title,
            placeholder = "예: 시부야 스크램블 집합",
            onValueChange = onTitleChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        JourneyScheduleTextField(
            label = "장소",
            value = uiState.location,
            placeholder = "장소 검색 혹은 직접 입력",
            onValueChange = onLocationChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        JourneyScheduleDaySection(
            days = uiState.days,
            selectedDay = uiState.selectedDay,
            onDayClick = onDayClick
        )

        Spacer(modifier = Modifier.height(28.dp))

        JourneyScheduleTimeSection(
            startTime = uiState.startTime,
            endTime = uiState.endTime,
            isEndTimeAdded = uiState.isEndTimeAdded,
            onStartTimeClick = onStartTimeClick,
            onAddEndTimeClick = onAddEndTimeClick,
            onEndTimeClick = onEndTimeClick
        )

        if (uiState.isMemoAdded) {
            Spacer(modifier = Modifier.height(28.dp))

            JourneyScheduleMemoSection(
                memo = uiState.memo,
                onMemoChange = onMemoChange,
                onRemoveClick = onRemoveMemoClick
            )
        }

        if (uiState.isCategoryAdded) {
            Spacer(modifier = Modifier.height(28.dp))

            JourneyScheduleCategorySection(
                selectedCategory = uiState.selectedCategory,
                onCategoryClick = onCategoryClick,
                onRemoveClick = onRemoveCategoryClick
            )
        }

        if (!uiState.isMemoAdded || !uiState.isCategoryAdded) {
            Spacer(modifier = Modifier.height(28.dp))

            JourneyScheduleOptionSection(
                showMemoOption = !uiState.isMemoAdded,
                showCategoryOption = !uiState.isCategoryAdded,
                onMemoClick = onMemoOptionClick,
                onCategoryClick = onCategoryOptionClick
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(name = "일정 생성", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun JourneyScheduleComposeScreenPreview() = DduruTheme {
    JourneyScheduleComposeScreen(
        uiState = JourneyScheduleComposeUiState(
            title = "시부야 스크램블 집합",
            location = "시부야 스크램블 교차로",
            days = listOf(
                JourneyScheduleDay(1, "05.12"),
                JourneyScheduleDay(2, "05.13"),
                JourneyScheduleDay(3, "05.14")
            ),
            selectedDay = 2,
            startTime = "오전 10:00",
            endTime = "오후 12:00",
            isEndTimeAdded = true,
            isMemoAdded = true,
            memo = "늦지 않게 도착하기",
            isCategoryAdded = true,
            selectedCategory = JourneyScheduleCategory.TOUR
        ),
        onBackClick = {},
        onTitleChange = {},
        onLocationChange = {},
        onDayClick = {},
        onStartTimeClick = {},
        onAddEndTimeClick = {},
        onEndTimeClick = {},
        onMemoOptionClick = {},
        onMemoChange = {},
        onRemoveMemoClick = {},
        onCategoryOptionClick = {},
        onCategoryClick = {},
        onRemoveCategoryClick = {},
        onSubmitClick = {}
    )
}

private enum class TimePickerTarget { START, END }

private fun Int.toKoreanTime(minute: Int): String {
    val period = if (this < 12) "오전" else "오후"
    val hour = if (this % 12 == 0) 12 else this % 12
    return "$period $hour:${minute.toString().padStart(2, '0')}"
}

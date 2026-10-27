package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleTimePicker
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleTimeSection
import com.gildongmu.dduru.presentation.feature.journey.component.TimePickerTarget
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleCategory
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleComposeUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleDay
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyScheduleComposeViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JourneyScheduleComposeRoute(
    journeyId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyScheduleComposeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(journeyId) { viewModel.loadJourneyDays(journeyId) }
    var timePickerTarget by remember { mutableStateOf<TimePickerTarget?>(null) }
    val selectedTime = when (timePickerTarget) {
        TimePickerTarget.START -> uiState.startTime
        TimePickerTarget.END -> uiState.endTime
        null -> null
    }

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
        onSubmitClick = { viewModel.onSubmitClick(journeyId, onBackClick) },
        modifier = modifier
    )

    JourneyScheduleTimePicker(
        target = timePickerTarget,
        selectedTime = selectedTime,
        minimumTime = uiState.startTime,
        onTimeSelected = { time ->
            when (timePickerTarget) {
                TimePickerTarget.START -> viewModel.onStartTimeChange(time)
                TimePickerTarget.END -> viewModel.onEndTimeChange(time)
                null -> Unit
            }
            timePickerTarget = null
        },
        onInvalidTime = viewModel::onTimeValidationFailed,
        onDismiss = { timePickerTarget = null }
    )
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
        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(32.dp))

        JourneyScheduleTextField(
            label = "장소",
            value = uiState.location,
            placeholder = "장소 검색 혹은 직접 입력",
            onValueChange = onLocationChange
        )

        Spacer(modifier = Modifier.height(32.dp))

        JourneyScheduleDaySection(
            days = uiState.days,
            selectedDay = uiState.selectedDay,
            onDayClick = onDayClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        JourneyScheduleTimeSection(
            startTime = uiState.startTime,
            endTime = uiState.endTime,
            isEndTimeAdded = uiState.isEndTimeAdded,
            errorMessage = uiState.timeErrorMessage,
            onStartTimeClick = onStartTimeClick,
            onAddEndTimeClick = onAddEndTimeClick,
            onEndTimeClick = onEndTimeClick
        )

        if (uiState.isMemoAdded) {
            Spacer(modifier = Modifier.height(32.dp))

            JourneyScheduleMemoSection(
                memo = uiState.memo,
                onMemoChange = onMemoChange,
                onRemoveClick = onRemoveMemoClick
            )
        }

        if (uiState.isCategoryAdded) {
            Spacer(modifier = Modifier.height(32.dp))

            JourneyScheduleCategorySection(
                selectedCategory = uiState.selectedCategory,
                onCategoryClick = onCategoryClick,
                onRemoveClick = onRemoveCategoryClick
            )
        }

        if (!uiState.isMemoAdded || !uiState.isCategoryAdded) {
            Spacer(modifier = Modifier.height(32.dp))

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
    JourneyScheduleComposePreviewContent(
        uiState = JourneyScheduleComposeUiState(
            title = "시부야 스크램블 집합",
            location = "시부야 스크램블 교차로",
            days = persistentListOf(
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
        )
    )
}

@Preview(name = "일정 생성 - 초기 화면", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun JourneyScheduleComposeInitialScreenPreview() = DduruTheme {
    JourneyScheduleComposePreviewContent(
        uiState = JourneyScheduleComposeUiState()
    )
}

@Composable
private fun JourneyScheduleComposePreviewContent(uiState: JourneyScheduleComposeUiState) {
    JourneyScheduleComposeScreen(
        uiState = uiState,
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

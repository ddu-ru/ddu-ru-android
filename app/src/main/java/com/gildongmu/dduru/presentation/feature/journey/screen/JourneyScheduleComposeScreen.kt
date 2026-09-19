package com.gildongmu.dduru.presentation.feature.journey.screen

import android.app.TimePickerDialog
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.toKoreanTime
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

@Composable
fun JourneyScheduleComposeRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyScheduleComposeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var timePickerTarget by remember { mutableStateOf<TimePickerTarget?>(null) }

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

    JourneyScheduleTimePicker(
        target = timePickerTarget,
        selectedTime = if (timePickerTarget == TimePickerTarget.START) uiState.startTime else uiState.endTime,
        minimumTime = uiState.startTime,
        onTimeSelected = { time ->
            if (timePickerTarget == TimePickerTarget.START) {
                viewModel.onStartTimeChange(time)
            } else {
                viewModel.onEndTimeChange(time)
            }
            timePickerTarget = null
        },
        onInvalidTime = viewModel::onTimeValidationFailed,
        onDismiss = { timePickerTarget = null }
    )
}

@Composable
private fun JourneyScheduleTimePicker(
    target: TimePickerTarget?,
    selectedTime: String?,
    minimumTime: String?,
    onTimeSelected: (String) -> Unit,
    onInvalidTime: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (target == null) return

    val context = LocalContext.current
    DisposableEffect(target) {
        val minimumMinutes = if (target == TimePickerTarget.END) {
            minimumTime?.toHourAndMinute()?.toMinutes()
        } else {
            null
        }
        if (minimumMinutes == LAST_TIME_MINUTES) {
            onInvalidTime("시작 시간이 오후 11:59이면 종료 시간을 설정할 수 없어요.")
            onDismiss()
            onDispose { }
        } else {
            val selectedMinutes = selectedTime.toHourAndMinute().toMinutes()
            val initialMinutes = if (target == TimePickerTarget.END && minimumMinutes != null) {
                maxOf(selectedMinutes, minimumMinutes + 1)
            } else {
                selectedMinutes
            }
            val dialog = TimePickerDialog(
                context,
                R.style.Theme_Dduru_TimePickerSpinner,
                { _, selectedHour, selectedMinute ->
                    val selectedTimeMinutes = (selectedHour to selectedMinute).toMinutes()
                    if (minimumMinutes != null && selectedTimeMinutes <= minimumMinutes) {
                        onInvalidTime("종료 시간은 시작 시간 이후로 설정해 주세요.")
                    } else {
                        onTimeSelected(selectedHour.toKoreanTime(selectedMinute))
                    }
                },
                initialMinutes / MINUTES_PER_HOUR,
                initialMinutes % MINUTES_PER_HOUR,
                DateFormat.is24HourFormat(context)
            ).apply {
                setOnDismissListener { onDismiss() }
                show()
            }

            onDispose { dialog.dismiss() }
        }
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
        initialUiState = JourneyScheduleComposeUiState(
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
        )
    )
}

@Preview(name = "일정 생성 - 초기 화면", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun JourneyScheduleComposeInitialScreenPreview() = DduruTheme {
    JourneyScheduleComposePreviewContent(
        initialUiState = JourneyScheduleComposeUiState()
    )
}

private enum class TimePickerTarget { START, END }

@Composable
private fun JourneyScheduleComposePreviewContent(initialUiState: JourneyScheduleComposeUiState) {
    var uiState by remember { mutableStateOf(initialUiState) }
    var timePickerTarget by remember { mutableStateOf<TimePickerTarget?>(null) }

    JourneyScheduleComposeScreen(
        uiState = uiState,
        onBackClick = {},
        onTitleChange = { uiState = uiState.copy(title = it) },
        onLocationChange = { uiState = uiState.copy(location = it) },
        onDayClick = { uiState = uiState.copy(selectedDay = it) },
        onStartTimeClick = { timePickerTarget = TimePickerTarget.START },
        onAddEndTimeClick = {
            uiState = uiState.copy(isEndTimeAdded = true)
            timePickerTarget = TimePickerTarget.END
        },
        onEndTimeClick = { timePickerTarget = TimePickerTarget.END },
        onMemoOptionClick = { uiState = uiState.copy(isMemoAdded = true) },
        onMemoChange = { uiState = uiState.copy(memo = it) },
        onRemoveMemoClick = { uiState = uiState.copy(isMemoAdded = false, memo = "") },
        onCategoryOptionClick = { uiState = uiState.copy(isCategoryAdded = true) },
        onCategoryClick = { uiState = uiState.copy(selectedCategory = it) },
        onRemoveCategoryClick = { uiState = uiState.copy(isCategoryAdded = false, selectedCategory = null) },
        onSubmitClick = {}
    )

    JourneyScheduleTimePicker(
        target = timePickerTarget,
        selectedTime = if (timePickerTarget == TimePickerTarget.START) uiState.startTime else uiState.endTime,
        minimumTime = uiState.startTime,
        onTimeSelected = { time ->
            uiState = if (timePickerTarget == TimePickerTarget.START) {
                uiState.copy(startTime = time, timeErrorMessage = null)
            } else {
                uiState.copy(endTime = time, timeErrorMessage = null)
            }
            timePickerTarget = null
        },
        onInvalidTime = { uiState = uiState.copy(timeErrorMessage = it) },
        onDismiss = { timePickerTarget = null }
    )
}

private fun String?.toHourAndMinute(): Pair<Int, Int> {
    val time = this ?: return DEFAULT_TIME
    val match = KOREAN_TIME_REGEX.matchEntire(time) ?: return DEFAULT_TIME
    val hour = match.groupValues[2].toInt()
    val minute = match.groupValues[3].toInt()
    val hour24 = when (match.groupValues[1]) {
        "오전" -> if (hour == 12) 0 else hour
        else -> if (hour == 12) 12 else hour + 12
    }
    return hour24 to minute
}

private fun Pair<Int, Int>.toMinutes(): Int = first * MINUTES_PER_HOUR + second

private val KOREAN_TIME_REGEX = Regex("(오전|오후) (\\d{1,2}):(\\d{2})")
private val DEFAULT_TIME = 9 to 0
private const val MINUTES_PER_HOUR = 60
private const val LAST_TIME_MINUTES = 23 * MINUTES_PER_HOUR + 59

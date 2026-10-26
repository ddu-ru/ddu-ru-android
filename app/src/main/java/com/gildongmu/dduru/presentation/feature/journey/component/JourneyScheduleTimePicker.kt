package com.gildongmu.dduru.presentation.feature.journey.component

import android.app.TimePickerDialog
import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.toKoreanTime

enum class TimePickerTarget { START, END }

@Composable
fun JourneyScheduleTimePicker(
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
        val minimumMinutes = target.minimumTimeInMinutes(minimumTime)
        if (minimumMinutes == LAST_TIME_MINUTES) {
            onInvalidTime(LAST_START_TIME_MESSAGE)
            onDismiss()
            onDispose { }
        } else {
            val initialMinutes = selectedTime.initialMinutes(minimumMinutes)
            val dialog = TimePickerDialog(
                context,
                R.style.Theme_Dduru_TimePickerSpinner,
                { _, selectedHour, selectedMinute ->
                    val selectedMinutes = (selectedHour to selectedMinute).toMinutes()
                    if (minimumMinutes != null && selectedMinutes <= minimumMinutes) {
                        onInvalidTime(INVALID_END_TIME_MESSAGE)
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

private fun TimePickerTarget.minimumTimeInMinutes(minimumTime: String?): Int? =
    if (this == TimePickerTarget.END) minimumTime?.toHourAndMinute()?.toMinutes() else null

private fun String?.initialMinutes(minimumMinutes: Int?): Int {
    val selectedMinutes = toHourAndMinute().toMinutes()
    return if (minimumMinutes == null) selectedMinutes else maxOf(selectedMinutes, minimumMinutes + 1)
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
private const val LAST_START_TIME_MESSAGE = "시작 시간이 오후 11:59이면 종료 시간을 설정할 수 없어요."
private const val INVALID_END_TIME_MESSAGE = "종료 시간은 시작 시간 이후로 설정해 주세요."

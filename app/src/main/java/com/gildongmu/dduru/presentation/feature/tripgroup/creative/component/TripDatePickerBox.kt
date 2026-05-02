package com.gildongmu.dduru.presentation.feature.tripgroup.creative.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TripDatePickerBox(
    label: String,
    selectedDate: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(163.dp)
            .height(61.dp)
            .background(
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(12.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.width(139.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                style = DduruTheme.typography.Micro2,
                color = DduruTheme.colors.gray500
            )
            Text(
                text = selectedDate ?: "선택해주세요",
                style = DduruTheme.typography.Body2,
                color = if (selectedDate != null) {
                    DduruTheme.colors.gray868
                } else {
                    DduruTheme.colors.gray400
                }
            )
        }
    }
}

@Preview(name = "TripDatePickerBox - 날짜 미선택")
@Composable
private fun TripDatePickerBoxEmptyPreview() {
    DduruTheme {
        TripDatePickerBox(
            label = "출발 날짜",
            selectedDate = null,
            onClick = {}
        )
    }
}

@Preview(name = "TripDatePickerBox - 날짜 선택됨")
@Composable
private fun TripDatePickerBoxSelectedPreview() {
    DduruTheme {
        TripDatePickerBox(
            label = "출발 날짜",
            selectedDate = "2025년 10월 06일",
            onClick = {}
        )
    }
}

@Preview(name = "TripDatePickerBox - 도착 날짜")
@Composable
private fun TripDatePickerBoxArrivalPreview() {
    DduruTheme {
        TripDatePickerBox(
            label = "도착 날짜",
            selectedDate = "2025년 10월 10일",
            onClick = {}
        )
    }
}

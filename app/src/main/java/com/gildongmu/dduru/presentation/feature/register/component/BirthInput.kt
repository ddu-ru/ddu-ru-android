package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BirthInputRow(
    year: Int?,
    month: Int?,
    day: Int?,
    years: List<Int>,
    months: List<Int>,
    days: List<Int>,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    onDaySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "년",
            selectedValue = year,
            items = years,
            onItemSelected = onYearSelected,
        )
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "월",
            selectedValue = month,
            items = months,
            onItemSelected = onMonthSelected,
        )
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "일",
            selectedValue = day,
            items = days,
            onItemSelected = onDaySelected,
        )
    }
}

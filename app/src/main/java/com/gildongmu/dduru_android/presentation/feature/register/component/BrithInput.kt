package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.presentation.feature.register.viewmodel.SignupViewModel
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupUiState

@Composable
fun BirthInputRow(
    state: SignupUiState,
    viewModel: SignupViewModel,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "년",
            selectedValue = state.year,
            items = viewModel.years,
            onItemSelected = viewModel::onYearSelected,
            enabled = enabled
        )
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "월",
            selectedValue = state.month,
            items = viewModel.months,
            onItemSelected = viewModel::onMonthSelected,
            enabled = enabled
        )
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "일",
            selectedValue = state.day,
            items = viewModel.days,
            onItemSelected = viewModel::onDaySelected,
            enabled = enabled
        )
    }
}

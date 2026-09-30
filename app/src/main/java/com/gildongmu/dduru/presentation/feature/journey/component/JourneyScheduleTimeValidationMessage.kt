package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyScheduleTimeValidationMessage(
    message: String?,
    modifier: Modifier = Modifier
) {
    if (message == null) return

    Text(
        text = message,
        modifier = modifier.padding(top = 4.dp),
        style = DduruTheme.typography.Caption2,
        color = DduruTheme.colors.validationRed
    )
}

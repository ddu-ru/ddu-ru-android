package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults.filledDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults.filledEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.state.Gender

@Composable
fun GenderSegment(
    selected: Gender?,
    onSelect: (Gender) -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val isMaleSelected = selected == Gender.MALE
        DduruButton(
            modifier = Modifier.weight(1f),
            text = "남자",
            enabled = enabled,
            onClick = { onSelect(Gender.MALE) },
            enabledColors = filledEnabledColors,
            disabledColors = filledDisabledColors,
        )

        val isFemaleSelected = selected == Gender.FEMALE
        DduruButton(
            modifier = Modifier.weight(1f),
            text = "여자",
            enabled = enabled,
            onClick = { onSelect(Gender.FEMALE) },
            enabledColors = filledEnabledColors,
            disabledColors = filledDisabledColors,
        )
    }
}

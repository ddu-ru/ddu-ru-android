package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults.filledEnabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults.whiteEnabledColors
import com.gildongmu.dduru.presentation.feature.register.state.Gender

@Composable
fun GenderSegment(
    selected: Gender?,
    onSelect: (Gender) -> Unit,
    enabled: Boolean
) {
    val maleSelected = selected == Gender.MALE
    val femaleSelected = selected == Gender.FEMALE

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DduruButton(
            modifier = Modifier.weight(1f),
            text = "남자",
            enabled = enabled,
            onClick = { onSelect(Gender.MALE) },
            enabledColors = if (maleSelected) {
                filledEnabledColors
            } else {
                whiteEnabledColors
            }
        )

        DduruButton(
            modifier = Modifier.weight(1f),
            text = "여자",
            enabled = enabled,
            onClick = { onSelect(Gender.FEMALE) },
            enabledColors = if (femaleSelected) {
                filledEnabledColors
            } else {
                whiteEnabledColors
            }
        )
    }
}

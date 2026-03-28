package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ValidationMessage(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DduruTheme.colors.validationRed
) {
    Text(
        text = text,
        modifier = modifier.padding(top = 4.dp),
        style = DduruTheme.typography.Caption2,
        color = color
    )
}

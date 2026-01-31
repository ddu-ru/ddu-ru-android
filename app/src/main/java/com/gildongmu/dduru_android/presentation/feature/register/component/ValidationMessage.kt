package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun ValidationMessage(
    modifier: Modifier,
    text : String,
    color: Color = DduruTheme.colors.ValidationRed,
){
    Text(
        text = text,
        modifier = modifier,
        style = DduruTheme.typography.BodySmall,
        color = color,
    )
}

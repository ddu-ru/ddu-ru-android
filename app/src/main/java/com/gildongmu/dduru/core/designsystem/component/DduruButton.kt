package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    cornerShape: Dp = 12.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(height)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(cornerShape),
                spotColor = DduruTheme.colors.BlackAlpha14,
                ambientColor = DduruTheme.colors.BlackAlpha14
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.WhiteAlpha30,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = DduruTheme.colors.Primary
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.ButtonLarge,
            color = DduruTheme.colors.Gray0
        )
    }
}

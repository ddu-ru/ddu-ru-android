package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = DduruTheme.colors.Primary,
        contentColor = Color.White,
        disabledContainerColor = DduruTheme.colors.Primary.copy(alpha = 0.3f), // 비활성 색상 보완
        disabledContentColor = Color.White.copy(alpha = 0.7f)
    ),
    cornerSize: Dp = 12.dp,
    borderColor: Color = DduruTheme.colors.WhiteAlpha30
) {
    val commonShape = RoundedCornerShape(cornerSize)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp)
            .shadow(
                elevation = 12.dp,
                shape = commonShape,
                spotColor = DduruTheme.colors.BlackAlpha14,
                ambientColor = DduruTheme.colors.BlackAlpha14
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = commonShape
            ),
        colors = colors,
        shape = commonShape,
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Button
        )
    }
}

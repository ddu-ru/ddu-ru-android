package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class DduruButtonStyle {
    FILLED, //파란색 채워진 버튼
    OUTLINED //회색 테두리 버튼
}

@Composable
fun DduruButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: DduruButtonStyle = DduruButtonStyle.FILLED,
    textStyle: TextStyle = DduruTheme.typography.Title1,
    iconRes: Int? = null,
    enabled: Boolean = true,
    cornerRadius: Dp = 16.dp,
    height: Dp = 48.dp
) {
    val backgroundColor = when (style) {
        DduruButtonStyle.FILLED -> DduruTheme.colors.primary
        DduruButtonStyle.OUTLINED -> DduruTheme.colors.grayAlpha30
    }

    val textColor = when (style) {
        DduruButtonStyle.FILLED -> DduruTheme.colors.gray0
        DduruButtonStyle.OUTLINED -> DduruTheme.colors.gray200
    }

    val borderColor = when (style) {
        DduruButtonStyle.FILLED -> DduruTheme.colors.whiteAlpha30
        DduruButtonStyle.OUTLINED -> DduruTheme.colors.grayBorderAlpha10
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(height)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(cornerRadius),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 13.dp),
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconRes != null) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = text,
                style = textStyle,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

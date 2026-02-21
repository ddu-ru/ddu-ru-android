package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class DduruButtonStyle {
    FILLED,
    OUTLINED
}

@Composable
fun DduruButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: DduruButtonStyle = DduruButtonStyle.FILLED,
    textStyle: TextStyle = DduruTheme.typography.Title1,
    iconRes: Int? = null,
    enabled: Boolean = true
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
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(16.dp),
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
                    modifier = Modifier.padding(4.dp)
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

@Preview(showBackground = true)
@Composable
private fun DduruButtonPreview() {
    DduruTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DduruButton(
                text = "네, 좋아요!",
                onClick = {},
                textStyle = DduruTheme.typography.H1,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "패스",
                    onClick = {},
                    style = DduruButtonStyle.OUTLINED,
                    iconRes = R.drawable.ic_heart_rounded,
                    modifier = Modifier.weight(1f)
                )

                DduruButton(
                    text = "함께할래요",
                    onClick = {},
                    iconRes = R.drawable.ic_heart_rounded,
                    modifier = Modifier.weight(1f)
                )
            }

            DduruButton(
                text = "코인 샵으로 이동",
                onClick = {},
                iconRes = R.drawable.ic_heart_rounded,
                modifier = Modifier.fillMaxWidth()
            )

            DduruButton(
                text = "아이콘 없음",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

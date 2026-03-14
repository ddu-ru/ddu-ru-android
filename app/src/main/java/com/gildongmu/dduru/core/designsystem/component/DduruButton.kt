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

data class DduruButtonEnabledColors(
    val backgroundColor: Color,
    val textColor: Color,
    val borderColor: Color
)

data class DduruButtonDisabledColors(
    val backgroundColor: Color,
    val textColor: Color,
    val borderColor: Color
)

@Composable
fun DduruButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabledColors: DduruButtonEnabledColors = DduruButtonEnabledColors(
        backgroundColor = DduruTheme.colors.primary,
        textColor = DduruTheme.colors.gray0,
        borderColor = DduruTheme.colors.gray0.copy(alpha = 0.3f)
    ),
    disabledColors: DduruButtonDisabledColors = DduruButtonDisabledColors(
        backgroundColor = DduruTheme.colors.primary.copy(alpha = 0.5f),
        textColor = DduruTheme.colors.gray0.copy(alpha = 0.5f),
        borderColor = DduruTheme.colors.gray0.copy(alpha = 0.15f)
    ),
    textStyle: TextStyle = DduruTheme.typography.Title1,
    iconRes: Int? = null,
    enabled: Boolean = true
) {
    val backgroundColor = if (enabled) enabledColors.backgroundColor else disabledColors.backgroundColor
    val textColor = if (enabled) enabledColors.textColor else disabledColors.textColor
    val borderColor = if (enabled) enabledColors.borderColor else disabledColors.borderColor

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
            disabledContainerColor = disabledColors.backgroundColor
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

object DduruButtonDefaults {
    val filledEnabledColors: DduruButtonEnabledColors
        @Composable
        get() = DduruButtonEnabledColors(
            backgroundColor = DduruTheme.colors.primary,
            textColor = DduruTheme.colors.gray0,
            borderColor = DduruTheme.colors.gray0.copy(alpha = 0.3f)
        )

    val filledDisabledColors: DduruButtonDisabledColors
        @Composable
        get() = DduruButtonDisabledColors(
            backgroundColor = DduruTheme.colors.primary.copy(alpha = 0.5f),
            textColor = DduruTheme.colors.gray0.copy(alpha = 0.5f),
            borderColor = DduruTheme.colors.gray0.copy(alpha = 0.15f)
        )

    val outlinedEnabledColors: DduruButtonEnabledColors
        @Composable
        get() = DduruButtonEnabledColors(
            backgroundColor = DduruTheme.colors.grayNeutral.copy(alpha = 0.3f),
            textColor = DduruTheme.colors.gray200,
            borderColor = DduruTheme.colors.grayBorder.copy(alpha = 0.1f)
        )

    val outlinedDisabledColors: DduruButtonDisabledColors
        @Composable
        get() = DduruButtonDisabledColors(
            backgroundColor = DduruTheme.colors.grayNeutral.copy(alpha = 0.15f),
            textColor = DduruTheme.colors.gray200.copy(alpha = 0.5f),
            borderColor = DduruTheme.colors.grayBorder.copy(alpha = 0.05f)
        )

    val whiteEnabledColors: DduruButtonEnabledColors
        @Composable
        get() = DduruButtonEnabledColors(
            backgroundColor = DduruTheme.colors.gray0,
            textColor = DduruTheme.colors.gray700,
            borderColor = DduruTheme.colors.grayBorder.copy(alpha = 0.1f)
        )

    val whiteDisabledColors: DduruButtonDisabledColors
        @Composable
        get() = DduruButtonDisabledColors(
            backgroundColor = DduruTheme.colors.gray200,
            textColor = DduruTheme.colors.gray400,
            borderColor = DduruTheme.colors.gray300
        )
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
                    enabledColors = DduruButtonDefaults.outlinedEnabledColors,
                    disabledColors = DduruButtonDefaults.outlinedDisabledColors,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "여자",
                    onClick = {},
                    enabledColors = DduruButtonDefaults.whiteEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    enabled = true,
                    modifier = Modifier.weight(1f)
                )

                DduruButton(
                    text = "남자",
                    onClick = {},
                    enabledColors = DduruButtonDefaults.whiteEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            DduruButton(
                text = "인증완료",
                onClick = {},
                enabledColors = DduruButtonDefaults.whiteEnabledColors,
                disabledColors = DduruButtonDefaults.whiteDisabledColors,
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

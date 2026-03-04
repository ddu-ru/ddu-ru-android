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
    enabledColors: DduruButtonEnabledColors = dduruButtonFilledEnabledColors(),
    disabledColors: DduruButtonDisabledColors = dduruButtonFilledDisabledColors(),
    textStyle: TextStyle = DduruTheme.typography.Title1,
    iconRes: Int? = null,
    enabled: Boolean = true
) {
    val currentBackgroundColor = if (enabled) enabledColors.backgroundColor else disabledColors.backgroundColor
    val currentTextColor = if (enabled) enabledColors.textColor else disabledColors.textColor
    val currentBorderColor = if (enabled) enabledColors.borderColor else disabledColors.borderColor

    Button(
        onClick = onClick,
        modifier = modifier
            .border(
                width = 1.dp,
                color = currentBorderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = currentBackgroundColor,
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
                color = currentTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun dduruButtonFilledEnabledColors() = DduruButtonEnabledColors(
    backgroundColor = DduruTheme.colors.primary,
    textColor = DduruTheme.colors.gray0,
    borderColor = DduruTheme.colors.whiteAlpha30
)

@Composable
fun dduruButtonFilledDisabledColors() = DduruButtonDisabledColors(
    backgroundColor = DduruTheme.colors.primary.copy(alpha = 0.5f),
    textColor = DduruTheme.colors.gray0.copy(alpha = 0.5f),
    borderColor = DduruTheme.colors.whiteAlpha30.copy(alpha = 0.5f)
)

@Composable
fun dduruButtonOutlinedEnabledColors() = DduruButtonEnabledColors(
    backgroundColor = DduruTheme.colors.grayAlpha30,
    textColor = DduruTheme.colors.gray200,
    borderColor = DduruTheme.colors.grayBorderAlpha10
)

@Composable
fun dduruButtonOutlinedDisabledColors() = DduruButtonDisabledColors(
    backgroundColor = DduruTheme.colors.grayAlpha30.copy(alpha = 0.5f),
    textColor = DduruTheme.colors.gray200.copy(alpha = 0.5f),
    borderColor = DduruTheme.colors.grayBorderAlpha10.copy(alpha = 0.5f)
)

@Composable
fun dduruButtonWhiteEnabledColors() = DduruButtonEnabledColors(
    backgroundColor = DduruTheme.colors.gray0,
    textColor = DduruTheme.colors.gray700,
    borderColor = DduruTheme.colors.grayBorderAlpha10
)

@Composable
fun dduruButtonWhiteDisabledColors() = DduruButtonDisabledColors(
    backgroundColor = DduruTheme.colors.gray200,
    textColor = DduruTheme.colors.gray400,
    borderColor = DduruTheme.colors.gray300
)

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
                    enabledColors = dduruButtonOutlinedEnabledColors(),
                    disabledColors = dduruButtonOutlinedDisabledColors(),
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
                    enabledColors = dduruButtonWhiteEnabledColors(),
                    disabledColors = dduruButtonWhiteDisabledColors(),
                    enabled = true,
                    modifier = Modifier.weight(1f)
                )

                DduruButton(
                    text = "남자",
                    onClick = {},
                    enabledColors = dduruButtonWhiteEnabledColors(),
                    disabledColors = dduruButtonWhiteDisabledColors(),
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            DduruButton(
                text = "인증완료",
                onClick = {},
                enabledColors = dduruButtonWhiteEnabledColors(),
                disabledColors = dduruButtonWhiteDisabledColors(),
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruConfirmDialog(
    title: String,
    description: String,
    firstButtonText: String,
    secondButtonText: String,
    onDismiss: () -> Unit,
    onFirstButtonClick: () -> Unit = onDismiss,
    onSecondButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = DduruTheme.typography.Title1,
    descriptionTextStyle: TextStyle = DduruTheme.typography.Body2,
    firstButtonTextStyle: TextStyle = DduruTheme.typography.Title1,
    secondButtonTextStyle: TextStyle = DduruTheme.typography.Title1,
    titleColor: Color = DduruTheme.colors.gray900,
    descriptionColor: Color = DduruTheme.colors.gray600,
    firstButtonBackgroundColor: Color = DduruTheme.colors.gray0,
    firstButtonTextColor: Color = DduruTheme.colors.gray700,
    secondButtonBackgroundColor: Color = DduruTheme.colors.primary,
    secondButtonTextColor: Color = DduruTheme.colors.gray0,
    cornerRadius: Dp = 16.dp
) {
    val shape = RoundedCornerShape(cornerRadius)
    Dialog(onDismissRequest = onDismiss) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier.dduruDualShadow(0.18f, 0.18f, shape, firstElevation = 20.dp)
                .width(335.dp).background(DduruTheme.colors.gray0, shape).padding(24.dp, 32.dp, 24.dp, 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(title, style = titleTextStyle, color = titleColor)
                Text(description, style = descriptionTextStyle, color = descriptionColor)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                DialogButton(
                    firstButtonText,
                    firstButtonTextStyle,
                    firstButtonBackgroundColor,
                    firstButtonTextColor,
                    cornerRadius,
                    onFirstButtonClick,
                    Modifier.weight(1f)
                )
                DialogButton(
                    secondButtonText,
                    secondButtonTextStyle,
                    secondButtonBackgroundColor,
                    secondButtonTextColor,
                    cornerRadius,
                    onSecondButtonClick,
                    Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    text: String,
    textStyle: TextStyle,
    background: Color,
    textColor: Color,
    radius: Dp,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val shape = RoundedCornerShape(radius)
    Row(
        modifier = modifier
            .height(48.dp)
            .border(1.dp, DduruTheme.colors.gray200, shape)
            .background(background, shape)
            .noRippleClickable(onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = textStyle, color = textColor)
    }
}

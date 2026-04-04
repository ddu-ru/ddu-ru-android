package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun SurveyOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    outerVerticalPadding: Dp = 8.dp,
    innerVerticalPadding: Dp = 24.dp,
    innerHorizontalPadding: Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = outerVerticalPadding)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray200,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .background(
                color = if (isSelected) {
                    DduruTheme.colors.secondary.copy(alpha = 0.3f)
                } else {
                    DduruTheme.colors.gray0
                },
                shape = RoundedCornerShape(size = 16.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(vertical = innerVerticalPadding, horizontal = innerHorizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Title1,
            color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray700,
            textAlign = TextAlign.Center,
            maxLines = maxLines
        )
    }
}

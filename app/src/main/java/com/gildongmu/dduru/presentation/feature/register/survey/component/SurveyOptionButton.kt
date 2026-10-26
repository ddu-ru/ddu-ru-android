package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val OptionShape = RoundedCornerShape(16.dp)

@Composable
fun SurveyOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray200,
                shape = OptionShape
            )
            .background(
                color = if (isSelected) {
                    DduruTheme.colors.secondary.copy(alpha = 0.3f)
                } else {
                    DduruTheme.colors.gray0
                },
                shape = OptionShape
            )
            .clip(OptionShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
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

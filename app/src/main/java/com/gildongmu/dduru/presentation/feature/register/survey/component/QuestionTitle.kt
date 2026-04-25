package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun QuestionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = DduruTheme.typography.Title1.copy(
            lineHeight = (18 * 1.8).sp
        ),
        color = DduruTheme.colors.gray900,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

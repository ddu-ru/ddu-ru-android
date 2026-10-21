package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun QuestionNumberBadge(
    currentQuestion: Int,
    totalQuestions: Int,
    label: String = "Survey",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = DduruTheme.colors.secondary,
                shape = RoundedCornerShape(size = 99.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = DduruTheme.typography.Micro,
                color = DduruTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$currentQuestion",
                    style = DduruTheme.typography.Micro,
                    color = DduruTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "/",
                    style = DduruTheme.typography.Micro,
                    color = DduruTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$totalQuestions",
                    style = DduruTheme.typography.Micro,
                    color = DduruTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

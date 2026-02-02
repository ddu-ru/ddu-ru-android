package com.gildongmu.dduru_android.presentation.feature.register.survey.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun QuestionNumberBadge(
    currentQuestion: Int,
    totalQuestions: Int = 11,
    label: String = "Question",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = DduruTheme.colors.Secondary,
                shape = RoundedCornerShape(size = 99.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = DduruTheme.typography.Badge,
                color = DduruTheme.colors.Primary,
                textAlign = TextAlign.Center,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$currentQuestion",
                    style = DduruTheme.typography.Badge,
                    color = DduruTheme.colors.Primary,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "/",
                    style = DduruTheme.typography.Badge.copy(
                        fontSize = 8.sp,
                        lineHeight = 8.sp
                    ),
                    color = DduruTheme.colors.Primary,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "$totalQuestions",
                    style = DduruTheme.typography.Badge, // 스타일 적용
                    color = DduruTheme.colors.Primary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

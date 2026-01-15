package com.gildongmu.dduru_android.presentation.feature.register.survey.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun QuestionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 18.sp,
            lineHeight = 32.4.sp,
            fontWeight = FontWeight(600),
            color = DduruTheme.colors.Gray900,
            textAlign = TextAlign.Center,
        ),
        modifier = modifier
    )
}

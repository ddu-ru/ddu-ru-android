package com.gildongmu.dduru_android.presentation.feature.register.survey.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun SurveyOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) DduruTheme.colors.Primary else DduruTheme.colors.Gray200,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .background(
                color = if (isSelected)
                    DduruTheme.colors.Secondary.copy(alpha = 0.3f)
                else
                    DduruTheme.colors.Gray0,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(600),
                color = if (isSelected) DduruTheme.colors.Primary else DduruTheme.colors.Gray700,
                textAlign = TextAlign.Center,
            )
        )
    }
}

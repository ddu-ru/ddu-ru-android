package com.gildongmu.dduru_android.presentation.feature.register.survey.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun QuestionCard(
    questionNumber: Int,
    questionTitle: String,
    imageRes: Int? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = DduruTheme.colors.BlackAlpha25,
                ambientColor = DduruTheme.colors.BlackAlpha25
            )
            .shadow(
                elevation = 16.dp,
                spotColor = DduruTheme.colors.BlackAlpha10,
                ambientColor = DduruTheme.colors.BlackAlpha10
            )
            .background(
                color = DduruTheme.colors.Gray0,
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth() // 박스 꽉 채우기
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                QuestionNumberBadge(currentQuestion = questionNumber)
                QuestionTitle(text = questionTitle)
            }

            if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(180.dp)
                )
            }
        }
    }
}

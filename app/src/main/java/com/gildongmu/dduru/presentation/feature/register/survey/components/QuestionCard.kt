package com.gildongmu.dduru.presentation.feature.register.survey.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun QuestionCard(
    questionNumber: Int,
    questionTitle: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val cardShape = remember { RoundedCornerShape(24.dp) }

    val shouldShowImage = !imageUrl.isNullOrEmpty() &&
        imageUrl != "survey/q7.png" &&
        !imageUrl.contains("q7")

    Box(
        modifier = modifier
            .padding(10.dp)
            .shadow(
                elevation = 16.dp,
                shape = cardShape,
                spotColor = DduruTheme.colors.blackAlpha10,
                ambientColor = DduruTheme.colors.blackAlpha10,
                clip = false
            )
            .shadow(
                elevation = 4.dp,
                shape = cardShape,
                spotColor = DduruTheme.colors.blackAlpha10,
                ambientColor = DduruTheme.colors.blackAlpha10,
                clip = false
            )
            .background(
                color = DduruTheme.colors.gray0,
                shape = cardShape
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                QuestionNumberBadge(currentQuestion = questionNumber)
                QuestionTitle(text = questionTitle)
            }

            if (shouldShowImage) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )
            }
        }
    }
}

package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.BuildConfig
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val CardShape = RoundedCornerShape(24.dp)

@Composable
fun QuestionCard(
    questionNumber: Int,
    totalQuestions: Int,
    questionTitle: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    questionNote: String? = null
) {
    val shouldShowImage = !imageUrl.isNullOrEmpty() && !imageUrl.contains("activity_tags")

    Box(
        modifier = modifier
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.05f,
                shape = CardShape,
                clip = false
            )
            .background(color = DduruTheme.colors.gray0, shape = CardShape)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuestionNumberBadge(
                    currentQuestion = questionNumber,
                    totalQuestions = totalQuestions
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    QuestionTitle(text = questionTitle)
                    if (questionNote != null) {
                        Text(
                            text = questionNote,
                            style = DduruTheme.typography.Body4,
                            color = DduruTheme.colors.gray500,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            if (shouldShowImage) {
                val fullUrl = if (imageUrl!!.startsWith("http")) {
                    imageUrl
                } else {
                    "${BuildConfig.BASE_URL.trimEnd('/')}/$imageUrl"
                }
                AsyncImage(
                    model = fullUrl,
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )
            }
        }
    }
}

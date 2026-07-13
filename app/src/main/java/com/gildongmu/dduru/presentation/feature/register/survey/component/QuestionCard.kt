package com.gildongmu.dduru.presentation.feature.register.survey.component

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gildongmu.dduru.BuildConfig
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun QuestionCard(
    questionNumber: Int,
    totalQuestions: Int,
    questionTitle: String,
    imageUrl: String?,
    token: String?,
    modifier: Modifier = Modifier
) {
    val cardShape = remember { RoundedCornerShape(24.dp) }
    val context = LocalContext.current

    val shouldShowImage = !imageUrl.isNullOrEmpty() &&
        !imageUrl.contains("activity_tags")

    Box(
        modifier = modifier
            .padding(10.dp)
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.10f,
                shape = cardShape,
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
                QuestionNumberBadge(currentQuestion = questionNumber, totalQuestions = totalQuestions)
                QuestionTitle(text = questionTitle)
            }

            if (shouldShowImage && token != null) {
                val fullUrl = if (imageUrl!!.startsWith("http")) {
                    imageUrl
                } else {
                    "${BuildConfig.BASE_URL.trimEnd('/')}/$imageUrl"
                }
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(fullUrl)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )
            }
        }
    }
}

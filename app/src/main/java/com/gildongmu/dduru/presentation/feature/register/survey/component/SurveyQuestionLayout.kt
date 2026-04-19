package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun SurveyQuestionLayout(
    questionNumber: Int,
    questionTitle: String,
    imageUrl: String?,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.gray50),
        contentAlignment = Alignment.TopCenter
    ) {
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
                .zIndex(10f)
        )

        PenguinCharacter(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 55.dp)
                .zIndex(5f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestionCard(
                questionNumber = questionNumber,
                questionTitle = questionTitle,
                imageUrl = imageUrl,
                modifier = Modifier
                    .width(343.dp)
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(32.dp))

            content()
        }
    }
}

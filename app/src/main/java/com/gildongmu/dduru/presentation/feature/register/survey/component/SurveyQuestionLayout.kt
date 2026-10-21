package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun SurveyQuestionLayout(
    questionNumber: Int,
    totalQuestions: Int,
    questionTitle: String,
    imageUrl: String?,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    questionNote: String? = null
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.gray50)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                QuestionCard(
                    questionNumber = questionNumber,
                    totalQuestions = totalQuestions,
                    questionTitle = questionTitle,
                    questionNote = questionNote,
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp)
                )

                PenguinCharacter(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 10.dp)
                        .offset(x = 2.dp)
                )

                BackButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 42.dp)
            ) {
                content()
            }
        }
    }
}

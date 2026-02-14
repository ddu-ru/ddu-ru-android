package com.gildongmu.dduru.presentation.feature.register.survey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.SurveyResult
import kotlinx.coroutines.delay

@Composable
fun SurveyResultScreen(
    result: SurveyResult,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isAnalyzing by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isAnalyzing = false
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (isAnalyzing) {
            AnalyzingScreen()
        } else {
            ResultScreen(result, onConfirm)
        }
    }
}

@Composable
private fun AnalyzingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = DduruTheme.colors.primary,
                strokeWidth = 6.dp
            )

            Text(
                text = "당신의 여행 스타일을\n분석하고 있어요...",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray900,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ResultScreen(
    result: SurveyResult,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "내 여행 성격",
            style = DduruTheme.typography.BodyMain,
            color = DduruTheme.colors.gray600
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result.avatarName,
            style = DduruTheme.typography.Heading,
            color = DduruTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = result.avatarDescription,
            style = DduruTheme.typography.BodyMain,
            color = DduruTheme.colors.gray800,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DduruTheme.colors.gray0
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(title = "💡 성격", content = result.personality)
                InfoItem(title = "💪 강점", content = result.strength)
                InfoItem(title = "💬 팁", content = result.tip)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DduruTheme.colors.primary
            )
        ) {
            Text(
                text = "네 맞아요!",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Composable
private fun InfoItem(title: String, content: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Body3,
            color = DduruTheme.colors.gray700
        )
        Text(
            text = content,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray900
        )
    }
}

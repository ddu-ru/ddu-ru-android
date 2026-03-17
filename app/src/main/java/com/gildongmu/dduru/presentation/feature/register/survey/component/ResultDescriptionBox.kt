package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ResultDescriptionBox(
    modifier: Modifier = Modifier,
    items: List<String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DduruTheme.colors.gray100,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray300,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "•  $it",
                style = DduruTheme.typography.Body2,
                textAlign = TextAlign.Center,
                color = DduruTheme.colors.gray700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultDescriptionBoxPreview() {
    DduruTheme {
        ResultDescriptionBox(
            modifier = Modifier
                .padding(24.dp),
            items = listOf(
                "어쩌구 저쩌구 설명..",
                "이러쿵 저러쿵 설명.."
            )
        )
    }
}

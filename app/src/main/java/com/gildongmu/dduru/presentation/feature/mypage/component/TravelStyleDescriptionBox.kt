package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TravelStyleDescriptionBox(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DduruTheme.colors.gray100, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = DduruTheme.colors.gray300, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items.forEach { item ->
            Row {
                Text(
                    text = "•",
                    style = DduruTheme.typography.Body3,
                    color = DduruTheme.colors.gray700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(21.dp)
                )
                Text(
                    text = item,
                    style = DduruTheme.typography.Body3,
                    color = DduruTheme.colors.gray700
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelStyleDescriptionBoxPreview() {
    DduruTheme {
        TravelStyleDescriptionBox(
            items = listOf("어쩌구 저쩌구 설명..", "이러쿵 저러쿵 설명.."),
            modifier = Modifier.padding(16.dp)
        )
    }
}

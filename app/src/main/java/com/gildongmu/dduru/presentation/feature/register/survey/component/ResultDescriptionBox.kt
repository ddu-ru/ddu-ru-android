package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ResultDescriptionBox(
    modifier: Modifier = Modifier,
    items: List<String>
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.Gray100)
            .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        )
        {   items.forEach {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "- $it",
                style = DduruTheme.typography.BodyMedium,
                textAlign = TextAlign.Center,
                color = DduruTheme.colors.Gray700
            )
        }
        }
    }
}

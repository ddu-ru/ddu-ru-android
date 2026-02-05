package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.state.Gender

@Composable
fun GenderSegment(
    selected: Gender?,
    onSelect: (Gender) -> Unit,
    enabled: Boolean
) {
    val unselectedColors = ButtonDefaults.buttonColors(
        containerColor = Color.White,
        contentColor = DduruTheme.colors.Gray700,
        disabledContainerColor = Color.White,
        disabledContentColor = DduruTheme.colors.Gray700
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val isMaleSelected = selected == Gender.MALE
        DduruButton(
            modifier = Modifier.weight(1f),
            text = "남자",
            onClick = { onSelect(Gender.MALE) },
            colors = if (isMaleSelected) {
                ButtonDefaults.buttonColors(
                    containerColor = DduruTheme.colors.Primary,
                    contentColor = Color.White,
                    disabledContainerColor = DduruTheme.colors.Primary.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                )
            } else {
                unselectedColors
            },
            borderColor = if (isMaleSelected) DduruTheme.colors.WhiteAlpha30 else DduruTheme.colors.Gray200,
            cornerSize = 16.dp
        )

        val isFemaleSelected = selected == Gender.FEMALE
        DduruButton(
            modifier = Modifier.weight(1f),
            text = "여자",
            onClick = { onSelect(Gender.FEMALE) },
            colors = if (isFemaleSelected) {
                ButtonDefaults.buttonColors(
                    containerColor = DduruTheme.colors.Primary,
                    contentColor = Color.White,
                    disabledContainerColor = DduruTheme.colors.Primary.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                )
            } else {
                unselectedColors
            },
            borderColor = if (isFemaleSelected) DduruTheme.colors.WhiteAlpha30 else DduruTheme.colors.Gray200,
            cornerSize = 16.dp
        )
    }
}

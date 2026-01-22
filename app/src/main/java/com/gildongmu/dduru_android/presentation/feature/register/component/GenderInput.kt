package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.state.Gender

@Composable
fun GenderSegment(
    selected: Gender?,
    onSelect: (Gender) -> Unit,
    enabled: Boolean
) {
    val unselectedColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = DduruTheme.colors.TextOnButtonDisabled,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = DduruTheme.colors.TextOnButtonDisabled,
//            Color.Black.copy(alpha = 0.4f)
    )
    val buttonShape = RoundedCornerShape(16.dp)

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        val isMaleSelected = selected == Gender.MALE
        PrimaryButton(
            text = "남자",
            enabled = enabled,
            onClick = { onSelect(Gender.MALE) },
            modifier = Modifier
                .weight(1f)
                .let { base ->
                    if (isMaleSelected) base else base.border(
                        width = 1.dp,
                        color =  DduruTheme.colors.MainColor,
                        shape = buttonShape
                    )
                },
            colors = if (isMaleSelected) ButtonDefaults.buttonColors(
                containerColor = DduruTheme.colors.MainColor,
                contentColor = Color.White,
                disabledContainerColor = DduruTheme.colors.MainColor.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            ) else unselectedColors
        )

        val isFemaleSelected = selected == Gender.FEMALE
        PrimaryButton(
            text = "여자",
            enabled = enabled,
            onClick = { onSelect(Gender.FEMALE) },
            modifier = Modifier
                .weight(1f)
                .let { base ->
                    if (isFemaleSelected) base else base.border(
                        width = 1.dp,
                        color =DduruTheme.colors.MainColor,
                        shape = buttonShape
                    )
                },
            colors = if (isFemaleSelected) ButtonDefaults.buttonColors(
                containerColor = DduruTheme.colors.MainColor,
                contentColor = Color.White,
                disabledContainerColor = DduruTheme.colors.MainColor.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            ) else unselectedColors
        )
    }
}

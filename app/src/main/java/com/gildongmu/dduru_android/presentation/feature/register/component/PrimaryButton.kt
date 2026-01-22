package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Blue,
        contentColor = Color.White,
        disabledContainerColor = Color.Blue.copy(alpha = 0.3f),
        disabledContentColor = Color.White.copy(alpha = 0.7f)
    )
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = colors
    ) {
        Text(text)
    }
}

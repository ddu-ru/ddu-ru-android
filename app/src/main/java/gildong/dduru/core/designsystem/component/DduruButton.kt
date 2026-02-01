package gildong.dduru.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import gildong.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = DduruTheme.colors.BlackAlpha14,
                ambientColor = DduruTheme.colors.BlackAlpha14
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.WhiteAlpha30,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = DduruTheme.colors.Primary
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.ButtonLarge,
            color = DduruTheme.colors.Gray0
        )
    }
}

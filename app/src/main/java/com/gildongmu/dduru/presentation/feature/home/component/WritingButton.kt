package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun WritingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(99.dp),
                spotColor = DduruTheme.colors.black.copy(alpha = 0.05f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.05f)
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray9E.copy(alpha = 0.3f),
                shape = RoundedCornerShape(99.dp)
            )
            .background(
                color = DduruTheme.colors.gray50,
                shape = RoundedCornerShape(99.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_write_plus),
            contentDescription = null,
            tint = DduruTheme.colors.gray700
        )

        Text(
            text = stringResource(id = R.string.writing_button_label),
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray700
        )
    }
}

@Preview(name = "WritingButton", showBackground = true)
@Composable
private fun WritingButtonPreview() {
    DduruTheme {
        WritingButton(onClick = {})
    }
}

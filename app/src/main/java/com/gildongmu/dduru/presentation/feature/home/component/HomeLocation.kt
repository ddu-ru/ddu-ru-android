package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeLocationCount(
    location:String,
    color: Color,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_home_location),
            contentDescription = null,
            tint = color
        )
        Text(
            text = location,
            color = color,
            style = textStyle
        )
    }
}


@Preview(name = "HomeAiLocation")
@Composable
private fun HomeAiLocationPreview() {
    DduruTheme {
        HomeLocationCount(
            location = "제주 서귀포",
            color = DduruTheme.colors.gray200,
            textStyle = DduruTheme.typography.Caption2
        )
    }
}

@Preview(name = "HomeSuperHostLocation")
@Composable
private fun HomeSuperHostLocationPreview() {
    DduruTheme {
        HomeLocationCount(
            location = "제주 서귀포",
            color = DduruTheme.colors.gray0,
            textStyle = DduruTheme.typography.Caption1
        )
    }
}

@Preview(name = "HomeLocation")
@Composable
private fun HomeLocationPreview() {
    DduruTheme {
        HomeLocationCount(
            location = "제주 서귀포",
            color = DduruTheme.colors.gray500,
            textStyle = DduruTheme.typography.Caption2
        )
    }
}

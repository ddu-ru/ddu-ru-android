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
fun HomeMemberCount(
    current: Int,
    max: Int,
    textColor: Color,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_home_person),
            contentDescription = null,
            tint = textColor
        )
        Text(
            text = "$current/${max}명",
            color = textColor,
            style = textStyle
        )
    }
}

@Composable
fun HomeGroupMemberCount(
    current: Int,
    max: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home_people),
            contentDescription = null,
            tint = DduruTheme.colors.gray500
        )
        Text(
            text = "$current/${max}명",
            color = DduruTheme.colors.gray500,
            style = DduruTheme.typography.Body2
        )
    }
}

@Preview(name = "HomeMemberCount")
@Composable
private fun HomeMemberCountPreview() {
    DduruTheme {
        HomeMemberCount(
            current = 3,
            max = 4,
            textColor = DduruTheme.colors.gray700,
            textStyle = DduruTheme.typography.Body2
        )
    }
}

@Preview(name = "HomeGroupMemberCount")
@Composable
private fun HomeGroupMemberCountPreview() {
    DduruTheme {
        HomeGroupMemberCount(current = 3, max = 4)
    }
}

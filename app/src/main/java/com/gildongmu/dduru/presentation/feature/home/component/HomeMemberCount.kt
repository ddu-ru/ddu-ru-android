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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeGroupMemberCount(
    current: Int,
    max : Int,
    modifier: Modifier = Modifier,
    textColor : Color = DduruTheme.colors.gray500
) {
   Row(modifier = modifier,verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.spacedBy(6.dp)
       ){
       Icon(painter = painterResource(R.drawable.ic_home_people),
           contentDescription = null,
           tint = textColor
       )
       Text(
           text = "$current/${max}명",
           color = textColor,
           style = DduruTheme.typography.Body2
       )
   }
}

@Preview(name = "HomeGroupMemberCount")
@Composable
private fun OnboardingHashtagPreview() {
    DduruTheme {
        HomeGroupMemberCount(
            current = 3,
            max= 4

            )
    }
}

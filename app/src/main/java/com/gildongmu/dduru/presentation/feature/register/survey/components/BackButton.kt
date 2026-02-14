package com.gildongmu.dduru.presentation.feature.register.survey.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                spotColor = DduruTheme.colors.blackAlpha10,
                ambientColor = DduruTheme.colors.blackAlpha10
            )
            .shadow(
                elevation = 8.dp,
                spotColor = DduruTheme.colors.blackAlpha05,
                ambientColor = DduruTheme.colors.blackAlpha05
            )
            .size(48.dp)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.whiteAlpha30,
                shape = RoundedCornerShape(size = 99.dp)
            )
            .background(
                color = DduruTheme.colors.whiteAlpha30,
                shape = RoundedCornerShape(size = 99.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "뒤로가기",
            tint = DduruTheme.colors.gray900,
            modifier = Modifier.size(24.dp)
        )
    }
}

package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
fun WritingExpandedMenu(
    onDismiss: () -> Unit,
    onCreateTravelRoom: () -> Unit,
    onWriteCommunity: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .width(135.dp)
            .wrapContentHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray200,
                    shape = RoundedCornerShape(16.dp)
                )
                .width(135.dp)
                .wrapContentHeight()
                .background(
                    color = DduruTheme.colors.gray0,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically, // Top -> CenterVertically
                modifier = Modifier
                    .wrapContentHeight()
                    .clickable(onClick = onCreateTravelRoom)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray700,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(id = R.string.writing_create_travel_room),
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray700
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically, // Top -> CenterVertically
                modifier = Modifier
                    .wrapContentHeight()
                    .clickable(onClick = onWriteCommunity)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray700,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(id = R.string.writing_write_community),
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray700
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(99.dp),
                    spotColor = DduruTheme.colors.blackAlpha05,
                    ambientColor = DduruTheme.colors.blackAlpha05
                )
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(99.dp),
                    spotColor = DduruTheme.colors.blackAlpha10,
                    ambientColor = DduruTheme.colors.blackAlpha10
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray400.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(99.dp)
                )
                .size(42.dp)
                .background(
                    color = DduruTheme.colors.gray50,
                    shape = RoundedCornerShape(99.dp)
                )
                .clickable(onClick = onDismiss)
                .padding(9.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = stringResource(id = R.string.writing_close),
                tint = DduruTheme.colors.gray700,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(name = "WritingExpandedMenu", showBackground = true)
@Composable
private fun WritingExpandedMenuPreview() {
    DduruTheme {
        WritingExpandedMenu(
            onDismiss = {},
            onCreateTravelRoom = {},
            onWriteCommunity = {}
        )
    }
}

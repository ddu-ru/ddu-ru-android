package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
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
        modifier = modifier.wrapContentHeight()
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
                .wrapContentHeight()
                .background(
                    color = DduruTheme.colors.gray0,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentHeight()
                    .noRippleClickable(onClick = onCreateTravelRoom)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_write_plus),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray700
                )
                Text(
                    text = stringResource(id = R.string.writing_create_travel_room),
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray700
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentHeight()
                    .noRippleClickable(onClick = onWriteCommunity)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_write_pencil),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray700
                )

                Text(
                    text = stringResource(id = R.string.writing_write_community),
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray700
                )
            }
        }

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .dduruDualShadow(
                    firstAlpha = 0.05f,
                    secondAlpha = 0.10f,
                    shape = RoundedCornerShape(99.dp)
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
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = DduruTheme.colors.gray700
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

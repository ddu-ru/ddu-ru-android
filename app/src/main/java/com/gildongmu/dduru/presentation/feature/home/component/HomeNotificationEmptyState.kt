package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeNotificationEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 86.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_home_empty_penguin),
            contentDescription = null,
            modifier = Modifier
                .width(38.dp)
                .height(40.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "현재 알림이 없어요\n새 알림이 오면 알려드릴게요",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray600,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationEmptyStatePreview() {
    DduruTheme {
        HomeNotificationEmptyState(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )
    }
}

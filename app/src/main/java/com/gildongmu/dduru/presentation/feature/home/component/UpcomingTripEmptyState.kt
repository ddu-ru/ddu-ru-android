package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun UpcomingTripEmptyState(
    modifier: Modifier = Modifier,

) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = DduruTheme.colors.gray0
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DduruTheme.colors.gray200
        ),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 38.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.img_home_empty_penguin),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 150.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text ="아직 여행이 없어요.\n새로운 여행을 시작해봐요!",
                style = DduruTheme.typography.Body1,
                color =DduruTheme.colors.gray600,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 89.dp)
            )

        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun UpcomingTripEmptyStatePreview() {
   DduruTheme {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                UpcomingTripEmptyState()
            }
        }
    }

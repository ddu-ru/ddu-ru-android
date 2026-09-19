package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyAdminMemberManagement(
    onKickMemberClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "멤버 관리",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DduruTheme.colors.gray0,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray200,
                    shape = RoundedCornerShape(16.dp)
                )
                .noRippleClickable(onClick = onKickMemberClick)
                .padding(
                    horizontal = 20.dp,
                    vertical = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_signout),
                contentDescription = null,
                tint = DduruTheme.colors.validationRed
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "멤버 강퇴하기",
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.validationRed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyAdminMemberManagementPreview() {
    DduruTheme {
        JourneyAdminMemberManagement(onKickMemberClick = {})
    }
}

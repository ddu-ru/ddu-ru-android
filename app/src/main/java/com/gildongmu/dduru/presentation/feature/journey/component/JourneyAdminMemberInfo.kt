package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember
import com.gildongmu.dduru.presentation.feature.journey.state.journeyAdminPreviewMember

@Composable
fun JourneyAdminMemberInfo(
    member: JourneyAdminMember,
    modifier: Modifier = Modifier,
    showBackground: Boolean = false,
    profileImageModel: Any? = member.profileImageUrl
) {
    Row(
        modifier = modifier
            .then(
                if (showBackground) {
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(DduruTheme.colors.gray100)
                        .padding(16.dp)
                        .fillMaxWidth()
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = profileImageModel,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = member.username,
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.black
            )

            if (member.roles.isNotEmpty()) {
                Text(
                    text = member.roles.joinToString(" · "),
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyAdminMemberInfoPreview() {
    DduruTheme {
        JourneyAdminMemberInfo(
            member = journeyAdminPreviewMember,
            showBackground = true,
            profileImageModel = R.drawable.img_blank_profile
        )
    }
}

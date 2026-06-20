package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

sealed interface MatchingHostProfileImage {
    data class Resource(val resId: Int) : MatchingHostProfileImage

    data class Url(val value: String) : MatchingHostProfileImage
}

@Composable
fun MatchingHostInfoComponent(
    username: String,
    age: String,
    gender: String,
    profileImage: MatchingHostProfileImage?,
    onProfileClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Box {
                val profileImageModel = when (profileImage) {
                    is MatchingHostProfileImage.Resource -> profileImage.resId
                    is MatchingHostProfileImage.Url -> profileImage.value
                    null -> R.drawable.img_blank_profile
                }

                AsyncImage(
                    model = profileImageModel,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.img_blank_profile)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = username,
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.black
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_verifiedbadge),
                        contentDescription = null,
                        tint = DduruTheme.colors.primary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$age · $gender",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray500
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = DduruTheme.colors.gray200
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onProfileClick)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "호스트 프로필 보기",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray800,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = DduruTheme.colors.gray700,
                modifier = Modifier.size(15.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DduruButton(
            text = "호스트에게 문의하기",
            onClick = onContactClick,
            enabledColors = DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.gray0,
                textColor = DduruTheme.colors.gray800,
                borderColor = DduruTheme.colors.gray200
            ),
            disabledColors = DduruButtonDefaults.whiteDisabledColors,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingHostInfoComponentPreview() {
    DduruTheme {
        MatchingHostInfoComponent(
            username = "username",
            age = "20대",
            gender = "여성",
            profileImage = MatchingHostProfileImage.Resource(R.drawable.img_profile),
            onProfileClick = { },
            onContactClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingBlankHostInfoComponentPreview() {
    DduruTheme {
        MatchingHostInfoComponent(
            username = "username",
            age = "20대",
            gender = "여성",
            profileImage = null,
            onProfileClick = { },
            onContactClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

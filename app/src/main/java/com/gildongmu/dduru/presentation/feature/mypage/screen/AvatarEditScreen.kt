package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.profile.component.ColorDotGrid
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileImageCircle
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList

@Composable
fun AvatarEditScreen(
    avatarImage: Any,
    avatarType: String,
    avatarMood: String,
    selectedColor: Color?,
    onColorClick: (Color) -> Unit,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 66.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                        .background(Color.White.copy(alpha = 0.5f), CircleShape)
                        .noRippleClickable(onClick = onBackClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_left),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray900,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "아바타 편집",
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.gray0
                )

                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(99.dp))
                        .background(DduruTheme.colors.primary, RoundedCornerShape(99.dp))
                        .noRippleClickable(onClick = onDoneClick)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "완료",
                        style = DduruTheme.typography.Title2,
                        color = DduruTheme.colors.gray0
                    )
                }
            }

            Spacer(Modifier.height(84.dp))

            ProfileImageCircle(
                image = avatarImage,
                backgroundColor = DduruTheme.colors.gray100,
                size = 260.dp
            )

            Spacer(Modifier.height(24.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = avatarType,
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray400,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = avatarMood,
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.gray0,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(40.dp))

            ColorDotGrid(
                colors = ProfileBackgroundColorList.map { it.color },
                selectedColor = selectedColor,
                onColorClick = onColorClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(176.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarEditScreenPreview() {
    DduruTheme {
        AvatarEditScreen(
            avatarImage = R.drawable.img_blank_profile,
            avatarType = "아바타 유형",
            avatarMood = "뚜르 무드",
            selectedColor = ProfileBackgroundColorList.first().color,
            onColorClick = {},
            onBackClick = {},
            onDoneClick = {}
        )
    }
}

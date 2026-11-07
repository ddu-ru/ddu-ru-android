package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList

@Composable
fun ProfileCharacterCustomDialog(
    selectedBg: Color,
    characterImage: Int,
    avatarName: String,
    onBgColorSelected: (Color) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    useProfileEditStyle: Boolean = false
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            if (useProfileEditStyle) {
                ProfileEditAvatarContent(
                    selectedBg = selectedBg,
                    characterImage = characterImage,
                    avatarName = avatarName,
                    onBgColorSelected = onBgColorSelected,
                    onConfirm = onConfirm,
                    onDismiss = onDismiss
                )
                return@Box
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        { onDismiss() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "뒤로가기",
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        text = "아바타 편집",
                        style = DduruTheme.typography.H1,
                        color = Color.White
                    )
                    DduruButton(
                        "완료",
                        onConfirm
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileImageCircle(
                        modifier = Modifier.padding(32.dp),
                        image = characterImage,
                        backgroundColor = selectedBg,
                        scale = 1.6f,
                        offsetRatio = 0.16f,
                        size = 260.dp
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp),
                        text = "아바타 유형",
                        style = DduruTheme.typography.Body2,
                        textAlign = TextAlign.Center,
                        color = DduruTheme.colors.gray600
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp),
                        text = avatarName,
                        style = DduruTheme.typography.H1,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, bottom = 16.dp),
                        text = "배경색",
                        style = DduruTheme.typography.Title2,
                        textAlign = TextAlign.Start,
                        color = Color.White
                    )
                    ColorDotGrid(
                        modifier = Modifier.padding(bottom = 78.dp),
                        colors = ProfileBackgroundColorList.map { it.color },
                        selectedColor = selectedBg,
                        onColorClick = onBgColorSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileEditAvatarContent(
    selectedBg: Color,
    characterImage: Int,
    avatarName: String,
    onBgColorSelected: (Color) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(48.dp)
                    .dduruDualShadow(firstAlpha = 0.10f, secondAlpha = 0.08f, shape = CircleShape)
                    .background(color = DduruTheme.colors.gray0.copy(alpha = 0.92f), shape = CircleShape)
                    .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                    .noRippleClickable(onClick = onDismiss)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = "뒤로가기",
                    tint = DduruTheme.colors.black,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "아바타 편집",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray0,
                modifier = Modifier.align(Alignment.Center)
            )

            DduruButton(
                text = "완료",
                onClick = onConfirm,
                textStyle = DduruTheme.typography.Title2,
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .height(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        ProfileImageCircle(
            image = characterImage,
            backgroundColor = selectedBg,
            scale = 1.6f,
            offsetRatio = 0.16f,
            size = 260.dp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "아바타 유형",
            style = DduruTheme.typography.Body4,
            color = DduruTheme.colors.gray400
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = avatarName,
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.gray0
        )

        Spacer(modifier = Modifier.height(41.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ProfileBackgroundColorList.chunked(5).forEach { rowColors ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowColors.forEach { backgroundColor ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(backgroundColor.color, CircleShape)
                                .then(
                                    if (backgroundColor.color == selectedBg) {
                                        Modifier.border(4.dp, DduruTheme.colors.gray0, CircleShape)
                                    } else {
                                        Modifier
                                    }
                                )
                                .noRippleClickable { onBgColorSelected(backgroundColor.color) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCharacterCustomDialogPreview() {
    var bg by remember { mutableStateOf(ProfileBackgroundColorList.first().color) }

    ProfileCharacterCustomDialog(
        selectedBg = bg,
        characterImage = R.drawable.img_penguin_personallity,
        avatarName = "뚜르 무드",
        onBgColorSelected = { bg = it },
        onConfirm = {},
        onDismiss = {}
    )
}

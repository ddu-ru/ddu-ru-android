package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    onDismiss: () -> Unit
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
                        onConfirm,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileImageCircle(
                        modifier = Modifier.padding(32.dp),
                        characterImage = characterImage,
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

                        onColorClick = onBgColorSelected
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCharacterCustomDialogPreview() {
    var bg by remember { mutableStateOf(Color.Red) }

    ProfileCharacterCustomDialog(
        selectedBg = bg,
        characterImage = R.drawable.img_penguin_personallity,
        avatarName = "뚜르 무드",
        onBgColorSelected = { bg = it },
        onConfirm = {},
        onDismiss = {}
    )
}

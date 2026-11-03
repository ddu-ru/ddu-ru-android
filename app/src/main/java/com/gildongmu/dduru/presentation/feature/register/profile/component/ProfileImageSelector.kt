package com.gildongmu.dduru.presentation.feature.register.profile.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption

@Composable
fun ProfileImageSelector(
    options: List<ProfileImageCropOption>,
    selectedOption: ProfileEditOption,
    selectedImageUri: Uri?,
    characterBackgroundColor: Color,
    isLocked: Boolean,
    onOptionSelected: (ProfileImageCropOption) -> Unit,
    onActionClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .selectableGroup()
        ) {
            options.forEach { option ->
                val optionLocked = isLocked && option.type == ProfileEditOption.CHARACTER

                Column(
                    Modifier
                        .selectable(
                            selected = option.type == selectedOption,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            enabled = !optionLocked,
                            role = Role.RadioButton,
                            onClick = { if (!optionLocked) onOptionSelected(option) }
                        )
                        .semantics {
                            contentDescription = buildString {
                                append(
                                    if (option.type == ProfileEditOption.CHARACTER) {
                                        "아바타 프로필"
                                    } else {
                                        "사진 프로필"
                                    }
                                )

                                if (optionLocked) {
                                    append(", 선택 불가")
                                }

                                if (option.type == selectedOption) {
                                    append(", 선택됨")
                                }
                            }
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val currentSource: Any = if (option.type == ProfileEditOption.CUSTOM) {
                        selectedImageUri ?: option.image
                    } else {
                        option.image
                    }

                    ProfileImageCircle(
                        image = currentSource,
                        scale = option.scale,
                        backgroundColor = characterBackgroundColor,
                        offsetRatio = option.offsetRatio,
                        isLocked = optionLocked
                    )

                    ProfileOptionCheck(
                        isSelected = option.type == selectedOption,
                        isLocked = optionLocked
                    )
                }
            }
        }

        Button(
            onActionClick,
            colors = ButtonDefaults.buttonColors(DduruTheme.colors.gray200),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = if (selectedOption == ProfileEditOption.CHARACTER) "배경 색상 선택" else "사진 변경",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray700
            )
        }
    }
}

@Composable
private fun ProfileOptionCheck(
    isSelected: Boolean,
    isLocked: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> DduruTheme.colors.primary
                    isLocked -> DduruTheme.colors.gray200
                    else -> DduruTheme.colors.gray0
                }
            )
            .border(
                width = 1.dp,
                color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray300,
                shape = CircleShape
            )
    ) {
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = DduruTheme.colors.gray0,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImageSelectorPreview() {
    val options = listOf(
        ProfileImageCropOption(
            R.drawable.img_penguin_personallity,
            ProfileEditOption.CHARACTER,
            1.6f,
            0.16f
        ),
        ProfileImageCropOption(R.drawable.img_blank_profile, ProfileEditOption.CUSTOM)
    )
    var selectedOption by remember { mutableStateOf(options.last().type) }

    ProfileImageSelector(
        options = options,
        selectedOption = selectedOption,
        selectedImageUri = "android.resource://com.gildongmu.dduru/drawable/img_blank_profile".toUri(),
        isLocked = false,
        onOptionSelected = { selectedOption = it.type },
        characterBackgroundColor = Color.White,
        onActionClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileImageSelectorLockedPreview() {
    val options = listOf(
        ProfileImageCropOption(
            R.drawable.img_penguin_personallity,
            ProfileEditOption.CHARACTER,
            1.6f,
            0.16f
        ),
        ProfileImageCropOption(R.drawable.img_blank_profile, ProfileEditOption.CUSTOM)
    )
    var selectedOption by remember { mutableStateOf(options.last().type) }

    ProfileImageSelector(
        options = options,
        selectedOption = selectedOption,
        selectedImageUri = "android.resource://com.gildongmu.dduru/drawable/img_blank_profile".toUri(),
        isLocked = true,
        onOptionSelected = { selectedOption = it.type },
        characterBackgroundColor = Color.White,
        onActionClick = {}
    )
}

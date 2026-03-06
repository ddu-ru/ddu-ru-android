package com.gildongmu.dduru.presentation.feature.register.profile.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileType
import androidx.core.net.toUri

@Composable
fun ProfileImageSelector(
    options: List<ProfileImageCropOption>,
    selectedOption: ProfileImageCropOption,
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
                Column(
                    Modifier.selectable(
                        selected = (option == selectedOption),
                        onClick = { if (!isLocked) onOptionSelected(option) },
                        enabled = !isLocked,
                        role = Role.RadioButton
                    )
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val currentUri =
                        if (option.type == ProfileType.CUSTOM) selectedImageUri else null

                    ProfileImageCircle(
                        characterImage = option.resId,
                        imageUri = currentUri,
                        scale = option.scale,
                        backgroundColor = characterBackgroundColor,
                        offsetRatio = option.offsetRatio
                    )

                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null,
                        enabled = !isLocked
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
                text = if (selectedOption.type == ProfileType.CHARACTER) "아바타 편집" else "사진 변경",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImageSelectorPreview() {
    ProfileImageSelector(
        options = listOf(
            ProfileImageCropOption(
                R.drawable.img_penguin_personallity,
                ProfileType.CHARACTER,
                1.6f,
                0.16f
            ),
            ProfileImageCropOption(R.drawable.img_blank_profile, ProfileType.CUSTOM)
        ),
        selectedOption = ProfileImageCropOption(
            R.drawable.img_penguin_personallity,
            ProfileType.CHARACTER,
            1.6f,
            0.16f
        ),
        selectedImageUri = "android.resource://com.gildongmu.dduru/drawable/img_blank_profile".toUri(),
        isLocked = false,
        onOptionSelected = { },
        characterBackgroundColor = Color.White,
        onActionClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun ProfileImageSelectorLockedPreview() {
    ProfileImageSelector(
        options = listOf(
            ProfileImageCropOption(
                R.drawable.img_penguin_personallity,
                ProfileType.CHARACTER,
                1.6f,
                0.16f
            ),
            ProfileImageCropOption(R.drawable.img_blank_profile, ProfileType.CUSTOM)
        ),
        selectedOption = ProfileImageCropOption(
            R.drawable.img_penguin_personallity,
            ProfileType.CHARACTER,
            1.6f,
            0.16f
        ),
        selectedImageUri = "android.resource://com.gildongmu.dduru/drawable/img_blank_profile".toUri(),
        isLocked = true,
        onOptionSelected = { },
        characterBackgroundColor = Color.White,
        onActionClick = {}
    )
}

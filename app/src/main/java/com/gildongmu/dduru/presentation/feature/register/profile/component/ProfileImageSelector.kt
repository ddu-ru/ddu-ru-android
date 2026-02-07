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
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileType

@Composable
fun ProfileImageSelector(
    options: List<ProfileImageCropOption>,
    selectedOption: ProfileImageCropOption,
    selectedImageUri: Uri?,
    characterBackgroundColor: Color,
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
                        onClick = { onOptionSelected(option) },
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
                        onClick = null
                    )
                }
            }
        }

        Button(
            onActionClick,
            colors = ButtonDefaults.buttonColors(DduruTheme.colors.Gray200),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = if (selectedOption.type == ProfileType.CHARACTER) "아바타 편집" else "사진 변경",
                style = DduruTheme.typography.Button,
                color = DduruTheme.colors.Gray700
            )
        }
    }
}

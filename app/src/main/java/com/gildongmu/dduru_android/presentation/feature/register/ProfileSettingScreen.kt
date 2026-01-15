package com.gildongmu.dduru_android.presentation.feature.register

import com.gildongmu.dduru_android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import androidx.compose.ui.Modifier
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.gildongmu.dduru_android.presentation.component.ProfileImageCrop
import com.gildongmu.dduru_android.presentation.component.SpeechBubble
import com.gildongmu.dduru_android.presentation.model.ProfileImageCropOption

@Composable
fun ProfileSettingScreen(
    modifier: Modifier
) {
    var text by remember { mutableStateOf("") }
    val maxLength = 60

    val radioOptions = listOf(
        ProfileImageCropOption(R.drawable.img_penguin_personallity, 1.6f, 15.dp),
        ProfileImageCropOption(R.drawable.img_blank_profile),
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column (
        modifier = modifier
            .background(DduruTheme.colors.Gray50)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "프로필 설정",
            style = DduruTheme.typography.TitleMedium,
            textAlign = TextAlign.Center,
        )
        Row (
            modifier = Modifier
                .selectableGroup()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){ radioOptions.forEach { option ->
            Column(
                Modifier.selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            )
            {
                ProfileImageCrop(
                resId = option.resId,
                scale = option.scale,
                offset = option.offset
                )

                RadioButton(
                    selected = (option == selectedOption),
                    onClick = null
                )
            }
            }
        }
        Button(
            {},
            colors = ButtonDefaults.buttonColors(DduruTheme.colors.Gray200),
            modifier = Modifier,
        ){
            Text(
                text = "아바타 편집",
                style = DduruTheme.typography.Button,
                color = DduruTheme.colors.Gray700
            )
        }

        Column( horizontalAlignment = Alignment.CenterHorizontally,)
        {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "겨울이 오길",
                style = DduruTheme.typography.TitleMedium,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier
                    .padding(8.dp, bottom = 24.dp),
                text = "뚜르 무드",
                style = DduruTheme.typography.LabelMedium,
                textAlign = TextAlign.Center,
                color = DduruTheme.colors.Gray600
            )
        }

        Column (
            horizontalAlignment = Alignment.Start,
        )
        {
            Text(
                modifier = Modifier
                    .padding(8.dp, bottom = 16.dp),
                text = "자기소개",
                style = DduruTheme.typography.BodyLarge
            )
            Box(
                modifier = Modifier
                    .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        if (it.length <= maxLength) text = it
                    },
                    maxLines = 4,
                    textStyle = DduruTheme.typography.BodyLarge.copy(
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            Modifier.padding(16.dp)
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = "나를 소개하는 한마디를 작성해주세요!",
                                    style = DduruTheme.typography.BodyLarge,
                                    color = DduruTheme.colors.Gray300
                                )
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 140.dp)
                )
                Text(
                    text = "${text.length} / $maxLength",
                    style = DduruTheme.typography.BodyMedium,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    color = DduruTheme.colors.Gray300
                )
            }
        }
        SpeechBubble(
            shadowElevation = 3.dp
        ){
            Text(
                modifier = Modifier,
                text = "모든 정보가 맞나요? 이제 시작해요!",
                style = DduruTheme.typography.BodyLarge,
                textAlign = TextAlign.Center
            )
        }
        DduruButton(
            "뚜르 시작하기",
            {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 78.dp))
    }
}

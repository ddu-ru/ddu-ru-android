package com.gildongmu.dduru_android.presentation.feature.register.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru_android.presentation.component.SpeechBubble
import com.gildongmu.dduru_android.presentation.feature.register.profile.component.ProfileCharacterCustomDialog
import com.gildongmu.dduru_android.presentation.feature.register.profile.component.ProfileImageSelector
import com.gildongmu.dduru_android.presentation.feature.register.profile.component.ProfileIntroduction
import com.gildongmu.dduru_android.presentation.model.ProfileType
import rememberGalleryLauncher

@Composable
fun ProfileSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val selectedOption by viewModel.selectedOption.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val introduction by viewModel.introduction.collectAsState()

    var showCharacterCustomScreen by remember { mutableStateOf(false) }
    val galleryLauncher = rememberGalleryLauncher { uri -> viewModel.onImageSelected(uri) }

    Column (
        modifier = modifier
            .background(DduruTheme.colors.Gray50)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
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

        ProfileImageSelector(
            options = viewModel.radioOptions,
            selectedOption = selectedOption,
            selectedImageUri = selectedImageUri,
            onOptionSelected = { viewModel.onOptionSelected(it) },
            onActionClick = {
                if (selectedOption.type == ProfileType.CHARACTER) showCharacterCustomScreen = true
                else galleryLauncher.launch("image/*")
            }
        )

        if (showCharacterCustomScreen) {
            ProfileCharacterCustomDialog(
                onDismiss = { showCharacterCustomScreen = false }
            )
        }

        Column( horizontalAlignment = Alignment.CenterHorizontally,)
        {
            Text(
                modifier = Modifier,
                text = "겨울이 오길",
                style = DduruTheme.typography.TitleMedium,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier
                    .padding(8.dp, 8.dp, bottom = 24.dp),
                text = "뚜르 무드",
                style = DduruTheme.typography.LabelMedium,
                textAlign = TextAlign.Center,
                color = DduruTheme.colors.Gray600
            )
        }

        ProfileIntroduction(
            text = introduction,
            maxLength = viewModel.maxIntroLength,
            onValueChange = { viewModel.onIntroductionChanged(it)},
            modifier = Modifier.fillMaxWidth()
        )

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
                .padding(top = 0.dp, bottom = 78.dp))
    }
}

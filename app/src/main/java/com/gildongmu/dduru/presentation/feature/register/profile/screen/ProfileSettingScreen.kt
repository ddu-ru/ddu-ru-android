package com.gildongmu.dduru.presentation.feature.register.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.register.profile.viewmodel.ProfileViewModel
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileCharacterCustomDialog
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileImageSelector
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileIntroduction
import com.gildongmu.dduru.presentation.feature.register.survey.model.ProfileType
import rememberGalleryLauncher


@Composable
fun ProfileSettingScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    var showCharacterCustomScreen by remember { mutableStateOf(false) }

    val selectedOption by viewModel.selectedOption.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val introduction by viewModel.introduction.collectAsState()
    val savedBgColor by viewModel.savedBgColor.collectAsState()

    val galleryLauncher = rememberGalleryLauncher { uri -> viewModel.onImageSelected(uri) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DduruTheme.colors.Gray50,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SpeechBubble(
                    modifier = Modifier.padding(5.dp),
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
                    text = "뚜르 시작하기",
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 78.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "프로필 설정",
                style = DduruTheme.typography.TitleLarge,
                textAlign = TextAlign.Center,
            )

            ProfileImageSelector(
                options = viewModel.radioOptions,
                selectedOption = selectedOption,
                selectedImageUri = selectedImageUri,
                onOptionSelected = { viewModel.onOptionSelected(it) },
                characterBackgroundColor = savedBgColor,
                onActionClick = {
                    if (selectedOption.type == ProfileType.CHARACTER) showCharacterCustomScreen = true
                    else galleryLauncher.launch("image/*")
                }
            )

            if (showCharacterCustomScreen) {
                ProfileCharacterCustomDialog(
                    viewModel = viewModel,
                    onDismiss = {
                        showCharacterCustomScreen = false
                    }
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
        }
    }
}

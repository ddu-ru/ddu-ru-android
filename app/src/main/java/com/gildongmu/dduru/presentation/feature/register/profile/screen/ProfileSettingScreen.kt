package com.gildongmu.dduru.presentation.feature.register.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.register.profile.component.NicknameInput
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileCharacterCustomDialog
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileImageSelector
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileIntroduction
import com.gildongmu.dduru.presentation.feature.register.profile.component.rememberGalleryLauncher
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileType
import com.gildongmu.dduru.presentation.feature.register.profile.util.surveyStatus
import com.gildongmu.dduru.presentation.feature.register.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileSettingScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    var showCharacterCustomScreen by remember { mutableStateOf(false) }

    val galleryLauncher = rememberGalleryLauncher { uri -> viewModel.onImageSelected(uri) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DduruTheme.colors.gray50,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpeechBubble(
                    modifier = Modifier.padding(5.dp),
                    shadowElevation = 3.dp
                ) {
                    Text(
                        modifier = Modifier,
                        text = "모든 정보가 맞나요? 이제 시작해요!",
                        style = DduruTheme.typography.Title2,
                        textAlign = TextAlign.Center
                    )
                }
                DduruButton(
                    text = "뚜르 시작하기",
                    onClick = {},
                    enabled = state.canStart,
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
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "프로필 설정",
                style = DduruTheme.typography.H1,
                textAlign = TextAlign.Center
            )

            ProfileImageSelector(
                options = viewModel.radioOptions,
                selectedOption = state.selectedOption ?: viewModel.radioOptions.last(),
                selectedImageUri = state.selectedImageUri,
                onOptionSelected = { viewModel.onProfileOptionSelected(it) },
                characterBackgroundColor = state.savedBgColor,
                isLocked = state.isSurveyComplete == surveyStatus.SKIPPED,
                onActionClick = {
                    if (state.selectedOption?.type == ProfileType.CHARACTER) {
                        showCharacterCustomScreen = true
                    } else {
                        galleryLauncher.launch("image/*")
                    }
                }
            )

            if (showCharacterCustomScreen) {
                ProfileCharacterCustomDialog(
                    selectedBg = state.selectedBgColor,
                    characterImage = viewModel.characterImageRes.collectAsState().value,
                    avatarName = "",
                    onBgColorSelected = viewModel::onBgColorSelected,
                    onConfirm = {
                        viewModel.commitBgColorSelection()
                        showCharacterCustomScreen = false
                    },
                    onDismiss = {
                        showCharacterCustomScreen = false
                    }
                )
            }

            NicknameInput(
                modifier = Modifier,
                enabled = true,
                onRandomClick = viewModel::onRandomNickname,
                value = state.nickname,
                onValueChange = { viewModel.onNicknameChanged(it) },
                validationMessage = state.nicknameErrorMessage ?: ""
            )

            ProfileIntroduction(
                text = state.introduction,
                maxLength = viewModel.maxIntroLength,
                onValueChange = { viewModel.onIntroductionChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

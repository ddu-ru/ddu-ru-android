package com.gildongmu.dduru.presentation.feature.register.profile.screen

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.register.component.ValidationMessage
import com.gildongmu.dduru.presentation.feature.register.profile.component.NicknameInput
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileCharacterCustomDialog
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileImageSelector
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileIntroduction
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileFormState
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileUiState
import com.gildongmu.dduru.domain.register.model.SurveyStatus
import com.gildongmu.dduru.presentation.feature.register.profile.util.rememberGalleryLauncher
import com.gildongmu.dduru.presentation.feature.register.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileSettingRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val characterImageRes by viewModel.characterImageRes.collectAsState()

    var showCharacterCustomScreen by remember { mutableStateOf(false) }

    val galleryLauncher = rememberGalleryLauncher { uri: Uri? ->
        viewModel.onImageSelected(uri)
    }

    ProfileSettingScreen(
        modifier = modifier,
        state = state,
        formState = formState,
        options = viewModel.radioOptions,
        characterImageRes = characterImageRes,
        onRandomClick = viewModel::onRandomNickname,
        onNicknameChange = viewModel::onNicknameChanged,
        onIntroductionChange = viewModel::onIntroductionChanged,
        onOptionSelected = viewModel::onProfileOptionSelected,
        onActionClick = {
            if (formState.selectedOption == ProfileEditOption.CHARACTER) {
                showCharacterCustomScreen = true
            } else {
                galleryLauncher.launch("image/*")
            }
        },
        showCharacterCustomScreen = showCharacterCustomScreen,
        onBgColorSelected = viewModel::onBgColorSelected,
        onConfirmCharacterCustom = {
            viewModel.commitBgColorSelection()
            showCharacterCustomScreen = false
        },
        onDismissCharacterCustom = {
            showCharacterCustomScreen = false
        },
        onStartClick = viewModel::onStartClick
    )
}

@Composable
fun ProfileSettingScreen(
    state: ProfileUiState,
    formState: ProfileFormState,
    options: List<ProfileImageCropOption>,
    characterImageRes: Int,
    modifier: Modifier = Modifier,
    onRandomClick: () -> Unit,
    onNicknameChange: (String) -> Unit,
    onIntroductionChange: (String) -> Unit,
    onOptionSelected: (ProfileImageCropOption) -> Unit,
    onActionClick: () -> Unit,
    showCharacterCustomScreen: Boolean,
    onBgColorSelected: (Color) -> Unit,
    onConfirmCharacterCustom: () -> Unit,
    onDismissCharacterCustom: () -> Unit,
    onStartClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                    modifier = Modifier.padding(bottom = 7.dp),
                    shadowElevation = 3.dp
                ) {
                    Text(
                        text = "모든 정보가 맞나요? 이제 시작해요!",
                        style = DduruTheme.typography.Title2,
                        textAlign = TextAlign.Center,
                        color = DduruTheme.colors.gray700
                    )
                }

                if (!state.submitErrorMessage.isNullOrBlank()) {
                    ValidationMessage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        text = state.submitErrorMessage
                    )
                }

                DduruButton(
                    text = "뚜르 시작하기",
                    onClick = onStartClick,
                    enabled = state.canStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 78.dp)
                )
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(24f))

                Text(
                    text = "프로필 설정",
                    style = DduruTheme.typography.H1,
                    textAlign = TextAlign.Center
                )

                ProfileImageSelector(
                    options = options,
                    selectedOption = formState.selectedOption,
                    selectedImageUri = formState.selectedImageUri,
                    onOptionSelected = onOptionSelected,
                    characterBackgroundColor = formState.savedBgColor,
                    isLocked = state.surveyStatus != SurveyStatus.COMPLETED,
                    onActionClick = onActionClick
                )

                if (!state.imageErrorMessage.isNullOrBlank()) {
                    ValidationMessage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        text = state.imageErrorMessage
                    )
                }

                Spacer(Modifier.weight(24f))

                NicknameInput(
                    modifier = Modifier,
                    onRandomClick = onRandomClick,
                    text = formState.nickname,
                    onValueChange = onNicknameChange,
                    validationMessage = state.nicknameErrorMessage ?: ""
                )

                Spacer(Modifier.weight(32f))

                ProfileIntroduction(
                    text = formState.introduction,
                    maxLength = 20,
                    onValueChange = onIntroductionChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.weight(18f))
            }
        }

        if (showCharacterCustomScreen) {
            ProfileCharacterCustomDialog(
                selectedBg = formState.selectedBgColor,
                characterImage = characterImageRes,
                avatarName = "",
                onBgColorSelected = onBgColorSelected,
                onConfirm = onConfirmCharacterCustom,
                onDismiss = onDismissCharacterCustom
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    DduruTheme {
        ProfileSettingScreen(
            state = ProfileUiState(
                nicknameErrorMessage = null,
                imageErrorMessage = null,
                submitErrorMessage = null,
                canStart = true
            ),
            formState = ProfileFormState(
                selectedOption = ProfileEditOption.CUSTOM,
                selectedBgColor = Color.White,
                savedBgColor = Color.White,
                nickname = "",
                introduction = ""
            ),
            options = listOf(
                ProfileImageCropOption(
                    image = R.drawable.img_penguin_personallity,
                    type = ProfileEditOption.CHARACTER,
                    scale = 1.6f,
                    offsetRatio = 0.16f
                ),
                ProfileImageCropOption(
                    image = R.drawable.img_blank_profile,
                    type = ProfileEditOption.CUSTOM
                )
            ),
            characterImageRes = R.drawable.img_penguin_personallity,
            onRandomClick = {},
            onNicknameChange = {},
            onIntroductionChange = {},
            onOptionSelected = {},
            onActionClick = {},
            showCharacterCustomScreen = false,
            onBgColorSelected = {},
            onConfirmCharacterCustom = {},
            onDismissCharacterCustom = {},
            onStartClick = {}
        )
    }
}

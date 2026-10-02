package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.component.ConfirmDialog
import com.gildongmu.dduru.presentation.feature.mypage.component.LabelValueRow
import com.gildongmu.dduru.presentation.feature.register.profile.component.NicknameInput
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileImageCircle
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileIntroduction

enum class ProfileEditTab(val label: String) {
    INFO("내 정보"),
    DESTINATION("가고싶은 여행"),
    STYLE("나의 여행 스타일")
}

enum class ProfileEditPhotoSlot { CURRENT, NEW }

@Composable
fun ProfileEditScreen(
    selectedTab: ProfileEditTab,
    onTabClick: (ProfileEditTab) -> Unit,
    currentPhoto: Any,
    newPhoto: Any,
    selectedPhotoSlot: ProfileEditPhotoSlot,
    onPhotoSlotClick: (ProfileEditPhotoSlot) -> Unit,
    photoActionLabel: String,
    onPhotoActionClick: () -> Unit,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onRandomNicknameClick: () -> Unit,
    bio: String,
    onBioChange: (String) -> Unit,
    birthDate: String,
    gender: String,
    phoneNumber: String,
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCurrentPhotoLocked: Boolean = false,
    showLeaveConfirmDialog: Boolean = false,
    onLeaveConfirmDismiss: () -> Unit = {},
    onLeaveConfirm: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 96.dp, bottom = 40.dp)
        ) {
            ProfileEditTabRow(
                selectedTab = selectedTab,
                onTabClick = onTabClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            PhotoSelectionRow(
                currentPhoto = currentPhoto,
                newPhoto = newPhoto,
                selectedPhotoSlot = selectedPhotoSlot,
                onPhotoSlotClick = onPhotoSlotClick,
                isCurrentPhotoLocked = isCurrentPhotoLocked,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                PhotoActionChip(text = photoActionLabel, onClick = onPhotoActionClick)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                    NicknameInput(
                        text = nickname,
                        validationMessage = "",
                        onValueChange = onNicknameChange,
                        onRandomClick = onRandomNicknameClick
                    )

                    ProfileIntroduction(
                        text = bio,
                        onValueChange = onBioChange
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(
                        text = "아래 정보는 회원가입 시 인증된 정보로, 수정할 수 없습니다.",
                        style = DduruTheme.typography.Caption2,
                        color = DduruTheme.colors.gray500
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        LabelValueRow(label = "생년월일", value = birthDate)
                        LabelValueRow(label = "성별", value = gender)
                        LabelValueRow(label = "전화번호", value = phoneNumber)
                    }
                }
            }
        }

        ProfileEditHeader(
            title = "프로필 수정",
            isSaveEnabled = isSaveEnabled,
            onBackClick = onBackClick,
            onSaveClick = onSaveClick,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        if (showLeaveConfirmDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                ConfirmDialog(
                    title = "저장하지 않고 나갈까요?",
                    description = "변경한 내용이 저장되지 않습니다.\n저장하지 않고 나가시겠어요?",
                    onDismiss = onLeaveConfirmDismiss,
                    onConfirm = onLeaveConfirm,
                    dismissText = "계속 수정",
                    confirmText = "나가기",
                    confirmBackgroundColor = DduruTheme.colors.gray900,
                    confirmTextColor = DduruTheme.colors.gray0
                )
            }
        }
    }
}

@Composable
private fun ProfileEditHeader(
    title: String,
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DduruTheme.colors.gray0, DduruTheme.colors.gray0.copy(alpha = 0f))
                )
            )
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            ProfileEditFloatingButton(
                iconRes = R.drawable.ic_chevron_left,
                onClick = onBackClick,
                backgroundColor = DduruTheme.colors.gray0.copy(alpha = 0.5f),
                iconTint = DduruTheme.colors.gray900,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = title,
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.black,
                modifier = Modifier.align(Alignment.Center)
            )

            ProfileEditFloatingButton(
                iconRes = R.drawable.ic_check,
                onClick = onSaveClick,
                enabled = isSaveEnabled,
                backgroundColor = if (isSaveEnabled) DduruTheme.colors.primary else DduruTheme.colors.gray200,
                iconTint = if (isSaveEnabled) DduruTheme.colors.gray0 else DduruTheme.colors.gray400,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
private fun ProfileEditFloatingButton(
    iconRes: Int,
    onClick: () -> Unit,
    backgroundColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
            .then(if (enabled) Modifier.noRippleClickable(onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ProfileEditTabRow(
    selectedTab: ProfileEditTab,
    onTabClick: (ProfileEditTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileEditTab.entries.forEach { tab ->
            val isSelected = tab == selectedTab

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(if (isSelected) DduruTheme.colors.black else DduruTheme.colors.gray50)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else DduruTheme.colors.gray100,
                        shape = RoundedCornerShape(99.dp)
                    )
                    .noRippleClickable { onTabClick(tab) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = tab.label,
                    style = DduruTheme.typography.Body4,
                    color = if (isSelected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Composable
private fun PhotoActionChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray200)
            .noRippleClickable(onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Body4,
            color = DduruTheme.colors.gray700
        )
    }
}

@Composable
private fun PhotoSelectionRow(
    currentPhoto: Any,
    newPhoto: Any,
    selectedPhotoSlot: ProfileEditPhotoSlot,
    onPhotoSlotClick: (ProfileEditPhotoSlot) -> Unit,
    isCurrentPhotoLocked: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        PhotoSlot(
            image = currentPhoto,
            isSelected = selectedPhotoSlot == ProfileEditPhotoSlot.CURRENT,
            isLocked = isCurrentPhotoLocked,
            onClick = { onPhotoSlotClick(ProfileEditPhotoSlot.CURRENT) }
        )

        PhotoSlot(
            image = newPhoto,
            isSelected = selectedPhotoSlot == ProfileEditPhotoSlot.NEW,
            isLocked = false,
            onClick = { onPhotoSlotClick(ProfileEditPhotoSlot.NEW) }
        )
    }
}

@Composable
private fun PhotoSlot(
    image: Any,
    isSelected: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileImageCircle(
            image = image,
            backgroundColor = DduruTheme.colors.gray100,
            size = 100.dp,
            isLocked = isLocked
        )

        PhotoCheckIndicator(
            isSelected = isSelected,
            isDisabled = isLocked,
            onClick = onClick
        )
    }
}

@Composable
private fun PhotoCheckIndicator(
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> DduruTheme.colors.primary
                    isDisabled -> DduruTheme.colors.gray200
                    else -> DduruTheme.colors.gray0
                }
            )
            .border(width = 1.dp, color = DduruTheme.colors.gray300, shape = CircleShape)
            .then(if (!isDisabled) Modifier.noRippleClickable(onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = DduruTheme.colors.gray0,
                modifier = Modifier.size(10.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "기본")
@Composable
private fun ProfileEditScreenPreview() {
    DduruTheme {
        var tab by remember { mutableStateOf(ProfileEditTab.INFO) }
        var slot by remember { mutableStateOf(ProfileEditPhotoSlot.NEW) }
        var nickname by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }

        ProfileEditScreen(
            selectedTab = tab,
            onTabClick = { tab = it },
            currentPhoto = R.drawable.img_penguin_personallity,
            newPhoto = R.drawable.img_blank_profile,
            selectedPhotoSlot = slot,
            onPhotoSlotClick = { slot = it },
            photoActionLabel = "사진 변경",
            onPhotoActionClick = {},
            nickname = nickname,
            onNicknameChange = { nickname = it },
            onRandomNicknameClick = {},
            bio = bio,
            onBioChange = { bio = it },
            birthDate = "2001.05.14",
            gender = "여성",
            phoneNumber = "010-1234-3232",
            isSaveEnabled = nickname.isNotBlank(),
            onBackClick = {},
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true, name = "현재 사진 선택 + 배경색 편집")
@Composable
private fun ProfileEditScreenCurrentSelectedPreview() {
    DduruTheme {
        var tab by remember { mutableStateOf(ProfileEditTab.INFO) }
        var slot by remember { mutableStateOf(ProfileEditPhotoSlot.CURRENT) }
        var nickname by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }

        ProfileEditScreen(
            selectedTab = tab,
            onTabClick = { tab = it },
            currentPhoto = R.drawable.img_penguin_personallity,
            newPhoto = R.drawable.img_blank_profile,
            selectedPhotoSlot = slot,
            onPhotoSlotClick = { slot = it },
            photoActionLabel = "배경색 편집",
            onPhotoActionClick = {},
            nickname = nickname,
            onNicknameChange = { nickname = it },
            onRandomNicknameClick = {},
            bio = bio,
            onBioChange = { bio = it },
            birthDate = "2001.05.14",
            gender = "여성",
            phoneNumber = "010-1234-3232",
            isSaveEnabled = nickname.isNotBlank(),
            onBackClick = {},
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true, name = "새 사진 선택 + 사진 변경")
@Composable
private fun ProfileEditScreenNewSelectedPreview() {
    DduruTheme {
        var tab by remember { mutableStateOf(ProfileEditTab.INFO) }
        var slot by remember { mutableStateOf(ProfileEditPhotoSlot.NEW) }
        var nickname by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }

        ProfileEditScreen(
            selectedTab = tab,
            onTabClick = { tab = it },
            currentPhoto = R.drawable.img_penguin_personallity,
            newPhoto = R.drawable.img_blank_profile,
            selectedPhotoSlot = slot,
            onPhotoSlotClick = { slot = it },
            photoActionLabel = "사진 변경",
            onPhotoActionClick = {},
            nickname = nickname,
            onNicknameChange = { nickname = it },
            onRandomNicknameClick = {},
            bio = bio,
            onBioChange = { bio = it },
            birthDate = "2001.05.14",
            gender = "여성",
            phoneNumber = "010-1234-3232",
            isSaveEnabled = nickname.isNotBlank(),
            onBackClick = {},
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true, name = "기존 사진 잠금")
@Composable
private fun ProfileEditScreenLockedPreview() {
    DduruTheme {
        var tab by remember { mutableStateOf(ProfileEditTab.INFO) }
        var slot by remember { mutableStateOf(ProfileEditPhotoSlot.NEW) }
        var nickname by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }

        ProfileEditScreen(
            selectedTab = tab,
            onTabClick = { tab = it },
            currentPhoto = R.drawable.img_penguin_personallity,
            newPhoto = R.drawable.img_blank_profile,
            selectedPhotoSlot = slot,
            onPhotoSlotClick = { slot = it },
            photoActionLabel = "사진 변경",
            onPhotoActionClick = {},
            nickname = nickname,
            onNicknameChange = { nickname = it },
            onRandomNicknameClick = {},
            bio = bio,
            onBioChange = { bio = it },
            birthDate = "2001.05.14",
            gender = "여성",
            phoneNumber = "010-1234-3232",
            isSaveEnabled = nickname.isNotBlank(),
            onBackClick = {},
            onSaveClick = {},
            isCurrentPhotoLocked = true
        )
    }
}

@Preview(showBackground = true, name = "나가기 확인 모달")
@Composable
private fun ProfileEditScreenLeaveConfirmPreview() {
    DduruTheme {
        var tab by remember { mutableStateOf(ProfileEditTab.INFO) }
        var slot by remember { mutableStateOf(ProfileEditPhotoSlot.NEW) }
        var nickname by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }

        ProfileEditScreen(
            selectedTab = tab,
            onTabClick = { tab = it },
            currentPhoto = R.drawable.img_penguin_personallity,
            newPhoto = R.drawable.img_blank_profile,
            selectedPhotoSlot = slot,
            onPhotoSlotClick = { slot = it },
            photoActionLabel = "사진 변경",
            onPhotoActionClick = {},
            nickname = nickname,
            onNicknameChange = { nickname = it },
            onRandomNicknameClick = {},
            bio = bio,
            onBioChange = { bio = it },
            birthDate = "2001.05.14",
            gender = "여성",
            phoneNumber = "010-1234-3232",
            isSaveEnabled = nickname.isNotBlank(),
            onBackClick = {},
            onSaveClick = {},
            showLeaveConfirmDialog = true,
            onLeaveConfirmDismiss = {},
            onLeaveConfirm = {}
        )
    }
}

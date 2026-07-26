package com.gildongmu.dduru.presentation.feature.matching.createPost.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.MultiLineTextField
import com.gildongmu.dduru.presentation.component.SingleLineTextField
import com.gildongmu.dduru.presentation.feature.home.component.HomeHashtag
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.AgeRangeBar
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.DduruCalendar
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.OptionalCheckbox
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RecruitmentCount
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RoundedMatchingTextField
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.ThumbnailImageCard
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.TripDatePickerBox
import com.gildongmu.dduru.presentation.feature.matching.createPost.defaults.DateSelectionDefaults
import com.gildongmu.dduru.presentation.feature.matching.createPost.defaults.MatchingOptionButtonDefaults
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.CompanionType
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiAction
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingEditPostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentCountNum
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentGenderOption
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel.MatchingEditPostViewModel
import java.time.LocalDate

@Composable
fun MatchingEditPostRoute(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchingEditPostViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    MatchingEditPostScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSaveClick = onSaveClick,
        onDateSectionAction = viewModel::onDateSectionAction,
        onRecruitmentSectionUiStateChange = viewModel::onRecruitmentSectionUiStateChange,
        onAgeSectionUiStateChange = viewModel::onAgeSectionUiStateChange,
        onPostWritingUiStateChange = viewModel::onPostWritingUiStateChange,
        onThumbnailAndTagUiStateChange = viewModel::onThumbnailAndTagUiStateChange,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MatchingEditPostScreen(
    uiState: MatchingEditPostUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDateSectionAction: (DateSectionUiAction) -> Unit,
    onRecruitmentSectionUiStateChange: (RecruitmentScreenUiState) -> Unit,
    onAgeSectionUiStateChange: (AgeSectionUiState) -> Unit,
    onPostWritingUiStateChange: (PostWritingUiState) -> Unit,
    onThumbnailAndTagUiStateChange: (ThumbnailAndTagUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val selectedButtonColors = MatchingOptionButtonDefaults.selectedColors
    val unselectedButtonColors = MatchingOptionButtonDefaults.unselectedColors
    val disabledButtonColors = MatchingOptionButtonDefaults.disabledColors

    val recruitmentState = uiState.recruitmentSectionUiState
    val ageState = uiState.ageSectionUiState
    val postWritingState = uiState.postWritingUiState
    val thumbnailAndTagState = uiState.thumbnailAndTagUiState
    val isCompanionTypeSelectionEnabled = !recruitmentState.isCompanionTypeOptional

    val onAddTag = {
        if (thumbnailAndTagState.canAddTag) {
            onThumbnailAndTagUiStateChange(thumbnailAndTagState.addTag())
            focusManager.clearFocus()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DduruTheme.colors.gray0,
        topBar = {
            MatchingPostEditTopBar(
                isNextEnabled = uiState.isSaveEnabled,
                region = thumbnailAndTagState.regionName,
                country = thumbnailAndTagState.countryName,
                onBackClick = onBackClick,
                onNextClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "대표사진",
                        style = DduruTheme.typography.Title2,
                        color = DduruTheme.colors.gray868
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ThumbnailImageCard(
                        regionName = thumbnailAndTagState.regionName,
                        countryName = thumbnailAndTagState.countryName,
                        imageUrl = thumbnailAndTagState.previewImageUrl,
                        imageResId = thumbnailAndTagState.imageResId,
                        onChangeClick = {}
                    )
                }
            }

            item {
                Column {
                    Text(
                        text = "제목",
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray800
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SingleLineTextField(
                        value = postWritingState.title,
                        onValueChange = {
                            onPostWritingUiStateChange(postWritingState.copy(title = it))
                        },
                        maxLength = 40,
                        placeholder = "제목은 최소 5자 이상입니다."
                    )
                }
            }

            item {
                Column {
                    Text(
                        text = "자세한 설명",
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray800
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MultiLineTextField(
                        value = postWritingState.content,
                        onValueChange = {
                            onPostWritingUiStateChange(postWritingState.copy(content = it))
                        },
                        maxLength = 1000,
                        placeholder = "어떤 여행을 하고 싶은지 자유롭게 작성해 보세요. \n최소 20자 이상 작성해 주세요.",
                        modifier = Modifier.height(250.dp)
                    )
                }
            }

            item {
                Column {
                    Text(
                        text = "여행 날짜",
                        style = DduruTheme.typography.Title2,
                        color = DduruTheme.colors.gray868
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TripDatePickerBox(
                            label = "출발 날짜",
                            selectedDate = DateSelectionDefaults.formatDate(uiState.dateSectionUiState.selectedStartDate),
                            onClick = { onDateSectionAction(DateSectionUiAction.StartDateClicked) },
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            painter = painterResource(R.drawable.ic_arrow),
                            contentDescription = null,
                            tint = DduruTheme.colors.gray500,
                            modifier = Modifier.width(8.dp)
                        )

                        TripDatePickerBox(
                            label = "도착 날짜",
                            selectedDate = DateSelectionDefaults.formatDate(uiState.dateSectionUiState.selectedEndDate),
                            onClick = { onDateSectionAction(DateSectionUiAction.EndDateClicked) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    DduruCalendar(
                        selectedStartDate = uiState.dateSectionUiState.selectedStartDate,
                        selectedEndDate = uiState.dateSectionUiState.selectedEndDate,
                        onDateSelected = { date ->
                            onDateSectionAction(DateSectionUiAction.DateSelected(date))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        text = "총 모집인원",
                        style = DduruTheme.typography.Title3,
                        color = DduruTheme.colors.gray800
                    )

                    RecruitmentCount(
                        uiState = recruitmentState.recruitmentCount,
                        onCountChange = { newCount ->
                            onRecruitmentSectionUiStateChange(
                                recruitmentState.copy(
                                    recruitmentCount = recruitmentState.recruitmentCount.copy(
                                        rawCount = newCount.coerceIn(
                                            RecruitmentCountNum.MIN,
                                            RecruitmentCountNum.MAX
                                        )
                                    )
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "동행 형태",
                            style = DduruTheme.typography.Title3,
                            color = DduruTheme.colors.gray800
                        )

                        OptionalCheckbox(
                            checked = recruitmentState.isCompanionTypeOptional,
                            onCheckedChange = { isChecked ->
                                onRecruitmentSectionUiStateChange(
                                    recruitmentState.copy(
                                        isCompanionTypeOptional = isChecked,
                                        selectedCompanionType = if (isChecked) {
                                            null
                                        } else {
                                            recruitmentState.selectedCompanionType
                                        }
                                    )
                                )
                            },
                            text = "상관없어요"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CompanionType.entries.forEach { type ->
                            val isSelected = recruitmentState.selectedCompanionType == type &&
                                isCompanionTypeSelectionEnabled

                            DduruButton(
                                modifier = Modifier.weight(1f),
                                text = when (type) {
                                    CompanionType.FULL -> "전체 동행"
                                    CompanionType.PARTIAL -> "부분 동행"
                                    CompanionType.MEAL -> "식사 동행"
                                },
                                onClick = {
                                    onRecruitmentSectionUiStateChange(
                                        recruitmentState.copy(
                                            selectedCompanionType = type,
                                            isCompanionTypeOptional = false
                                        )
                                    )
                                },
                                enabled = isCompanionTypeSelectionEnabled,
                                enabledColors = if (isSelected) {
                                    selectedButtonColors
                                } else {
                                    unselectedButtonColors
                                },
                                disabledColors = disabledButtonColors
                            )
                        }
                    }

                    recruitmentState.companionTypeGuideMessage?.let { guideMessage ->
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = guideMessage,
                            style = DduruTheme.typography.Body2,
                            color = DduruTheme.colors.gray400
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "함께하고 싶은 성별",
                        style = DduruTheme.typography.Title3,
                        color = DduruTheme.colors.gray800,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RecruitmentGenderOption.entries.forEach { option ->
                            DduruButton(
                                modifier = Modifier.weight(1f),
                                text = option.label,
                                onClick = {
                                    onAgeSectionUiStateChange(ageState.copy(selectedGender = option))
                                },
                                enabledColors = if (ageState.selectedGender == option) {
                                    selectedButtonColors
                                } else {
                                    unselectedButtonColors
                                },
                                disabledColors = disabledButtonColors
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "함께하고 싶은 연령대",
                            style = DduruTheme.typography.Title3,
                            color = DduruTheme.colors.gray800
                        )

                        OptionalCheckbox(
                            checked = ageState.isAgeIrrelevant,
                            text = "상관없어요",
                            onCheckedChange = { checked ->
                                onAgeSectionUiStateChange(
                                    if (checked) {
                                        ageState.copy(
                                            selectedStartAge = null,
                                            selectedEndAge = null,
                                            isAgeIrrelevant = true
                                        )
                                    } else {
                                        ageState.copy(isAgeIrrelevant = false)
                                    }
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AgeRangeBar(
                        selectedStart = if (ageState.isAgeIrrelevant) null else ageState.selectedStartAge,
                        selectedEnd = if (ageState.isAgeIrrelevant) null else ageState.selectedEndAge,
                        onRangeChanged = { start, end ->
                            onAgeSectionUiStateChange(
                                ageState.copy(
                                    selectedStartAge = start,
                                    selectedEndAge = end,
                                    isAgeIrrelevant = false
                                )
                            )
                        },
                        enabled = !ageState.isAgeIrrelevant,
                        isAgeIrrelevant = ageState.isAgeIrrelevant,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

            item {
                Column {
                    Text(
                        text = "해시태그",
                        style = DduruTheme.typography.Title2,
                        color = DduruTheme.colors.gray868
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RoundedMatchingTextField(
                        value = thumbnailAndTagState.tagInput,
                        onValueChange = {
                            onThumbnailAndTagUiStateChange(thumbnailAndTagState.copy(tagInput = it))
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_hashtag),
                                contentDescription = null,
                                tint = DduruTheme.colors.gray400
                            )
                        },
                        placeholder = {
                            Text(
                                text = "해시태그 입력...",
                                style = DduruTheme.typography.Body1,
                                color = DduruTheme.colors.gray500
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { onAddTag() })
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "한글 4자 또는 영문/숫자 7자 이하로 입력해 주세요.",
                        style = DduruTheme.typography.Caption2,
                        color = DduruTheme.colors.gray400
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "추가된 태그",
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray800
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        thumbnailAndTagState.addedTags.forEach { addedTag ->
                            HomeHashtag(
                                text = addedTag,
                                textColor = DduruTheme.colors.gray700,
                                textStyle = DduruTheme.typography.Caption1,
                                backgroundColor = DduruTheme.colors.gray200,
                                contentPadding = PaddingValues(12.dp),
                                trailingIcon = {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clickable {
                                                onThumbnailAndTagUiStateChange(
                                                    thumbnailAndTagState.removeTag(addedTag)
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            modifier = Modifier.fillMaxWidth(),
                                            painter = painterResource(id = R.drawable.ic_close),
                                            contentDescription = null,
                                            tint = DduruTheme.colors.gray700
                                        )
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "인기 태그",
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray800
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.popularTags.forEach { popularTag ->
                            HomeHashtag(
                                text = popularTag,
                                textColor = DduruTheme.colors.gray700,
                                textStyle = DduruTheme.typography.Caption1,
                                backgroundColor = DduruTheme.colors.gray200,
                                contentPadding = PaddingValues(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchingPostEditTopBar(
    isNextEnabled: Boolean,
    region: String?,
    country: String?,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .border(
                    1.dp,
                    DduruTheme.colors.gray0.copy(alpha = 0.3f),
                    CircleShape,
                )
                .dduruDualShadow(
                    firstAlpha = 0.8f,
                    secondAlpha = 0.16f,
                    shape = CircleShape
                )
                .background(
                    DduruTheme.colors.gray0,
                    CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(DduruTheme.colors.gray0, CircleShape),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "여행방 수정",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.black
            )

            if (region != null || country != null) {
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    region?.let {
                        Text(
                            text = it,
                            style = DduruTheme.typography.Micro,
                            color = DduruTheme.colors.gray400
                        )
                    }

                    if (region != null && country != null) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(DduruTheme.colors.gray200, CircleShape)
                        )
                    }

                    country?.let {
                        Text(
                            text = it,
                            style = DduruTheme.typography.Micro,
                            color = DduruTheme.colors.gray400
                        )
                    }
                }
            }
            }

        DduruButton(
            text = "다음",
            onClick = onNextClick,
            enabled = isNextEnabled,
            enabledColors = DduruButtonDefaults.filledEnabledColors,
            disabledColors = DduruButtonDefaults.whiteDisabledColors,
            textStyle = DduruTheme.typography.Title2,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingPostEditScreenPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxSize()
        ) {
            MatchingEditPostScreen(
                uiState = MatchingEditPostUiState(
                    popularTags = MatchingCreatePostMockData.popularTags
                ),
                onBackClick = {},
                onSaveClick = {},
                onDateSectionAction = {},
                onRecruitmentSectionUiStateChange = {},
                onAgeSectionUiStateChange = {},
                onPostWritingUiStateChange = {},
                onThumbnailAndTagUiStateChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingPostEditScreenEnabledNextButtonPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxSize()
        ) {
            MatchingEditPostScreen(
                uiState = MatchingEditPostUiState(
                    dateSectionUiState = DateSectionUiState(
                        selectedStartDate = LocalDate.now(),
                        selectedEndDate = LocalDate.now().plusDays(3)
                    ),
                    recruitmentSectionUiState = RecruitmentScreenUiState(
                        selectedCompanionType = CompanionType.FULL
                    ),
                    ageSectionUiState = AgeSectionUiState(
                        selectedGender = RecruitmentGenderOption.ANY,
                        isAgeIrrelevant = true
                    ),
                    postWritingUiState = PostWritingUiState(
                        title = "후쿠오카 여행방",
                        content = "맛집과 관광지를 함께 둘러볼 여행 메이트를 찾고 있어요."
                    ),
                    popularTags = MatchingCreatePostMockData.popularTags
                ),
                onBackClick = {},
                onSaveClick = {},
                onDateSectionAction = {},
                onRecruitmentSectionUiStateChange = {},
                onAgeSectionUiStateChange = {},
                onPostWritingUiStateChange = {},
                onThumbnailAndTagUiStateChange = {}
            )
        }
    }
}

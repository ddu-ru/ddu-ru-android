package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.component.MultiLineTextField
import com.gildongmu.dduru.presentation.feature.mypage.component.ConfirmDialog
import com.gildongmu.dduru.presentation.feature.mypage.component.InquiryCategoryGrid
import com.gildongmu.dduru.presentation.feature.mypage.component.InquiryGuideNotes
import com.gildongmu.dduru.presentation.feature.mypage.component.PhotoAttachField
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryCategory

private const val INQUIRY_CONTENT_MAX_LENGTH = 500

@Composable
fun InquiryWriteScreen(
    selectedCategory: InquiryCategory?,
    content: String,
    attachedFileName: String?,
    guideNotes: List<String>,
    onBackClick: () -> Unit,
    onCategoryClick: (InquiryCategory) -> Unit,
    onContentChange: (String) -> Unit,
    onAttachClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    showSubmitDialog: Boolean = false,
    onSubmitDialogDismiss: () -> Unit = {},
    onSubmitConfirm: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            FloatingBackTopBar(
                title = "문의하기",
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "궁금한 점이나 불편한 점을 남겨주세요",
                        style = DduruTheme.typography.H1,
                        color = DduruTheme.colors.gray900
                    )
                    Text(
                        text = "문의 유형을 선택하고 내용을 남겨주시면\n확인 후 자세히 답변드릴게요.",
                        style = DduruTheme.typography.Body3,
                        color = DduruTheme.colors.gray600
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    InquirySection(title = "문의 유형") {
                        InquiryCategoryGrid(
                            selectedCategory = selectedCategory,
                            onCategoryClick = onCategoryClick
                        )
                    }

                    InquirySection(title = "상세 내용") {
                        MultiLineTextField(
                            value = content,
                            onValueChange = onContentChange,
                            maxLength = INQUIRY_CONTENT_MAX_LENGTH,
                            placeholder = "문의 내용을 입력해주세요",
                            modifier = Modifier.height(220.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "사진 첨부",
                                style = DduruTheme.typography.Title3,
                                color = DduruTheme.colors.gray700
                            )
                            Text(
                                text = "선택",
                                style = DduruTheme.typography.Title3,
                                color = DduruTheme.colors.gray500
                            )
                        }

                        PhotoAttachField(
                            fileName = attachedFileName,
                            onClick = onAttachClick
                        )
                    }

                    InquiryGuideNotes(items = guideNotes)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DduruTheme.colors.gray50)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                DduruButton(
                    text = "제출하기",
                    onClick = onSubmitClick,
                    enabledColors = DduruButtonEnabledColors(
                        backgroundColor = DduruTheme.colors.gray900,
                        textColor = DduruTheme.colors.gray0,
                        borderColor = DduruTheme.colors.gray0.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
        }

        if (showSubmitDialog) {
            ConfirmDialog(
                title = "문의를 제출할까요?",
                description = "답변이 등록되면 알림으로 안내드려요.\n답변까지 1~3일 정도 걸릴 수 있어요.",
                onDismiss = onSubmitDialogDismiss,
                onConfirm = onSubmitConfirm,
                dismissText = "취소",
                confirmText = "제출",
                confirmBackgroundColor = DduruTheme.colors.gray900,
                confirmTextColor = DduruTheme.colors.gray0
            )
        }
    }
}

@Composable
private fun InquirySection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = title,
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray700
        )
        content()
    }
}

private val DefaultGuideNotes = listOf(
    "처리 결과는 알림으로 안내드립니다",
    "처리까지 1-3일 소요될 수 있습니다"
)

@Composable
private fun InquiryWritePreviewHost(
    initialCategory: InquiryCategory? = null,
    initialAttachedFileName: String? = null,
    guideNotes: List<String> = DefaultGuideNotes,
    showSubmitDialog: Boolean = false
) {
    var category by remember { mutableStateOf(initialCategory) }
    var content by remember { mutableStateOf("") }

    InquiryWriteScreen(
        selectedCategory = category,
        content = content,
        attachedFileName = initialAttachedFileName,
        guideNotes = guideNotes,
        onBackClick = {},
        onCategoryClick = { category = it },
        onContentChange = { content = it },
        onAttachClick = {},
        onSubmitClick = {},
        showSubmitDialog = showSubmitDialog
    )
}

@Preview(showBackground = true, heightDp = 966, name = "문의하기")
@Composable
private fun InquiryWriteScreenPreview() {
    DduruTheme { InquiryWritePreviewHost() }
}

@Preview(showBackground = true, heightDp = 966, name = "유형 선택 + 사진 첨부")
@Composable
private fun InquiryWriteScreenAttachedPreview() {
    DduruTheme {
        InquiryWritePreviewHost(
            initialCategory = InquiryCategory.SERVICE,
            initialAttachedFileName = "IMG_12831.png"
        )
    }
}

@Preview(showBackground = true, heightDp = 966, name = "제출 확인 모달")
@Composable
private fun InquiryWriteScreenDialogPreview() {
    DduruTheme {
        InquiryWritePreviewHost(
            initialCategory = InquiryCategory.SERVICE,
            initialAttachedFileName = "IMG_12831.png",
            guideNotes = listOf("허위 신고 시 계정 제재가 있을 수 있습니다") + DefaultGuideNotes,
            showSubmitDialog = true
        )
    }
}

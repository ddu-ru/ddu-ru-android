package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.MultiLineTextField
import com.gildongmu.dduru.presentation.component.SingleLineTextField
import com.gildongmu.dduru.presentation.feature.matching.createPost.model.StepHeaderUiModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState

@Composable
fun PostWritingSection(
    modifier: Modifier = Modifier
) {
    var uiState by remember { mutableStateOf(PostWritingUiState()) }

    val stepHeaderPostWriting = remember {
        StepHeaderUiModel(
            step = 4,
            title = "제목과 내용을 입력해주세요",
            subtitle = "동행을 찾기 위한 매력적인 글을 작성해 보세요"
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        CreatePostStepHeader(
            uiModel = stepHeaderPostWriting,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.weight(24f))

        Text(
            text = "제목",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(8.dp))

        SingleLineTextField(
            value = uiState.title,
            onValueChange = { uiState = uiState.copy(title = it) },
            maxLength = 40,
            placeholder = "제목은 최소 5자 이상입니다."
        )

        Spacer(modifier = Modifier.weight(24f))

        Text(
            text = "자세한 설명",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultiLineTextField(
            modifier = Modifier.weight(250f),
            value = uiState.content,
            onValueChange = { uiState = uiState.copy(content = it) },
            maxLength = 1000,
            placeholder = "어떤 여행을 하고 싶은지 자유롭게 작성해 보세요. \n최소 20자 이상 작성해 주세요.",
        )

        Spacer(modifier = Modifier.weight(100f))
    }
}

@Preview(showBackground = true)
@Composable
private fun PostWritingSectionPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.fillMaxWidth()
        ) {
            PostWritingSection(
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

package com.gildongmu.dduru.presentation.feature.journey.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gildongmu.dduru.core.common.util.rememberGalleryLauncher
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyPostComposeTopBar
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyPostContentField
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyPostPhotoSection
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyPostComposeUiState
import com.gildongmu.dduru.presentation.feature.journey.state.MAX_POST_LENGTH
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyPostComposeViewModel

@Composable
fun JourneyPostComposeRoute(
    modifier: Modifier = Modifier,
    viewModel: JourneyPostComposeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val galleryLauncher = rememberGalleryLauncher(viewModel::onPhotoSelected)

    JourneyPostComposeScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onContentChange = viewModel::onContentChange,
        onAddPhotoClick = { galleryLauncher.launch("image/*") },
        onRemovePhotoClick = viewModel::onRemovePhotoClick,
        onSubmitClick = viewModel::onSubmitClick,
        modifier = modifier,
    )

}

@Composable
private fun JourneyPostAuthor(
    username: String,
    createdAt: String,
    profileImageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(32.dp).clip(CircleShape),
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = username, style = DduruTheme.typography.Caption1, color = DduruTheme.colors.gray846)
            Text(text = createdAt, style = DduruTheme.typography.Caption2, color = DduruTheme.colors.gray500)
        }
    }
}

@Composable
fun JourneyPostComposeScreen(
    uiState: JourneyPostComposeUiState,
    onBackClick: () -> Unit,
    onContentChange: (String) -> Unit,
    onAddPhotoClick: () -> Unit,
    onRemovePhotoClick: (Int) -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
    ) {
        JourneyPostComposeTopBar(
            enabled = uiState.canSubmit && !uiState.isOverMaxLength,
            onBackClick = onBackClick,
            onSubmitClick = onSubmitClick,
        )

        Spacer(modifier = Modifier.height(30.dp))

        JourneyPostAuthor(
            username = uiState.username,
            createdAt = uiState.createdAt,
            profileImageUrl = uiState.profileImageUrl,
        )

        Spacer(modifier = Modifier.height(16.dp))

        JourneyPostContentField(
            value = uiState.content,
            onValueChange = onContentChange,
            isError = uiState.isOverMaxLength,
            modifier = Modifier.fillMaxHeight(1f / 3f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "사진 추가",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(16.dp))

        JourneyPostPhotoSection(
            photos = uiState.selectedPhotos,
            onAddPhotoClick = onAddPhotoClick,
            onRemovePhotoClick = onRemovePhotoClick,
        )
    }
}

@Composable
private fun JourneyPostComposePreviewContent(
    uiState: JourneyPostComposeUiState,
) {
    var selectedPhotos by remember { mutableStateOf(uiState.selectedPhotos) }

    JourneyPostComposeScreen(
        uiState = uiState.copy(selectedPhotos = selectedPhotos),
        onBackClick = {},
        onContentChange = {},
        onAddPhotoClick = {},
        onRemovePhotoClick = {},
        onSubmitClick = {},
    )

}

@Preview(
    name = "게시글 작성 - 기본",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
)
@Composable
private fun JourneyPostComposeScreenPreview() {
    DduruTheme {
        JourneyPostComposePreviewContent(
            uiState = JourneyPostComposeUiState(username = "길동", createdAt = "방금 전"),
        )
    }
}

@Preview(
    name = "게시글 작성 - 글자 수 초과",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
)
@Composable
private fun JourneyPostComposeScreenErrorPreview() {
    DduruTheme {
        JourneyPostComposePreviewContent(
            uiState = JourneyPostComposeUiState(
                username = "길동",
                createdAt = "방금 전",
                content = "가".repeat(MAX_POST_LENGTH + 1),
            ),
        )
    }
}

@Preview(
    name = "게시글 작성 - 사진 추가됨",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
)
@Composable
private fun JourneyPostComposeScreenWithPhotosPreview() {
    DduruTheme {
        JourneyPostComposePreviewContent(
            uiState = JourneyPostComposeUiState(
                username = "길동",
                createdAt = "방금 전",
                content = "제주에서 보낸 첫날, 정말 즐거웠어요!",
                selectedPhotos = previewSelectedPhotos(),
            ),
        )
    }
}

private fun previewSelectedPhotos(): List<Uri> = listOf(
    Uri.parse("android.resource://com.gildongmu.dduru/drawable/img_journey_preview_coast"),
    Uri.parse("android.resource://com.gildongmu.dduru/drawable/img_journey_preview_coast"),
    Uri.parse("android.resource://com.gildongmu.dduru/drawable/img_journey_preview_coast"),
)

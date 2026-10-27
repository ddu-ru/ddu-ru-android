package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

const val MAX_CHAT_PHOTO_COUNT = 10

fun toggledPhotoSelection(selectedIndices: List<Int>, index: Int): List<Int> = when {
    index in selectedIndices -> selectedIndices - index
    selectedIndices.size >= MAX_CHAT_PHOTO_COUNT -> selectedIndices
    else -> selectedIndices + index
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPhotoPickerBottomSheet(
    photoUris: List<String>,
    selectedIndices: List<Int>,
    onPhotoClick: (Int) -> Unit,
    onSendClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = DduruTheme.colors.gray0,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        modifier = modifier
    ) {
        ChatPhotoPickerContent(
            photoUris = photoUris,
            selectedIndices = selectedIndices,
            onPhotoClick = onPhotoClick,
            onSendClick = onSendClick,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun ChatPhotoPickerContent(
    photoUris: List<String>,
    selectedIndices: List<Int>,
    onPhotoClick: (Int) -> Unit,
    onSendClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(725.dp)
    ) {
        PhotoPickerHeader(
            onSendClick = onSendClick,
            onDismiss = onDismiss
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(photoUris) { index, uri ->
                val order = selectedIndices.indexOf(index)
                PhotoPickerItem(
                    uri = uri,
                    selectedOrder = if (order >= 0) order + 1 else null,
                    onClick = { onPhotoClick(index) }
                )
            }
        }
    }
}

@Composable
private fun PhotoPickerHeader(
    onSendClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val sendShape = RoundedCornerShape(99.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
            .height(48.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_x_gray),
            contentDescription = "닫기",
            tint = DduruTheme.colors.gray500,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .noRippleClickable(onClick = onDismiss)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "최근 사진",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.black
            )
            Text(
                text = "최대 ${MAX_CHAT_PHOTO_COUNT}장 사진을 선택합니다.",
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray600
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .height(48.dp)
                .widthIn(min = 64.dp)
                .dduruDualShadow(
                    firstAlpha = 0.10f,
                    secondAlpha = 0.08f,
                    shape = sendShape
                )
                .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), sendShape)
                .background(DduruTheme.colors.primary, sendShape)
                .noRippleClickable(onClick = onSendClick)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "전송",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Composable
private fun PhotoPickerItem(
    uri: String,
    selectedOrder: Int?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .noRippleClickable(onClick = onClick)
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(DduruTheme.colors.gray200)
        )
        PhotoSelectionBadge(
            order = selectedOrder,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
        )
    }
}

@Composable
private fun PhotoSelectionBadge(
    order: Int?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(
                if (order != null) DduruTheme.colors.primary else DduruTheme.colors.gray0.copy(alpha = 0.6f)
            )
            .border(3.dp, DduruTheme.colors.gray0, CircleShape)
    ) {
        if (order != null) {
            Text(
                text = order.toString(),
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatPhotoPickerContentPreview() {
    DduruTheme {
        var selected by remember { mutableStateOf(listOf(0, 1, 2, 3, 4)) }
        ChatPhotoPickerContent(
            photoUris = List(15) { "" },
            selectedIndices = selected,
            onPhotoClick = { selected = toggledPhotoSelection(selected, it) },
            onSendClick = {},
            onDismiss = {}
        )
    }
}

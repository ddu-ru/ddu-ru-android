package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class ChatAttachOption(
    @DrawableRes val iconRes: Int,
    val label: String
) {
    PHOTO(R.drawable.ic_images, "사진"),
    CAMERA(R.drawable.ic_camera, "카메라"),
    MAP(R.drawable.ic_map_pin, "지도"),
    POLL(R.drawable.ic_check_square, "투표")
}

@Composable
fun ChatAttachPanel(
    onOptionClick: (ChatAttachOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        ChatAttachOption.entries.forEach { option ->
            ChatAttachOptionItem(
                option = option,
                onClick = { onOptionClick(option) }
            )
        }
    }
}

@Composable
private fun ChatAttachOptionItem(
    option: ChatAttachOption,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .noRippleClickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .background(DduruTheme.colors.gray200, RoundedCornerShape(28.dp))
        ) {
            Image(
                painter = painterResource(option.iconRes),
                contentDescription = option.label,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = option.label,
            style = DduruTheme.typography.Body3,
            color = DduruTheme.colors.gray846,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatAttachPanelPreview() {
    DduruTheme {
        ChatAttachPanel(
            onOptionClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

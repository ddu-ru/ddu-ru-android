package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PhotoAttachField(
    fileName: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = DduruTheme.colors.gray0.copy(alpha = 0.9f), shape = shape)
            .border(width = 0.8.dp, color = DduruTheme.colors.gray200, shape = shape)
            .noRippleClickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (fileName == null) {
            Icon(
                painter = painterResource(id = R.drawable.ic_images),
                contentDescription = null,
                tint = DduruTheme.colors.gray900,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = DduruTheme.colors.acceptGreen,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = fileName ?: "사진 첨부하기",
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray900,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoAttachFieldPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PhotoAttachField(fileName = null, onClick = {})
                PhotoAttachField(fileName = "IMG_12831.png", onClick = {})
            }
        }
    }
}

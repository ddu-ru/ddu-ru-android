package com.gildongmu.dduru.presentation.feature.tripgroup.creative.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun CustomToast(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
) {
    val toastShape = RoundedCornerShape(16.dp)

    val shadowColor1 = DduruTheme.colors.black.copy(alpha = 0.2f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(shape = toastShape) {
                radius = 40f
                spread = 10f
                color = shadowColor1
                offset = Offset(0f, 0f)
            }
            .background(
                color = DduruTheme.colors.gray928.copy(alpha = 0.4f),
                shape = toastShape,
            )
            .border(
                width = 1.dp,
                color =  DduruTheme.colors.gray0.copy(alpha = 0.3f),
                shape = toastShape
            ),
        )
    {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = DduruTheme.colors.secondary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = DduruTheme.colors.primary,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = DduruTheme.typography.Title2,
                    color = DduruTheme.colors.gray0
                )
                Text(
                    text = message,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray300
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CustomToastPreview() {
        CustomToast(
            title = "게시글 업로드 완료",
            message = "동행 시작을 기다리고 있어요",
            modifier = Modifier
                .padding(top = 60.dp, start = 16.dp, end = 16.dp, bottom = 60.dp)
        )
}

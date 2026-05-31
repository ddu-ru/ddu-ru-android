package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun LocationSearchCardComponent(
    region: String,
    country: String,
    modifier: Modifier = Modifier,
    imageResId: Int? = null,
    onClick: () -> Unit = {},
) {
    val hasImage = imageResId != null
    val resolvedImageResId = imageResId ?: R.drawable.ic_home_location

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .border(
                width = 0.6.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(all = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = DduruTheme.colors.gray100, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (hasImage) {
                Image(
                    painter = painterResource(id = resolvedImageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home_location),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray600,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = region,
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.black
            )
            Text(
                text = country,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray500
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationSearchCardComponentNoImagePreview() {
    DduruTheme {
        LocationSearchCardComponent(
            region = "제주도",
            country = "국내",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationSearchCardImageComponentPreview() {
    DduruTheme {
        LocationSearchCardComponent(
            region = "후쿠오카",
            country = "일본",
            imageResId = R.drawable.fukuoka,
            modifier = Modifier.padding(16.dp)
        )
    }
}

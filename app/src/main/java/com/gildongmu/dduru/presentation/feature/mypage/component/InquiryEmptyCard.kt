package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun InquiryEmptyCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(312.dp)
            .background(color = DduruTheme.colors.gray0, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = DduruTheme.colors.gray200, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Box(modifier = Modifier.size(64.dp)) {
            Image(
                painter = painterResource(id = R.drawable.img_inquiry_empty_penguin),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.76.dp, top = 2.72.dp, end = 5.6.dp, bottom = 2.72.dp)
            )
        }

        Text(
            text = "아직 문의한 내용이 없어요.\n궁금한 점이 생기면 언제든 문의해주세요.",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(287.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InquiryEmptyCardPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            InquiryEmptyCard(modifier = Modifier.padding(16.dp))
        }
    }
}

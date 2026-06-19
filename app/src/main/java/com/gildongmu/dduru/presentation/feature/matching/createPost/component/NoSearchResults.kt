package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun NoSearchResults(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.img_penguin_ver1),
                contentDescription = null,
                modifier = Modifier.width(maxWidth * 0.12f),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "검색 결과가 없습니다.",
            modifier = Modifier.fillMaxWidth(),
            color = DduruTheme.colors.gray700,
            style = DduruTheme.typography.H2,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "도시, 국가 이름을 입력해보세요.\n 단어가 한글 혹은 영어로 정확한지 확인해보세요.",
            modifier = Modifier.fillMaxWidth(),
            color = DduruTheme.colors.gray500,
            style = DduruTheme.typography.Caption2,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun NoSearchResultsPreview() {
    DduruTheme {
        NoSearchResults()
    }
}

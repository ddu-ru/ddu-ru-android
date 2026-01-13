package com.gildongmu.dduru_android.presentation.feature.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.component.LoadingFlashing
import com.gildongmu.dduru_android.presentation.component.SpeechBubble

@Composable
fun OnboardingAnalysisScreen(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(DduruTheme.colors.Gray50)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SpeechBubble(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,) {
                LoadingFlashing()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    //닉네임 받아와서 출력해야함(나중에 수정)
                    text = "답변하신 내용을 바탕으로\n 겨울이 오길 님의 여행 성격을 \n분석하고 있어요!",
                    style = DduruTheme.typography.BodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_penguin_3),
            contentDescription = "뚜비 캐릭터",
            modifier = Modifier
                .width(211.dp)
                .height(277.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

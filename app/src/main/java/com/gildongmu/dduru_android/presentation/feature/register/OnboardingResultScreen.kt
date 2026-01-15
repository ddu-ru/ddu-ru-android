package com.gildongmu.dduru_android.presentation.feature.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.component.SpeechBubble

@Composable
fun OnboardingResultScreen(
    modifier: Modifier
){
    Column (
        modifier = modifier
            .background(DduruTheme.colors.Gray50)
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            text = "내 여행 성격",
            style = DduruTheme.typography.TitleMedium,
            textAlign = TextAlign.Center,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            SpeechBubble {
                Text(
                    modifier = Modifier.padding(horizontal = 69.dp),
                    text = "모두가 편안해야\n내가 편해요!",
                    style = DduruTheme.typography.BodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            Image(
                painter = painterResource(id = R.drawable.img_penguin_personallity),
                contentDescription = "뚜비 캐릭터",
                modifier = Modifier
                    .width(202.dp)
                    .height(213.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "포근포근형",
                style = DduruTheme.typography.TitleMedium,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier
                    .padding(8.dp, bottom = 24.dp),
                text = "#따듯함 #세심함 #여행즐김러",
                style = DduruTheme.typography.LabelMedium,
                textAlign = TextAlign.Center,
                color = DduruTheme.colors.Gray600
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DduruTheme.colors.Gray100)
                    .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "- 어쩌구저쩌구 설명",
                        style = DduruTheme.typography.BodyMedium,
                        textAlign = TextAlign.Center,
                        color = DduruTheme.colors.Gray700
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "- 어쩌구저쩌구 설명",
                        style = DduruTheme.typography.BodyMedium,
                        textAlign = TextAlign.Center,
                        color = DduruTheme.colors.Gray700
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "- 어쩌구저쩌구 설명",
                        style = DduruTheme.typography.BodyMedium,
                        textAlign = TextAlign.Center,
                        color = DduruTheme.colors.Gray700
                    )
                }
            }
        }
        DduruButton(
            "네 맞아요!",
            {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

package com.gildongmu.dduru_android.presentation.feature.register.survey.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.gildongmu.dduru_android.presentation.component.SpeechBubble


@Composable

fun SurveyResultDetail(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        SpeechBubble {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "모두가 편안해야\n내가 편해요!", //avatarDescription
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
            text = "포근포근형", //avatarName
            style = DduruTheme.typography.TitleMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .padding(8.dp, bottom = 24.dp),
            text = "#따듯함 #세심함 #여행즐김러", //tags
            style = DduruTheme.typography.LabelMedium,
            textAlign = TextAlign.Center,
            color = DduruTheme.colors.Gray600
        )
        Spacer(modifier = Modifier.padding(32.dp))
        ResultDescriptionBox(listOf("어쩌구 저쩌구 설명...","어쩌구 저쩌구 설명...","어쩌구 저쩌구 설명..."))
    }
}

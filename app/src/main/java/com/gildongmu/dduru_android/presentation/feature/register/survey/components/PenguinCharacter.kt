package com.gildongmu.dduru_android.presentation.feature.register.survey.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun PenguinCharacter(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.img_survey_penguin),
        contentDescription = "펭귄 캐릭터",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .background(
                color = DduruTheme.colors.Gray50,
                shape = RoundedCornerShape(99.dp)
            )
            // 3. 크기 및 패딩
            .size(width = 48.dp, height = 60.dp)
            .padding(8.dp)
    )
}

package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailImageHeader(
    modifier: Modifier = Modifier,
    destination: String,
    country: String,
    imageUrl: String? =null,
    imageRes: Int = R.drawable.img_container
) {
    // 1. 전체 컨테이너
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp)) // 전체 둥근 모서리 적용
    ) {
        // 2. 배경 이미지 (이 영역 안에만 나타납니다)
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 이미지를 120dp 높이에 맞춰 자릅니다.
        )

        // 3. 반투명 오버레이 (이미지 위에 덮어씌웁니다)
        Box(
            modifier = Modifier
                .fillMaxSize() // 부모(120dp) 크기만큼 꽉 채웁니다.
                .background(DduruTheme.colors.BlackAlpha30) // 💡 투명도 30% 검정색 적용
                .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = destination,
                    style = DduruTheme.typography.TitleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DduruTheme.colors.Gray0
                    )
                )
                Text(
                    text = country,
                    modifier = Modifier
                        .alpha(0.7f)
                        .padding(bottom = 4.dp),
                    style = DduruTheme.typography.BodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DduruTheme.colors.Gray0
                    )
                )
            }
        }
    }
}

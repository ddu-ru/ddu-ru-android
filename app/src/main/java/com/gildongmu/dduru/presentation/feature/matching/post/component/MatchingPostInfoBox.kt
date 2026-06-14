package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun CommonBoxComponent(
    recruitmentType: String,
    recruitmentCount: String,
    recruitCapacity: String,
    minAge: Int,
    maxAge: Int,
    preferredGender: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = recruitmentType,
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "모집 유형",
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray600
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            color = DduruTheme.colors.gray200,
            thickness = 1.dp
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$recruitmentCount/$recruitCapacity",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "모집 인원",
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray600
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            color = DduruTheme.colors.gray200,
            thickness = 1.dp
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$minAge-${maxAge}세 $preferredGender",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "모집 조건",
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray600
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonBoxComponentPreview() {
    DduruTheme {
        CommonBoxComponent(
            recruitmentType = "전체 동행",
            recruitmentCount = "1",
            recruitCapacity = "4",
            minAge = 20,
            maxAge = 30,
            preferredGender = "남성",
            modifier = Modifier.padding(16.dp)
        )
    }
}

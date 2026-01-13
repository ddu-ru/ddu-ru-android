package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailBudgetSection(
    minBudget: Int?,
    maxBudget: Int?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.Gray100, RoundedCornerShape(16.dp))
            .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 라벨 영역: 아이콘 + 예상 경비
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_money_wavy), // MoneyWavy 아이콘
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = "예상 경비",
                style = DduruTheme.typography.HomeFilter.copy(
                    fontWeight = FontWeight.Bold,
                    color = DduruTheme.colors.Gray700
                )
            )
        }

        // 값 영역: 금액 + 배지
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val budgetText = if (minBudget != null && maxBudget != null) {
                "${minBudget}만원~ ${maxBudget}만원"
            } else {
                "예산 협의 필요"
            }

            Text(
                text = budgetText,
                style = DduruTheme.typography.LabelLarge.copy( // 16sp 적용
                    color = DduruTheme.colors.Gray800,
                    fontWeight = FontWeight.Bold
                )
            )

            // "같이 상의해봐요" 배지 (Variables.secondary 배경색 사용)
            DetailSmallBadge(text = "같이 상의해봐요", isPrimary = true)
        }
    }
}

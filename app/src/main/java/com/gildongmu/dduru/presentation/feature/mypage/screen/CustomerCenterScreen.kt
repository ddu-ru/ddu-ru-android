package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FilterChipRow
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.SearchTextField
import com.gildongmu.dduru.presentation.feature.mypage.component.FaqItemCard
import com.gildongmu.dduru.presentation.feature.mypage.component.PillSegmentedControl
import com.gildongmu.dduru.presentation.feature.mypage.model.FaqUiModel

private val CustomerCenterSegments = listOf("FAQ", "최근 문의")

@Composable
fun CustomerCenterScreen(
    segmentIndex: Int,
    categories: List<String>,
    selectedCategoryIndex: Int,
    searchQuery: String,
    faqs: List<FaqUiModel>,
    expandedFaqIds: Set<Long>,
    onBackClick: () -> Unit,
    onSegmentClick: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onFaqClick: (Long) -> Unit,
    onInquiryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "고객센터",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            PillSegmentedControl(
                tabs = CustomerCenterSegments,
                selectedIndex = segmentIndex,
                onTabClick = onSegmentClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "무엇을 도와드릴까요?",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray900
            )

            Spacer(modifier = Modifier.height(24.dp))

            SearchTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                placeholderMessage = "FAQ 검색",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            FilterChipRow(
                labels = categories,
                selectedIndex = selectedCategoryIndex,
                onChipClick = onCategoryClick
            )

            Spacer(modifier = Modifier.height(19.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                faqs.forEach { faq ->
                    FaqItemCard(
                        question = faq.question,
                        answer = faq.answer,
                        isExpanded = faq.id in expandedFaqIds,
                        onClick = { onFaqClick(faq.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "찾는 내용이 없나요?",
                style = DduruTheme.typography.Body4,
                color = DduruTheme.colors.gray500,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            DduruButton(
                text = "새 문의하기",
                onClick = onInquiryClick,
                enabledColors = DduruButtonEnabledColors(
                    backgroundColor = DduruTheme.colors.gray958,
                    textColor = DduruTheme.colors.gray0,
                    borderColor = DduruTheme.colors.gray0.copy(alpha = 0.1f)
                ),
                textStyle = DduruTheme.typography.Title2,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

private val PreviewFaqs = List(6) { index ->
    FaqUiModel(
        id = index.toLong(),
        question = "Q. 동행 신청은 어떻게 하나요?",
        answer = "홈이나 매칭 탭에서 원하는 여행을 선택하고 ‘동행 신청’을 눌러주세요."
    )
}

@Preview(showBackground = true, heightDp = 1209, name = "고객센터 FAQ")
@Composable
private fun CustomerCenterScreenPreview() {
    DduruTheme {
        var segmentIndex by remember { mutableStateOf(0) }
        var categoryIndex by remember { mutableStateOf(1) }
        var query by remember { mutableStateOf("") }
        var expandedIds by remember { mutableStateOf(setOf(0L, 1L, 2L)) }

        CustomerCenterScreen(
            segmentIndex = segmentIndex,
            categories = listOf("전체", "서비스", "계정", "매칭", "보안"),
            selectedCategoryIndex = categoryIndex,
            searchQuery = query,
            faqs = PreviewFaqs,
            expandedFaqIds = expandedIds,
            onBackClick = {},
            onSegmentClick = { segmentIndex = it },
            onCategoryClick = { categoryIndex = it },
            onSearchQueryChange = { query = it },
            onSearchClick = {},
            onFaqClick = { id ->
                expandedIds = if (id in expandedIds) expandedIds - id else expandedIds + id
            },
            onInquiryClick = {}
        )
    }
}

package com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val InfoBulletItems = listOf(
    "티켓 1장으로 게시글 1개에 적용할 수 있어요",
    "슈퍼호스트는 적용한 시점부터 3일 동안 유지돼요",
    "한 번에 하나의 게시글에만 적용할 수 있어요",
    "적용 중인 게시글과 남은 기간은 작성글 관리에서 확인할 수 있어요"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperhostInfoBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = DduruTheme.colors.gray0,
        scrimColor = DduruTheme.colors.black.copy(alpha = 0.5f),
        dragHandle = null
    ) {
        SuperhostInfoSheetContent(onDismiss = onDismiss)
    }
}

@Composable
internal fun SuperhostInfoSheetSurface(modifier: Modifier = Modifier) {
    SuperhostInfoSheetContent(
        onDismiss = {},
        modifier = modifier.background(DduruTheme.colors.gray0, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    )
}

@Composable
private fun SuperhostInfoSheetContent(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 40.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .background(DduruTheme.colors.primary, CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_superhost),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "슈퍼호스트란?",
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray900
                )

                Text(
                    text = "내 모집글을 더 많은 동행에게 보여줄 수 있는 권한이에요",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600
                )

                Text(
                    text = buildAnnotatedString {
                        append("슈퍼호스트를 적용한 게시글은 ")
                        withStyle(SpanStyle(color = DduruTheme.colors.gray700)) {
                            append("일반 게시글보다 더 눈에 잘 띄는 위치에 우선 노출")
                        }
                        append("돼요. 동행을 찾는 사용자에게 게시글이 노출될 기회가 늘어나 더 빠르게 동행을 모집할 수 있어요")
                    },
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600
                )

                Column {
                    InfoBulletItems.forEach { item ->
                        Row {
                            Text(
                                text = "•",
                                style = DduruTheme.typography.Body2.copy(lineHeight = 25.2.sp),
                                color = DduruTheme.colors.gray600,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 7.dp)
                            )
                            Text(
                                text = item,
                                style = DduruTheme.typography.Body2.copy(lineHeight = 25.2.sp),
                                color = DduruTheme.colors.gray600
                            )
                        }
                    }
                }
            }
        }

        SuperhostSheetOutlinedButton(
            text = "확인",
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, name = "슈퍼호스트 안내")
@Composable
private fun SuperhostInfoSheetPreview() {
    DduruTheme {
        SuperhostInfoSheetContent(onDismiss = {})
    }
}

package com.gildongmu.dduru.presentation.feature.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = DduruTheme.colors.gray0,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        PolicyBottomSheetContent(
            onClose = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}

@Composable
private fun PolicyBottomSheetContent(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.875f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "뚜르 서비스 약관",
                style = DduruTheme.typography.Title1,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onClose,
                modifier =
                    Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource( R.drawable.ic_x_pass),
                    contentDescription = "닫기",
                    tint = DduruTheme.colors.gray500
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
                .background(
                    color = DduruTheme.colors.gray100,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray300,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = """
                        뚜르 서비스 이용약관 (Placeholder)

                        제1조 (목적)

                        본 약관은 '뚜르'(이하 "회사")가 제공하는 여행 동행 매칭 플랫폼 서비스(이하 "서비스")의 이용과 관련하여 회사와 이용자 간의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.

                        제2조 (정의)
                        1. "서비스"란 회사가 제공하는 여행 동행자 매칭, 채팅, 일정 관리 등 관련 제반 서비스를 의미합니다.
                        2. "이용자"란 본 약관에 따라 회사가 제공하는 서비스를 이용하는 회원 및 비회원을 말합니다.
                        3. "회원"이란 서비스에 가입하여 본 약관에 따라 회사와 이용계약을 체결한 자를 말합니다.
                        4. "매칭"이란 여행 취향, 일정, 목적지 등의 정보를 기반으로 동행자를 연결하는 서비스를 말합니다.

                        제3조 (약관의 효력 및 변경)
                        1. 본 약관은 서비스 화면에 게시하거나 기타의 방법으로 공지함으로써 효력이 발생합니다.
                        2. 회사는 필요한 경우 관련 법령을 위배하지 않는 범위에서 본 약관을 변경할 수 있으며, 변경된 약관은 시행일로부터 효력을 가집니다.

                        제4조 (서비스의 제공)
                        1. 회사는 이용자에게 동행자 탐색, 프로필 조회, 채팅, 일정 공유 등의 기능을 제공합니다.
                        2. 회사는 서비스 운영상 필요에 따라 제공 내용을 변경할 수 있습니다.

                        제5조 (이용자의 의무)
                        1. 이용자는 관계 법령, 본 약관, 서비스 이용안내 등을 준수해야 합니다.
                        2. 이용자는 타인의 정보를 도용하거나 서비스 운영을 방해하는 행위를 해서는 안 됩니다.
                    """.trimIndent(),
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray700,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
        }

        DduruButton(
            text = "닫기",
            onClick = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 47.dp
                )
        )
    }
}

@Preview()
@Composable
fun PolicyBottomSheetPreview() {
    DduruTheme {
        PolicyBottomSheetContent(
            onClose = {}
        )
    }
}

package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val LeaveNotices = listOf(
    "해당 여정에서 더 이상 멤버로 표시되지 않아요.",
    "나의 여정에서 작성한 게시글과 댓글은 그대로 남지만,\n나간 뒤에는 수정하거나 삭제할 수 없어요.",
    "해당 여정의 모든 채팅방에서 자동으로 나가요.",
    "다시 참여하려면 호스트의 승인이 필요해요."
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomLeaveBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = DduruTheme.colors.gray0,
        modifier = modifier
    ) {
        ChatRoomLeaveContent(
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
fun ChatRoomLeaveContent(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_push_alert),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "여행방에서 나가시겠어요?",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray900
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "여행방에서 나가기 전에 아래 내용을 확인해주세요.",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600
                )
                Column {
                    LeaveNotices.forEach { notice ->
                        LeaveNoticeItem(text = notice)
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            DduruButton(
                text = "취소",
                onClick = onDismiss,
                enabledColors = DduruButtonDefaults.whiteEnabledColors,
                disabledColors = DduruButtonDefaults.whiteDisabledColors,
                modifier = Modifier.weight(1f)
            )
            DduruButton(
                text = "나가기",
                onClick = onConfirm,
                enabledColors = DduruButtonEnabledColors(
                    backgroundColor = DduruTheme.colors.rejectConfirmBg,
                    textColor = DduruTheme.colors.rejectRed,
                    borderColor = DduruTheme.colors.gray0.copy(alpha = 0.3f)
                ),
                disabledColors = DduruButtonDefaults.whiteDisabledColors,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LeaveNoticeItem(text: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600,
            modifier = Modifier.width(21.dp)
        )
        Text(
            text = text,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomLeaveContentPreview() {
    DduruTheme {
        ChatRoomLeaveContent(
            onDismiss = {},
            onConfirm = {}
        )
    }
}

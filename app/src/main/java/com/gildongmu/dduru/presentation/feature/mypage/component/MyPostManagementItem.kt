package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.model.MyPostManagementUiModel

@Composable
fun MyPostManagementItem(
    post: MyPostManagementUiModel,
    showDivider: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                PostTitleAndMeta(
                    title = post.title,
                    destination = post.destination,
                    dateRange = post.dateRange,
                    capacity = post.capacity
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RecruitStatus(isRecruiting = post.isRecruiting)
                    PostCount(iconRes = R.drawable.ic_post_eye, count = post.viewCount)
                    PostCount(iconRes = R.drawable.ic_post_heart, count = post.likeCount)
                }
            }

            PostThumbnail(thumbnailUrl = post.thumbnailUrl)
        }

        if (showDivider) {
            HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray200)
        }
    }
}

@Composable
private fun RecruitStatus(isRecruiting: Boolean) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = if (isRecruiting) DduruTheme.colors.primary else DduruTheme.colors.gray500,
                    shape = CircleShape
                )
        )
        Text(
            text = if (isRecruiting) "모집중" else "모집마감",
            style = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp),
            color = if (isRecruiting) DduruTheme.colors.primary else DduruTheme.colors.gray500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPostManagementItemPreview() {
    DduruTheme {
        Column {
            MyPostManagementItem(
                post = MyPostManagementUiModel(
                    id = 1,
                    title = "야호!!! 이거보고 미국갔습니다",
                    destination = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    capacity = "3/4명",
                    isRecruiting = true,
                    viewCount = "1,242",
                    likeCount = "34"
                ),
                showDivider = true,
                onClick = {}
            )
            MyPostManagementItem(
                post = MyPostManagementUiModel(
                    id = 2,
                    title = "제주도 3박 4일 여행",
                    destination = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    capacity = "3/4명",
                    isRecruiting = false,
                    viewCount = "1,242",
                    likeCount = "34"
                ),
                showDivider = false,
                onClick = {}
            )
        }
    }
}

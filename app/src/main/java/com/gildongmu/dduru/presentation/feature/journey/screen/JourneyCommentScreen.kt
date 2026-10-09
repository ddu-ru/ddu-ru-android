package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyCommentInput
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyCommentItem
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyCommentItemUiModel

@Composable
fun JourneyCommentScreen(
    post: JourneyCommentItemUiModel,
    comments: List<JourneyCommentItemUiModel>,
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            JourneyCommentTopBar(
                onBackClick = onBackClick,
            )
        },
        bottomBar = {
            JourneyCommentInput(
                value = commentText,
                onValueChange = onCommentTextChange,
                onSendClick = onSendClick,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            item {
                JourneyCommentItem(
                    comment = post,
                    onReplyClick = {},
                    onPinClick = {},
                    onMoreClick = {},
                )

                HorizontalDivider(
                    color = Color(0xFFEEEEEE),
                )
            }

            item {
                Text(
                    text = buildAnnotatedString {
                        append("댓글 ")

                        withStyle(
                            SpanStyle(
                                color = Color(0xFF2D9CFF),
                                fontWeight = FontWeight.SemiBold,
                            ),
                        ) {
                            append(comments.size.toString())
                        }
                    },
                    fontSize = 16.sp,
                    color = Color(0xFF303030),
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 28.dp,
                    ),
                )
            }

            items(
                items = comments,
                key = { it.id },
            ) { comment ->
                JourneyCommentItem(
                    comment = comment,
                    onReplyClick = {},
                    onPinClick = {},
                    onMoreClick = {},
                )

                HorizontalDivider(
                    color = Color(0xFFEEEEEE),
                )
            }
        }
    }
}

@Composable
private fun JourneyCommentTopBar(
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(horizontal = 24.dp),
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(56.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                )
                .background(Color.White, CircleShape),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기",
                tint = Color.Black,
            )
        }

        Text(
            text = "댓글",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyCommentScreenPreview() {
    var commentText by remember { mutableStateOf("") }

    JourneyCommentScreen(
        post = JourneyCommentItemUiModel(
            id = 1L,
            username = "username",
            createdAt = "4월 25일 12:23",
            content = "Text",
        ),
        comments = listOf(
            JourneyCommentItemUiModel(
                id = 2L,
                username = "username",
                createdAt = "4월 25일 12:23",
                content = "Text",
            ),
            JourneyCommentItemUiModel(
                id = 3L,
                username = "username",
                createdAt = "4월 25일 12:23",
                content = "Text",
            ),
        ),
        commentText = commentText,
        onCommentTextChange = { commentText = it },
        onSendClick = {},
        onBackClick = {},
    )
}


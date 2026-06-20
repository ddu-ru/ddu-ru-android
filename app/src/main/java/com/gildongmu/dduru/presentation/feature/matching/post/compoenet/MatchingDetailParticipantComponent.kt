package com.gildongmu.dduru.presentation.feature.matching.post.compoenet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private const val PARTICIPANT_ITEMS_PER_ROW = 4

data class MatchingDetailParticipantUiModel(
    val userId: Long,
    val nickname: String,
    val ageText: String,
    val genderText: String,
    val profileImage: Any?,
    val isHost: Boolean = false
)

@Composable
fun MatchingDetailParticipantComponent(
    participants: List<MatchingDetailParticipantUiModel>,
    recruitCapacity: Int,
    modifier: Modifier = Modifier
) {
    val participantCount = participants.size
    val remainingCount = (recruitCapacity - participantCount).coerceAtLeast(0)
    val participantSlots = buildList {
        participants.forEach { add(MatchingDetailParticipantSlot.Participant(it)) }
        if (remainingCount > 0) {
            add(MatchingDetailParticipantSlot.Remaining(remainingCount))
        }
    }.chunked(PARTICIPANT_ITEMS_PER_ROW)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "참여 멤버",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray700,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${participantCount}/${recruitCapacity}명",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray200,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                participantSlots.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { slot ->
                            when (slot) {
                                is MatchingDetailParticipantSlot.Participant ->
                                    MatchingDetailParticipantItem(
                                        participant = slot.participant,
                                        modifier = Modifier.weight(1f)
                                    )


                                is MatchingDetailParticipantSlot.Remaining ->
                                    MatchingDetailRemainingParticipantItem(
                                        remainingCount = slot.count,
                                        modifier = Modifier.weight(1f)
                                    )
                            }
                        }

                        repeat(PARTICIPANT_ITEMS_PER_ROW - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

private sealed interface MatchingDetailParticipantSlot {
    data class Participant(val participant: MatchingDetailParticipantUiModel) :
        MatchingDetailParticipantSlot

    data class Remaining(val count: Int) : MatchingDetailParticipantSlot
}

@Composable
private fun MatchingDetailParticipantItem(
    participant: MatchingDetailParticipantUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = participant.profileImage ?: R.drawable.img_blank_profile,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.img_blank_profile)
            )

            if (participant.isHost) {
                Text(
                    text = "호스트",
                    style = DduruTheme.typography.Micro,
                    color = DduruTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 8.dp)
                        .background(
                            color = DduruTheme.colors.secondary100,
                            shape = RoundedCornerShape(99.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = participant.nickname,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.black,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "${participant.ageText} · ${participant.genderText}",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500,
            maxLines = 1
        )
    }
}

@Composable
private fun MatchingDetailRemainingParticipantItem(
    remainingCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray200,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_write_plus),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "남은 자리",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray400,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "${remainingCount}개",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray400,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailParticipantComponentPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0
        ) {
            MatchingDetailParticipantComponent(
                recruitCapacity = 9,
                participants = listOf(
                    MatchingDetailParticipantUiModel(
                        userId = 1,
                        nickname = "username",
                        ageText = "30대",
                        genderText = "남자",
                        profileImage = R.drawable.img_profile,
                        isHost = true
                    ),
                    MatchingDetailParticipantUiModel(
                        userId = 2,
                        nickname = "username",
                        ageText = "30대",
                        genderText = "남자",
                        profileImage = R.drawable.img_profile
                    ),
                    MatchingDetailParticipantUiModel(
                        userId = 3,
                        nickname = "username",
                        ageText = "30대",
                        genderText = "남자",
                        profileImage = R.drawable.img_profile
                    ),
                    MatchingDetailParticipantUiModel(
                        userId = 4,
                        nickname = "username",
                        ageText = "30대",
                        genderText = "남자",
                        profileImage = R.drawable.img_profile
                    ),
                    MatchingDetailParticipantUiModel(
                        userId = 5,
                        nickname = "username",
                        ageText = "30대",
                        genderText = "남자",
                        profileImage = R.drawable.img_profile
                    ),
                    MatchingDetailParticipantUiModel(
                        userId = 6,
                        nickname = "username",
                        ageText = "30대",
                        genderText = "남자",
                        profileImage = R.drawable.img_profile
                    )
                ),
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

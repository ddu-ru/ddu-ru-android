package com.gildongmu.dduru.presentation.feature.mypage.model

import com.gildongmu.dduru.R
import com.gildongmu.dduru.domain.entity.mypage.BlockedUser
import com.gildongmu.dduru.domain.entity.mypage.Faq
import com.gildongmu.dduru.domain.entity.mypage.Inquiry
import com.gildongmu.dduru.domain.entity.mypage.InquiryDetail
import com.gildongmu.dduru.domain.entity.mypage.InquiryState
import com.gildongmu.dduru.domain.entity.mypage.InquiryType
import com.gildongmu.dduru.domain.entity.mypage.MyPagePost
import com.gildongmu.dduru.domain.entity.mypage.MyPagePostDisplayStatus
import com.gildongmu.dduru.domain.entity.mypage.SuperHostPost
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import java.text.NumberFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val ShortDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd")
private val FullDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

fun MyPagePost.toManagementUiModel() = MyPostManagementUiModel(
    id = id,
    title = title,
    destination = destination,
    dateRange = toDateRange(startDate, endDate),
    capacity = "$recruitCount/${recruitCapacity}명",
    isRecruiting = displayStatus == MyPagePostDisplayStatus.RECRUITING,
    viewCount = viewCount.toCountText(),
    likeCount = likeCount.toCountText(),
    thumbnailUrl = photoUrl
)

fun MyPagePost.toWishlistCardUiModel() = MatchingPostCardUiModel(
    id = id,
    title = title,
    authorName = authorNickname,
    location = destination,
    dateRange = toDateRange(startDate, endDate),
    currentMemberCount = recruitCount,
    maxMemberCount = recruitCapacity,
    gender = MatchingGender.entries.firstOrNull { it.code == preferredGender } ?: MatchingGender.ANY,
    companionType = MatchingCompanionType.entries.firstOrNull { it.code == companionType }
        ?: MatchingCompanionType.FULL,
    isSuperHost = isAuthorSuperHost,
    isLiked = true,
    thumbnailImageUrl = photoUrl,
    thumbnailImageRes = R.drawable.fukuoka
)

fun SuperHostPost.toUiModel(now: LocalDateTime) = SuperhostPostUiModel(
    id = postId,
    title = title,
    destination = destination,
    dateRange = toDateRange(startDate, endDate),
    capacity = "$recruitCount/${recruitCapacity}명",
    viewCount = viewCount.toCountText(),
    thumbnailUrl = photoUrl,
    remainingTime = superHostEndsAt?.let { toRemainingTime(now, it) }
)

fun BlockedUser.toUiModel() = BlockedUserUiModel(
    id = userId,
    username = nickname,
    introduction = bio.orEmpty(),
    profileImageUrl = profileImageUrl
)

fun Faq.toUiModel() = FaqUiModel(
    id = id,
    question = question,
    answer = answer
)

fun Inquiry.toUiModel() = InquiryUiModel(
    id = id,
    category = type.toInquiryCategory()?.label.orEmpty(),
    title = content,
    date = createdAt?.format(FullDateFormatter).orEmpty(),
    status = state.toInquiryStatus()
)

fun InquiryDetail.toUiModel() = InquiryDetailUiModel(
    category = type.toInquiryCategory()?.label.orEmpty(),
    status = state.toInquiryStatus(),
    date = createdAt?.format(FullDateFormatter).orEmpty(),
    title = content,
    body = "",
    answer = answer,
    answerDate = answeredAt?.format(FullDateFormatter)
)

fun InquiryType?.toInquiryCategory(): InquiryCategory? = when (this) {
    InquiryType.SERVICE -> InquiryCategory.SERVICE
    InquiryType.ACCOUNT -> InquiryCategory.ACCOUNT
    InquiryType.MATCHING -> InquiryCategory.MATCHING
    InquiryType.SECURITY -> InquiryCategory.SECURITY
    InquiryType.BUG -> InquiryCategory.BUG
    InquiryType.OTHER -> InquiryCategory.ETC
    null -> null
}

fun InquiryCategory.toInquiryType(): InquiryType = when (this) {
    InquiryCategory.SERVICE -> InquiryType.SERVICE
    InquiryCategory.ACCOUNT -> InquiryType.ACCOUNT
    InquiryCategory.MATCHING -> InquiryType.MATCHING
    InquiryCategory.SECURITY -> InquiryType.SECURITY
    InquiryCategory.BUG -> InquiryType.BUG
    InquiryCategory.ETC -> InquiryType.OTHER
}

private fun InquiryState.toInquiryStatus(): InquiryStatus = when (this) {
    InquiryState.WAITING -> InquiryStatus.WAITING
    InquiryState.ANSWERED -> InquiryStatus.ANSWERED
}

private fun toDateRange(startDate: String, endDate: String): String =
    "${startDate.toShortDate()} - ${endDate.toShortDate()}"

private fun String.toShortDate(): String =
    runCatching { LocalDate.parse(this).format(ShortDateFormatter) }.getOrDefault(this)

private fun Int.toCountText(): String = NumberFormat.getNumberInstance(Locale.KOREA).format(this)

private fun toRemainingTime(now: LocalDateTime, endsAt: LocalDateTime): String {
    val duration = Duration.between(now, endsAt).coerceAtLeast(Duration.ZERO)
    return "${duration.toDays()}일 ${duration.toHours() % 24}시간 ${duration.toMinutes() % 60}분 남음"
}

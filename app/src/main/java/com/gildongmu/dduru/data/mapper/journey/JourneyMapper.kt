package com.gildongmu.dduru.data.mapper.journey

import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyAuthorResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyCommentListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyCommentResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPostListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPostNoticeResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPostResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyProfileImageResponse
import com.gildongmu.dduru.domain.journey.entity.JourneyAuthor
import com.gildongmu.dduru.domain.journey.entity.JourneyComment
import com.gildongmu.dduru.domain.journey.entity.JourneyCommentList
import com.gildongmu.dduru.domain.journey.entity.JourneyPost
import com.gildongmu.dduru.domain.journey.entity.JourneyPostNotice
import com.gildongmu.dduru.domain.journey.entity.JourneyPostPage
import com.gildongmu.dduru.domain.journey.entity.JourneyProfileImage

fun JourneyPostListResponse.toDomain(): JourneyPostPage = JourneyPostPage(
    journeyId = journeyId,
    posts = posts.map { it.toDomain() },
    nextCursor = nextCursor,
    hasNext = hasNext,
    size = size
)

fun JourneyPostResponse.toDomain(): JourneyPost = JourneyPost(
    journeyPostId = journeyPostId,
    author = author.toDomain(),
    content = content,
    imageUrls = imageUrls,
    isNotice = isNotice,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isAuthor = isAuthor,
    commentCount = commentCount
)

fun JourneyPostNoticeResponse.toDomain(): JourneyPostNotice = JourneyPostNotice(
    journeyPostId = journeyPostId,
    isNotice = isNotice
)

fun JourneyCommentListResponse.toDomain(): JourneyCommentList = JourneyCommentList(
    journeyPostId = journeyPostId,
    commentCount = commentCount,
    comments = comments.map { it.toDomain() }
)

fun JourneyCommentResponse.toDomain(): JourneyComment = JourneyComment(
    commentId = commentId,
    journeyPostId = journeyPostId,
    author = author.toDomain(),
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isAuthor = isAuthor
)

fun JourneyAuthorResponse.toDomain(): JourneyAuthor = JourneyAuthor(
    userId = userId,
    nickname = nickname,
    profileImage = profileImage.toDomain(),
    isHost = isHost,
    gender = gender,
    ageGroup = ageGroup
)

fun JourneyProfileImageResponse.toDomain(): JourneyProfileImage = JourneyProfileImage(
    type = type,
    url = url,
    bgColorId = bgColorId
)

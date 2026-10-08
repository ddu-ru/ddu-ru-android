package com.gildongmu.dduru.presentation.feature.journey.state

import kotlinx.collections.immutable.persistentListOf

private const val blankProfileImage =
    "android.resource://com.gildongmu.dduru/drawable/img_blank_profile"

internal val journeyAdminPreviewMember = JourneyAdminMember(
    id = 1L,
    username = "뚜르",
    profileImageUrl = blankProfileImage,
    roles = persistentListOf("호스트", "총무")
)

internal val journeyAdminPreviewMembers = persistentListOf(
    journeyAdminPreviewMember,
    JourneyAdminMember(
        id = 2L,
        username = "길동",
        profileImageUrl = blankProfileImage,
        roles = persistentListOf("사진 담당")
    ),
    JourneyAdminMember(
        id = 3L,
        username = "영희",
        profileImageUrl = blankProfileImage,
        roles = persistentListOf("일정 담당")
    )
)

package com.gildongmu.dduru.presentation.feature.journey.state

private const val blankProfileImage =
    "android.resource://com.gildongmu.dduru/drawable/img_blank_profile"

internal val journeyAdminPreviewMember = JourneyAdminMember(
    id = 1L,
    username = "뚜르",
    profileImageUrl = blankProfileImage,
    roles = listOf("호스트", "총무")
)

internal val journeyAdminPreviewMembers = listOf(
    journeyAdminPreviewMember,
    JourneyAdminMember(
        id = 2L,
        username = "길동",
        profileImageUrl = blankProfileImage,
        roles = listOf("사진 담당")
    ),
    JourneyAdminMember(
        id = 3L,
        username = "영희",
        profileImageUrl = blankProfileImage,
        roles = listOf("일정 담당")
    )
)

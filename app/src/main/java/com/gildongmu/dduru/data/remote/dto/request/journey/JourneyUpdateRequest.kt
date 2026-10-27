package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class JourneyUpdateRequest(
    @EncodeDefault(EncodeDefault.Mode.NEVER) val title: String? = null,
    @EncodeDefault(EncodeDefault.Mode.NEVER) val photoUrl: String? = null,
    @EncodeDefault(EncodeDefault.Mode.NEVER) val startDate: String? = null,
    @EncodeDefault(EncodeDefault.Mode.NEVER) val endDate: String? = null
)

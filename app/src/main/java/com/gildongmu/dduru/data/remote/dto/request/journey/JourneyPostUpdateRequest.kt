package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class JourneyPostUpdateRequest(
    @EncodeDefault(EncodeDefault.Mode.NEVER) val content: String? = null,
    @EncodeDefault(EncodeDefault.Mode.NEVER) val imageUrls: List<String>? = null
)

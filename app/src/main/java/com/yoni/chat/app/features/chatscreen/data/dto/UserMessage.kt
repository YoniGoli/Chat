package com.yoni.chat.app.features.chatscreen.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserMessage(
    val sessionId: String? = null,
    val message: String
)
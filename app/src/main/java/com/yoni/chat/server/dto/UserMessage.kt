package com.yoni.chat.server.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserMessage(
    val sessionId: String?,
    val message: String,
)

sealed class MessageInputType {
    object Number: MessageInputType()
    object Text: MessageInputType()
    data class Selection(val options: List<String>): MessageInputType()
}
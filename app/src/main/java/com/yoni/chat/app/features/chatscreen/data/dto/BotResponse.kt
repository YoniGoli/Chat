package com.yoni.chat.app.features.chatscreen.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BotResponse(
    val sessionId: String,
    val messages: List<BotMessage>,
    val waitingFor: WaitingFor,
    val waitingOptions: List<String>?
)

@JsonClass(generateAdapter = true)
internal data class BotMessage(
    val id: String,
    val timestamp: Long,
    val message: String,
)

internal enum class WaitingFor {
    NUMBER, TEXT, SELECTION
}
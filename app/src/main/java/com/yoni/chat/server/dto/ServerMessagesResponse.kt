package com.yoni.chat.server.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ServerMessagesResponse(
    val sessionId: String,
    val messages: List<ServerMessage>,
    val waitingFor: WaitingFor,
    val waitingOptions: List<String>?
)
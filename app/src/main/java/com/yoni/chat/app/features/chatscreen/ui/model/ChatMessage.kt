package com.yoni.chat.app.features.chatscreen.ui.model

internal data class ChatMessage(
    val id: String,
    val sender: String?,
    val message: String,
    val timestamp: Long,
) {
    val isIncoming: Boolean
        get() = !sender.isNullOrEmpty()

}


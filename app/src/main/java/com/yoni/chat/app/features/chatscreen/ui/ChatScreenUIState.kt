package com.yoni.chat.app.features.chatscreen.ui

import com.yoni.chat.app.features.chatscreen.ui.model.ChatMessage
import com.yoni.chat.app.features.chatscreen.ui.model.InputType

internal data class ChatScreenUIState(
    val messages: List<ChatMessage> = listOf(),
    val waitingFor: InputType = InputType.Text
)
package com.yoni.chat.server

import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.WaitingFor
import com.yoni.chat.server.model.ChatStep
import com.yoni.chat.server.reply.IReplyToUserMessage

internal data class SessionState(
    val messages: List<ServerMessage>,
    val waitingFor: WaitingFor,
    val waitingOptions: List<String>? = null,
    val nextReply: IReplyToUserMessage,
)
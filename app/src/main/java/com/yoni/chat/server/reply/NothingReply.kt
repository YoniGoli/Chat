package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.dto.WaitingFor

internal object NothingReply : IReplyToUserMessage {
    override fun getResponse(message: UserMessage): SessionState {
        return SessionState(
            messages = listOf(),
            waitingFor = WaitingFor.TEXT,
            nextReply = IReplyToUserMessage.Nothing
        )
    }
}
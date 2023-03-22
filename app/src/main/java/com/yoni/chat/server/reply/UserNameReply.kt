package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.dto.WaitingFor

internal object UserNameReply : IReplyToUserMessage {
    override fun getResponse(message: UserMessage): SessionState {
        return SessionState(
            messages = listOf(
                ServerMessage(
                    message = "Nice to meet you ${message.message} :)",
                ),
                ServerMessage(
                    message = "What is your phone number?",
                )
            ),
            waitingFor = WaitingFor.NUMBER,
            nextReply = IReplyToUserMessage.PhoneNumber
        )
    }
}
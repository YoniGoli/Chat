package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.WaitingFor
import com.yoni.chat.server.model.ChatStep

internal object WelcomeReply : IReplyToUserMessage {
    override fun getResponse(message: UserMessage): SessionState {
        return SessionState(
            messages = listOf(
                ServerMessage(
                    message = "Hello I am Yoni!",
                ),
                ServerMessage(
                    message = "What is your name?",
                )
            ),
            waitingFor = WaitingFor.TEXT,
            nextReply = IReplyToUserMessage.UserName
        )
    }
}
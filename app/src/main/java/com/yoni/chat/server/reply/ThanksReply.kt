package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.dto.WaitingFor

internal object ThanksReply : IReplyToUserMessage {
    override fun getResponse(message: UserMessage): SessionState {
        return if(message.message.uppercase() == "RESTART") {
            return SessionState(
                messages = listOf(
                    ServerMessage(
                        message = "Hello again, I am Yoni!",
                    ),
                    ServerMessage(
                        message = "What is your name?",
                    )
                ),
                waitingFor = WaitingFor.TEXT,
                nextReply = IReplyToUserMessage.UserName
            )
        }
        else {
            SessionState(
                messages = listOf(
                    ServerMessage(
                        message = "Bye Bye",
                    )
                ),
                waitingFor = WaitingFor.TEXT,
                nextReply = IReplyToUserMessage.Nothing
            )
        }
    }
}
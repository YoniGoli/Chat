package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.dto.WaitingFor
import com.yoni.chat.server.model.ChatStep

internal object TermsOfServiceReply : IReplyToUserMessage {
    override fun getResponse(message: UserMessage): SessionState {
        return SessionState(
            messages = listOf(
                ServerMessage(
                    message = "Thanks!",
                ),
                ServerMessage(
                    message = "What do you want to do now?",
                )
            ),
            waitingFor = WaitingFor.SELECTION,
            waitingOptions = listOf("Restart", "Exit"),
            nextReply = IReplyToUserMessage.ThankYou
        )
    }
}
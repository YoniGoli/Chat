package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.dto.WaitingFor

internal object PhoneNumberReply : IReplyToUserMessage {
    override fun getResponse(message: UserMessage): SessionState {
        // Just a simple example for content validation...
        val isValidResponse = try {
            message.message.toLong()
            true
        } catch (e: Exception ){
            false
        }

        return if(isValidResponse) {
            getValidResponse()
        } else {
            getInvalidResponse()
        }
    }

    private fun getValidResponse() = SessionState(
        messages = listOf(
            ServerMessage(
                message = "Do you agree to our terms of service?",
            ),
        ),
        waitingFor = WaitingFor.SELECTION,
        waitingOptions = listOf("Yes", "No"),
        nextReply = IReplyToUserMessage.TermsOfService
    )

    private fun getInvalidResponse() = SessionState(
        messages = listOf(
            ServerMessage(
                message = "Invalid phone number please try again",
            )
        ),
        waitingFor = WaitingFor.NUMBER,
        nextReply = IReplyToUserMessage.PhoneNumber
    )
}
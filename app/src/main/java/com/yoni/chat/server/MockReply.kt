package com.yoni.chat.server

import com.yoni.chat.server.dto.ServerMessagesResponse
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.reply.IReplyToUserMessage
import java.util.*

private typealias SESSION_ID = String

internal object MockReply {

    private const val NICE_TO_MEET_YOU = "Nice to meet you %s :)"
    private const val WHATS_YOUR_PHONE_NUMBER = "What is your phone number?"
    private const val DO_YOU_AGREE = "Do you agree to our terms of service?"
    private const val THANKS = "Thanks"
    private const val LAST_STEP = "This is the last step!"
    private const val WHAT_DO_YOU_WANT_TO_DO = "What do you want to do now?"
    private const val BYE_BYE = "Bye Bye"

    private val sessions: MutableMap<SESSION_ID, IReplyToUserMessage> = mutableMapOf()

    fun reply(message: UserMessage): ServerMessagesResponse {
        val sessionId: String = message.sessionId ?: UUID.randomUUID().toString()

        val botReply: IReplyToUserMessage = sessions.getOrElse(sessionId) {
            IReplyToUserMessage.Welcome
        }

        val botResponse: SessionState = botReply.getResponse(message)

        sessions[sessionId] = botResponse.nextReply

        return ServerMessagesResponse(
            sessionId = sessionId,
            messages = botResponse.messages,
            waitingFor = botResponse.waitingFor,
            waitingOptions = botResponse.waitingOptions
        )
    }

}
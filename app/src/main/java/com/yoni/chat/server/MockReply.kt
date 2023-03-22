package com.yoni.chat.server

import com.yoni.chat.server.dto.ServerMessagesResponse
import com.yoni.chat.server.dto.UserMessage
import com.yoni.chat.server.reply.IReplyToUserMessage
import java.util.*

private typealias SESSION_ID = String

internal object MockReply {

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
package com.yoni.chat.server.reply

import com.yoni.chat.server.SessionState
import com.yoni.chat.server.dto.UserMessage

internal sealed interface IReplyToUserMessage {
    fun getResponse(message: UserMessage): SessionState


    companion object {
        val Welcome = WelcomeReply
        val UserName = UserNameReply
        val PhoneNumber = PhoneNumberReply
        val TermsOfService = TermsOfServiceReply
        val ThankYou = ThanksReply
        val Nothing = NothingReply
    }
}
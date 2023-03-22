package com.yoni.chat.server.model

import com.yoni.chat.server.reply.IReplyToUserMessage

internal sealed class ChatStep(val nextStep: ChatStep, val reply: IReplyToUserMessage) {
    object Welcome: ChatStep(PhoneNumber, IReplyToUserMessage.Welcome)
    object PhoneNumber: ChatStep(TermsOfService, IReplyToUserMessage.PhoneNumber)
    object TermsOfService: ChatStep(LastStep, IReplyToUserMessage.Welcome)
    object LastStep: ChatStep(Nothing, IReplyToUserMessage.Welcome)
    object Nothing: ChatStep(Nothing, IReplyToUserMessage.Welcome)
}
package com.yoni.chat.app.features.chatscreen.data.remote

import com.yoni.chat.app.features.chatscreen.data.IChatDataSource
import com.yoni.chat.app.features.chatscreen.data.dto.BotResponse
import com.yoni.chat.app.features.chatscreen.data.dto.UserMessage

internal class ChatBotRemoteDataSource(
    private val chatBotService: ChatBotService
): IChatDataSource {

    override suspend fun sendMessage(message: UserMessage): BotResponse {
        return chatBotService.sendMessage(message)
    }

}
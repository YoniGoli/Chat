package com.yoni.chat.app.features.chatscreen.data

import com.yoni.chat.app.features.chatscreen.data.dto.BotResponse
import com.yoni.chat.app.features.chatscreen.data.dto.UserMessage

internal interface IChatDataSource {
    suspend fun sendMessage(message: UserMessage): BotResponse
}
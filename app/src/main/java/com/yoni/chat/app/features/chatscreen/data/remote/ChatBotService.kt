package com.yoni.chat.app.features.chatscreen.data.remote

import com.yoni.chat.app.features.chatscreen.data.dto.BotResponse
import com.yoni.chat.app.features.chatscreen.data.dto.UserMessage
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ChatBotService {

    @POST("chatbot")
    suspend fun sendMessage(@Body message: UserMessage): BotResponse

}
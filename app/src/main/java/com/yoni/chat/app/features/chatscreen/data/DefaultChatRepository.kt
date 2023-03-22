package com.yoni.chat.app.features.chatscreen.data

import com.yoni.chat.app.common.runCatchingCancellable
import com.yoni.chat.app.features.chatscreen.data.dto.BotResponse
import com.yoni.chat.app.features.chatscreen.data.dto.UserMessage

internal class DefaultChatRepository(
    private val remote: IChatDataSource
): IChatRepository {

    override suspend fun sendMessage(message: UserMessage): Result<BotResponse> {
        return runCatchingCancellable {
            remote.sendMessage(message)
        }
    }

}
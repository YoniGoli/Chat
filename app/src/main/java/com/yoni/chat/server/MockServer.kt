package com.yoni.chat.server

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import com.yoni.chat.app.features.chatscreen.data.dto.UserMessageJsonAdapter
import com.yoni.chat.server.dto.ServerMessage
import com.yoni.chat.server.dto.ServerMessageJsonAdapter
import com.yoni.chat.server.dto.ServerMessagesResponse
import com.yoni.chat.server.dto.UserMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object MockServer {
    const val PORT = 5433
    private const val MOCK_DELAY = 2000L

    private val server = MockWebServer()
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val userMessageAdapter: JsonAdapter<UserMessage> = moshi.adapter(UserMessage::class.java)
    private val serverResponseAdapter: JsonAdapter<ServerMessagesResponse> = moshi.adapter(ServerMessagesResponse::class.java)

    private var serverIsOnline: Boolean = false
    private val notFoundResponse = MockResponse().setResponseCode(404)

    val isOnline: Boolean
        get() = serverIsOnline

    fun init() = GlobalScope.launch {
        server.dispatcher = object: Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {

                Thread.sleep(MOCK_DELAY)

                val mockResponse = when(request.method) {
                    "POST" -> replyToPostRequest(request)
                    else -> null
                }

                return mockResponse ?: notFoundResponse
            }

        }

        server.start(port = PORT)
        serverIsOnline = true
    }

    private fun replyToPostRequest(request: RecordedRequest): MockResponse?{
        val message: UserMessage? = userMessageAdapter.fromJson(request.body.readUtf8())

        return message?.let { userMessage ->
            val serverReply = MockReply.reply(userMessage)
            val serverMessagesResponse = serverResponseAdapter.toJson(serverReply)

            MockResponse().setResponseCode(200)
                .setBody(serverMessagesResponse)
        }
    }


}
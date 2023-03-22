package com.yoni.chat.app.features.chatscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoni.chat.app.features.chatscreen.data.IChatRepository
import com.yoni.chat.app.features.chatscreen.data.dto.BotMessage
import com.yoni.chat.app.features.chatscreen.data.dto.BotResponse
import com.yoni.chat.app.features.chatscreen.data.dto.UserMessage
import com.yoni.chat.app.features.chatscreen.data.dto.WaitingFor
import com.yoni.chat.app.features.chatscreen.ui.ChatScreenUIEvent
import com.yoni.chat.app.features.chatscreen.ui.ChatScreenUIState
import com.yoni.chat.app.features.chatscreen.ui.model.ChatMessage
import com.yoni.chat.app.features.chatscreen.ui.model.InputType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class ChatScreenViewModel @Inject constructor(
    private val repository: IChatRepository
): ViewModel(){

    private val _uiState: MutableStateFlow<ChatScreenUIState> = MutableStateFlow(ChatScreenUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<ChatScreenUIEvent> = MutableSharedFlow(replay = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    private var userSessionId: String? = null

    fun start() {
        viewModelScope.launch {
            sendMessage("")
        }
    }

    fun sendUserMessage(message: String) {
        updateUiState {
            it.copy(
                messages = buildList {
                    addAll(it.messages)
                    add(
                        ChatMessage(
                            id = UUID.randomUUID().toString(),
                            sender = null,
                            message = message,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            )
        }

        viewModelScope.launch {
            // Mock delay time to improve the user experience and make it feels like We're talking to a human
            delay(750)

            sendMessage(message)
        }
    }

    private suspend fun sendMessage(message: String) {
        emitUiEvent(ChatScreenUIEvent.Typing)

        val messageResponse = repository.sendMessage(UserMessage(
            sessionId = userSessionId,
            message = message
        ))

        messageResponse
            .onSuccess {
                emitUiEvent(ChatScreenUIEvent.Idle)
                onMessageReceived(it)
            }
            .onFailure {
                emitUiEvent(ChatScreenUIEvent.Idle)
                println(it)
            }
    }

    private fun onMessageReceived(botResponse: BotResponse) {
        userSessionId = botResponse.sessionId

        val chatMessages = botResponse.messages.map {
            it.toChatMessage()
        }

        updateUiState {
            it.copy(
                waitingFor = botResponse.waitingFor.toInputType(botResponse),
                messages = buildList {
                    addAll(it.messages)
                    addAll(chatMessages)
                }
            )
        }
    }

    private fun updateUiState(block: (ChatScreenUIState) -> ChatScreenUIState) {
        _uiState.update(block)
    }

    private fun emitUiEvent(event: ChatScreenUIEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun WaitingFor.toInputType(botResponse: BotResponse): InputType {
        return when(this) {
            WaitingFor.NUMBER -> InputType.Number
            WaitingFor.TEXT -> InputType.Text
            WaitingFor.SELECTION -> {
                botResponse.waitingOptions?.let {
                    InputType.Selection(
                        leftText = requireNotNull(it.getOrNull(0)),
                        rightText = requireNotNull(it.getOrNull(1)),
                    )
                } ?: InputType.Text
            }
        }
    }

    private fun BotMessage.toChatMessage(): ChatMessage {
        return ChatMessage(
            id = id,
            sender = "server",
            message = message,
            timestamp = timestamp
        )
    }



}
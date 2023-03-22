package com.yoni.chat.app.features.chatscreen.ui

sealed class ChatScreenUIEvent {

    object Typing: ChatScreenUIEvent()
    object Idle: ChatScreenUIEvent()

}
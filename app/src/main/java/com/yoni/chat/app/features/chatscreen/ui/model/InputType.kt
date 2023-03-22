package com.yoni.chat.app.features.chatscreen.ui.model

internal sealed class InputType(val inputTypeId: Int) {
    object Number: InputType(android.text.InputType.TYPE_CLASS_NUMBER)
    object Text: InputType(android.text.InputType.TYPE_CLASS_TEXT)
    data class Selection(val leftText: String, val rightText: String): InputType(0)
}
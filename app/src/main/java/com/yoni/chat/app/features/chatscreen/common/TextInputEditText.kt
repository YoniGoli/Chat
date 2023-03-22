package com.yoni.chat.app.features.chatscreen.common

import com.google.android.material.textfield.TextInputEditText

internal fun TextInputEditText.waitForUIAction(uiActionId: Int, block: () -> Unit) {
    setOnEditorActionListener { v, actionId, event ->
        if (actionId == uiActionId) {
            block()
        }

        false
    }
}
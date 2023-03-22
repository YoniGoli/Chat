package com.yoni.chat.app.features.chatscreen.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.run {
        hideSoftInputFromWindow(windowToken, 0)
    }
}
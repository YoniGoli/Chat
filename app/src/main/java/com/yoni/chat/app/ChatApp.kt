package com.yoni.chat.app

import android.app.Application
import com.yoni.chat.server.MockServer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp: Application(){

    override fun onCreate() {
        super.onCreate()
        MockServer.init()
    }

}
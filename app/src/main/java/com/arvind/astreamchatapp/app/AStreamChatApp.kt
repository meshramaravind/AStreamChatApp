package com.arvind.astreamchatapp.app

import android.app.Application
import com.arvind.astreamchatapp.R
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain

@HiltAndroidApp
class AStreamChatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client =
            ChatClient.Builder(getString(R.string.api_key), this).logLevel(ChatLogLevel.ALL).build()
        ChatDomain.Builder(client, this).build()
    }

}
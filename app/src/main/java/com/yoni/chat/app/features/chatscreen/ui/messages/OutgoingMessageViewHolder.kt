package com.yoni.chat.app.features.chatscreen.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoni.chat.R
import com.yoni.chat.app.features.chatscreen.ui.model.ChatMessage
import com.yoni.chat.databinding.MessageItemOutgoingBinding

internal class OutgoingMessageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.message_item_outgoing, parent, false)
) {
    private val binding: MessageItemOutgoingBinding = MessageItemOutgoingBinding.bind(itemView)

    fun onBind(message: ChatMessage) {
        binding.message.text = message.message
    }
}
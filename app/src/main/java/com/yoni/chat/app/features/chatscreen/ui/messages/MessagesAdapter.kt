package com.yoni.chat.app.features.chatscreen.ui.messages

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoni.chat.app.features.chatscreen.ui.model.ChatMessage

internal class MessagesAdapter: ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).isIncoming) {
            true -> INCOMING_MESSAGE
            false -> OUTGOING_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            INCOMING_MESSAGE -> IncomingMessageViewHolder(parent)
            OUTGOING_MESSAGE -> OutgoingMessageViewHolder(parent)
            else -> {
                throw IllegalStateException()
            }
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is IncomingMessageViewHolder -> {
                holder.onBind(getItem(position))
            }
            is OutgoingMessageViewHolder -> {
                holder.onBind(getItem(position))
            }
        }
    }

    companion object {
        private const val INCOMING_MESSAGE = 1
        private const val OUTGOING_MESSAGE = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatMessage>() {

            override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
                return oldItem == newItem
            }

        }
    }
}



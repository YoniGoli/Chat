package com.yoni.chat.app.features.chatscreen

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoni.chat.R
import com.yoni.chat.app.common.fadeIn
import com.yoni.chat.app.common.fadeOut
import com.yoni.chat.app.common.viewBinding
import com.yoni.chat.app.features.chatscreen.common.hideKeyboard
import com.yoni.chat.app.features.chatscreen.common.waitForUIAction
import com.yoni.chat.app.features.chatscreen.ui.ChatScreenUIEvent
import com.yoni.chat.app.features.chatscreen.ui.ChatScreenUIState
import com.yoni.chat.app.features.chatscreen.ui.messages.MessagesAdapter
import com.yoni.chat.app.features.chatscreen.ui.model.InputType
import com.yoni.chat.databinding.FragmentChatScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
internal class ChatScreenFragment: Fragment(R.layout.fragment_chat_screen) {

    private val binding by viewBinding(FragmentChatScreenBinding::bind)
    private val viewModel by viewModels<ChatScreenViewModel>()

    private val messagesAdapter = MessagesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(view.context).apply {
            stackFromEnd = true
        }

        binding.messages.run {
            layoutManager = linearLayoutManager
            adapter = messagesAdapter
        }

        binding.editTextInput.waitForUIAction(
            uiActionId = EditorInfo.IME_ACTION_SEND,
            block = this::sendMessage
        )

        binding.sendButton.setOnClickListener {
            sendMessage()
        }

        collectFlows()
        viewModel.start()
    }

    private fun sendMessage() {
        binding.editTextInput.clearFocus()

        binding.editTextInput.text?.run {
            toString()
                .takeUnless { it.isEmpty() }
                ?.also { userMessage ->
                    clear()
                    viewModel.sendUserMessage(userMessage)
                }

        }
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent
                .flowWithLifecycle(lifecycle)
                .onEach(this@ChatScreenFragment::onUiEvent)
                .collect()
        }

        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach(this@ChatScreenFragment::render)
                .collect()
        }
    }

    private fun render(state: ChatScreenUIState) {
        messagesAdapter.submitList(state.messages)
        binding.messages.smoothScrollToPosition(state.messages.size)

        if(binding.editTextInput.inputType != state.waitingFor.inputTypeId) {
            binding.editTextInput.hideKeyboard()
            binding.editTextInput.inputType = state.waitingFor.inputTypeId
            binding.editTextInput.clearFocus()
        }

        if(state.waitingFor is InputType.Selection) {
            bindSelectionBar(state.waitingFor)
        }

        binding.editTextInput.requestFocus()
    }

    private fun bindSelectionBar(selection: InputType.Selection) {
        binding.selectionBar.selectionBarContainer.visibility = View.VISIBLE

        binding.selectionBar.sectionItemLeft.text = selection.leftText
        binding.selectionBar.sectionItemRight.text = selection.rightText

        binding.selectionBar.sectionItemLeft.setOnClickListener {
            viewModel.sendUserMessage(selection.leftText)
            binding.selectionBar.selectionBarContainer.visibility = View.GONE
        }

        binding.selectionBar.sectionItemRight.setOnClickListener {
            viewModel.sendUserMessage(selection.rightText)
            binding.selectionBar.selectionBarContainer.visibility = View.GONE
        }
    }

    private fun onUiEvent(event: ChatScreenUIEvent) {
        when(event) {
            ChatScreenUIEvent.Typing -> {
                binding.typingBar.container.fadeIn()
            }
            else -> {
                if(binding.typingBar.container.visibility == View.VISIBLE) {
                    binding.typingBar.container.fadeOut(duration = 350L)
                }
            }
        }
    }

}
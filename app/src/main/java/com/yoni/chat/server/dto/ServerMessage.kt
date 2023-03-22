package com.yoni.chat.server.dto

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
internal data class ServerMessage(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val message: String,
    val options: List<String>? = null
)

internal enum class WaitingFor {
    NUMBER, TEXT, SELECTION
}
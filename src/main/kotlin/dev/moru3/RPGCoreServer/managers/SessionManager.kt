package dev.moru3.RPGCoreServer.managers

import dev.moru3.RPGCoreServer.managers.PermissionType.*
import org.springframework.web.socket.WebSocketSession

class SessionManager {
    companion object {

        val blockAddress = mutableListOf<String>()

        val strikeAddress = mutableMapOf<String, Int>()

        private val tokens = mutableMapOf("test" to Token("test").also { it.allowAccessPlayerDataByRestAPI = true; it.allowAccessPlayerDataByWebSocket = true })

        val sessions = mutableListOf<WebSocketSession>()

        fun addToken(token: Token) { tokens[token.token] = token }

        fun isEnableToken(token: String, permissionType: PermissionType): Boolean {
            when(permissionType) {
                CONNECT_WEBSOCKET -> tokens[token]?.also { return it.allowAccessPlayerDataByWebSocket }?:return false
                UNLIMITED_REST_REQUEST -> tokens[token]?.also { return it.allowAccessPlayerDataByRestAPI }?:return false
            }
            return false
        }
    }
}
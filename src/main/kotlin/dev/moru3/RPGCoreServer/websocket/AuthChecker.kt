package dev.moru3.RPGCoreServer.websocket

import dev.moru3.RPGCoreServer.websocket.model.TokenInfo
import java.net.InetSocketAddress
import java.util.regex.Pattern

class AuthChecker {
    companion object {
        private val authenticationHeaderRegex = Pattern.compile("^Bearer [\\d\\w]{32,}$")
        private val tokens = mutableMapOf<String, TokenInfo>()

        fun String.checkToken(inetSocketAddress: InetSocketAddress): Boolean {
            val info = tokens[this]?:return false
            return info.inetSocketAddress==null||info.inetSocketAddress==inetSocketAddress
        }
        fun String.checkAuthRegex(): Boolean = authenticationHeaderRegex.matcher(this).matches()
    }
}
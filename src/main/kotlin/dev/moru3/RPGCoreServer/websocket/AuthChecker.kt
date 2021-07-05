package dev.moru3.RPGCoreServer.websocket

import java.util.regex.Pattern

class AuthChecker {
    companion object {
        private val authenticationHeaderRegex = Pattern.compile("^Bearer [\\d\\w]{32,}$")

        fun String.checkAuthRegex(): Boolean = authenticationHeaderRegex.matcher(this).matches()
    }
}
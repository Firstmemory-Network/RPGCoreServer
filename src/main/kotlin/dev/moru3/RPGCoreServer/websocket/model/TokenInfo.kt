package dev.moru3.RPGCoreServer.websocket.model

import java.net.InetSocketAddress
import java.security.SecureRandom

class TokenInfo(val inetSocketAddress: InetSocketAddress? = null) {
    var token: String = ""
        private set

    init {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val random = SecureRandom.getInstance("SHA1PRNG")
        repeat(128) { token += characters[random.nextInt(characters.length)] }
    }
}
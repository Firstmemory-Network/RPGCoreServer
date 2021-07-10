package dev.moru3.RPGCoreServer.websocket.data

enum class NotifyType(val id: Byte) {
    PLAYER_JOIN(0),
    PLAYER_QUIT(1);

    companion object {
        val types = mutableMapOf<Byte, NotifyType>()
        init { values().forEach { types[it.id] = it } }

        fun getById(id: Byte): NotifyType { return types[id]?:throw IllegalArgumentException() }
    }
}
package dev.moru3.RPGCoreServer.websocket.data

enum class ResponseType(val id: Byte) {
    UPDATE_PLAYER_DATA(0),
    UPDATE_SECURITY(1),
    MESSAGE(2),
    ERROR(3),
}
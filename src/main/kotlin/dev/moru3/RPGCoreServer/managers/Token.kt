package dev.moru3.RPGCoreServer.managers

class Token(val token: String) {
    var allowAccessPlayerDataByWebSocket = false
    var allowAccessPlayerDataByRestAPI = false
}

enum class PermissionType {
    CONNECT_WEBSOCKET,
    UNLIMITED_REST_REQUEST
}
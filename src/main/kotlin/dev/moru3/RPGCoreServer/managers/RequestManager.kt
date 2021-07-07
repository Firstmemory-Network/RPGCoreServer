package dev.moru3.RPGCoreServer.managers

class RequestManager {
    companion object {
        val accessCountPerHours = mutableMapOf<String, Int>()
    }
}
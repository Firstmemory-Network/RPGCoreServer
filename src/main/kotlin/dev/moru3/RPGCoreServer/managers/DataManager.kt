package dev.moru3.RPGCoreServer.managers

import dev.moru3.RPGCoreServer.websocket.data.IPlayerData
import dev.moru3.RPGCoreServer.websocket.data.PlayerData
import java.util.*

class DataManager {
    companion object {
        val dataList = mutableMapOf<UUID, IPlayerData>()
        fun getPlayerData(uniqueId: String): IPlayerData {
            val uuid = UUID.fromString(uniqueId)
            dataList[uuid]?.also { return it }
            PlayerData(uuid).also { dataList[uuid] = it }.also { return it }
        }
    }
}
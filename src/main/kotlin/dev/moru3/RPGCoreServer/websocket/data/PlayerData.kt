package dev.moru3.RPGCoreServer.websocket.data

import me.moru3.sqlow.Update
import me.moru3.sqlow.Where
import java.util.*
import kotlin.concurrent.thread

class PlayerData(val uuid: UUID): IPlayerData {
    override var experience: Int = 0
        set(value) {
            if(value==field) { return }
            thread { Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("exp", value).send() }
            field = value
        }

    override var balance: Int = 0
        set(value) {
            if(value==field) { return }
            thread { Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("money", value).send() }
            field = value
        }


}
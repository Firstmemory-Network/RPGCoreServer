package dev.moru3.RPGCoreServer.websocket.data

import me.moru3.sqlow.*
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.pow

class PlayerData(val uuid: UUID): IPlayerData {
    override var balance: Int = 0
        set(value) {
            if(value==field) { return }
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("money", value).send()
            field = value
        }

    override var experience: Int = 0
        set(value) {
            if(value==field) { return }
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("exp", value).send()
            field = value
        }
    override var level: Int = 0
        set(value) {
            if(value<=field) { return }
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("level", value).send()
            field = value
        }
    override var maxStamina: Int = 0
        set(value) {
            if(value==field) { return }
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("max_stamina", value).send()
            field = value
        }

    override var maxHealth: Int = 0
        set(value) {
            if(value<=field) { return }
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("max_health", value).send()
            field = value
        }

    override var statusPoint: Int = 0
        set(value) {
            if(value<=field) { return }
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid)).addValue("status_point", value).send()
            field = value
        }

    override val skillSet: ISkillSet = SkillSet(this)
    override val customData: MutableMap<String, String> = mutableMapOf()

    /**
     * real time var
     */
    override var health: Double = 1.0
        set(value) {
            //TODO
            field = value
        }
    override var stamina: Int = 1
        set(value) {
            //TODO
            field = value
        }

    var oldLevel: Int = 0
        set(value) {
            Update("userdata", Where().addKey("uuid").equals().addValue(uuid))
                .addValue("last_level", level).send()
            field = value
        }

    fun checkLevelUp(): Boolean {
        return if((10.0 * level).pow(2) <= experience) {
            level+=1
            experience = ((experience-(10.0 * (level-1))).pow(2)).toInt()
            checkLevelUp()
            true
        } else {
            false
        }
    }

    private fun reSetup() {
        Insert("userdata")
            .addValue(DataType.VARCHAR ,"uuid", uuid)
            .send(false)
        Insert("status")
            .addValue(DataType.VARCHAR ,"uuid", uuid)
            .send(false)
    }

    private fun syncOldLevel() {
        if(oldLevel==level) { return }
        thread {
            repeat((1..level-oldLevel).count()) {
                level+=1
                Thread.sleep(500)
            }
        }
    }

    override fun reload() {
        reSetup()
        val result = Select("userdata", Where().addKey("uuid").equals().addValue(uuid)).send()
        if(!result.next()) throw NullPointerException()
        balance = result.getInt("money")
        experience = result.getInt("exp")
        level = result.getInt("level")
        maxHealth = result.getInt("max_health")
        maxStamina = result.getInt("max_stamina")
        statusPoint = result.getInt("status_point")
        oldLevel = result.getInt("old_level")
    }

    init {
        reload()
        health = maxHealth.toDouble()
        stamina = maxStamina
        syncOldLevel()
    }

    companion object {

        private val data = mutableMapOf<UUID, IPlayerData>()

        fun createHeroData(uuid: UUID): IPlayerData {
            data[uuid]?.also { return it }
            val result = PlayerData(uuid)
            data[uuid] = result
            return result
        }

        fun getHeroData(uuid: UUID): IPlayerData {
            return data[uuid]?:createHeroData(uuid)
        }
    }
}
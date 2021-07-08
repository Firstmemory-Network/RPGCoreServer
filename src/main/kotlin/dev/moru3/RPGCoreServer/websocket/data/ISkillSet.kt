package dev.moru3.RPGCoreServer.websocket.data

interface ISkillSet {
    var stamina: Int
    var defence: Int
    var strength: Int
    var intelligence: Int
    var vomiting: Int

    fun reload()
}

enum class SkillType(val id: Byte) {
    STAMINA(0),
    DEFENCE(1),
    STRENGTH(2),
    INTELLIGENCE(3),
    VOMITING(4);

    override fun toString(): String {
        return super.toString().lowercase()
    }
}
package dev.moru3.RPGCoreServer.websocket.data

enum class RequestType(val id: Byte) {
    SET_PLAYER_DATA(0),
    SERVER_NOTIFY(1);

    companion object {
        val types = mutableMapOf<Byte, RequestType>()

        init {
            values().forEach { types[it.id] = it }
        }

        fun getById(id: Byte): RequestType {
            return types[id]?:throw IllegalArgumentException()
        }
    }
}

enum class DataType(val id: Byte) {
    BALANCE(0),
    EXPERIENCE(1),
    LEVEL(2),
    MAX_STAMINA(3),
    MAX_HEALTH(4),
    STATUS_POINT(5),
    STATUS_LEVEL(6),
    CUSTOM_DATA(7),
    STAMINA(8),
    HEALTH(9);

    companion object {
        private val types = mutableMapOf<Byte, DataType>()

        init { DataType.values().forEach { types[it.id] = it } }

        fun getById(id: Byte): DataType { return types[id]?:throw IllegalArgumentException() }
    }
}

enum class CalcType(val id: Byte) {
    SET(0),
    ADD(1),
    SUB(2);

    companion object {
        private val types = mutableMapOf<Byte, CalcType>()

        init { CalcType.values().forEach { types[it.id] = it } }

        fun getById(id: Byte): CalcType { return types[id]?:throw IllegalArgumentException() }
    }
}
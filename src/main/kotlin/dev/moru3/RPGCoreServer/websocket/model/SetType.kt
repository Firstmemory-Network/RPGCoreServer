package dev.moru3.RPGCoreServer.websocket.model

/**
 * invoke: (元の値, 計算する値)->結果
 */
enum class SetType(val id: Byte) {
    SET(0),
    ADD(1),
    SUB(2);

    fun Double.calc(v: Double): Double {
        return when(this@SetType) {
            SET -> v
            ADD -> this+v
            SUB -> this-v
        }
    }

    fun Float.calc(v: Float): Float {
        return when(this@SetType) {
            SET -> v
            ADD -> this+v
            SUB -> this-v
        }
    }

    fun Int.calc(v: Int): Int {
        return when(this@SetType) {
            SET -> v
            ADD -> Math.addExact(this, v)
            SUB -> Math.subtractExact(this, v)
        }
    }

    fun Long.calc(v: Long): Long {
        return when(this@SetType) {
            SET -> v
            ADD -> Math.addExact(this, v)
            SUB -> Math.subtractExact(this, v)
        }
    }

    companion object {
        val types = mutableMapOf<Byte, SetType>()

        init {
            values().forEach { types[it.id] = it }
        }

        fun getById(id: Byte): SetType { return types[id]?:throw IllegalArgumentException() }
    }
}
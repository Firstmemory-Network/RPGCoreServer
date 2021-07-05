package dev.moru3.RPGCoreServer.websocket.model

/**
 * invoke: (元の値, 計算する値)->結果
 */
enum class SetType {
    SET,
    ADD,
    SUB;

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
            ADD -> this+v
            SUB -> this-v
        }
    }

    fun Long.calc(v: Long): Long {
        return when(this@SetType) {
            SET -> v
            ADD -> this+v
            SUB -> this-v
        }
    }
}
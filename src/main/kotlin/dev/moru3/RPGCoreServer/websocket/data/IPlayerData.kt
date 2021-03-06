package dev.moru3.RPGCoreServer.websocket.data

interface IPlayerData {

    var isOnline: Boolean

    /**
     * かね
     */
    var balance: Long

    /**
     * EXP
     */
    var experience: Long

    /**
     * レベル
     */
    var level: Int

    /**
     * 最大スタミナ
     */
    var maxStamina: Int

    /**
     * 最大体力
     */
    var maxHealth: Int

    /**
     * ステータスポイント。
     */
    var statusPoint: Int

    /**
     * スキルレベルを保管しています。
     */
    val skillSet: ISkillSet

    /**
     * カスタムデータが保管されています。
     */
    val customData: MutableMap<String, String>

    /**
     * スタミナ
     */
    var stamina: Int

    /**
     * 体力
     */
    var health: Double

    /**
     * すべてのプレイヤーデータを再ロードします。
     */
    fun reload()
}
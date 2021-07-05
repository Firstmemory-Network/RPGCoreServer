package dev.moru3.RPGCoreServer.websocket.model

class ExperienceIn(override val uuid: String, val value: Int, val type: SetType = SetType.SET): Player

class ExperienceOut(override val uuid: String, val value: Int, val type: SetType = SetType.SET, val result: Int): Player
package dev.moru3.RPGCoreServer.websocket

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.moru3.RPGCoreServer.managers.DataManager.Companion.getPlayerData
import dev.moru3.RPGCoreServer.managers.PermissionType
import dev.moru3.RPGCoreServer.managers.SessionManager
import dev.moru3.RPGCoreServer.managers.SessionManager.Companion.blockAddress
import dev.moru3.RPGCoreServer.managers.SessionManager.Companion.sessions
import dev.moru3.RPGCoreServer.managers.SessionManager.Companion.strikeAddress
import dev.moru3.RPGCoreServer.websocket.data.DataType
import dev.moru3.RPGCoreServer.websocket.data.DataType.*
import dev.moru3.RPGCoreServer.websocket.data.RequestType
import dev.moru3.RPGCoreServer.websocket.data.RequestType.*
import dev.moru3.RPGCoreServer.websocket.model.SetType
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketController: TextWebSocketHandler() {
    val gson = Gson()
    val authenticatedSessions = mutableListOf<WebSocketSession>()

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        exception.printStackTrace()
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("${session.remoteAddress?.hostName?:"null"} からのセッションが確立されました。")
        sessions.add(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
        println("${session.remoteAddress?.hostName?:"null"} からのセッションが切断されました。")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val address = session.remoteAddress?.hostName?: run { session.close(CloseStatus.POLICY_VIOLATION);throw Exception() }
            if(message.payload.isEmpty()) { throw Exception("The JSON format is not correct.") }
            val body = try { gson.fromJson(message.payload, JsonObject::class.java) } catch (e: Exception) { throw Exception("The JSON format is not correct.") }
            if(!authenticatedSessions.contains(session)) {
                if(body.has("token")) {
                    val token = body["token"].asString
                    if(SessionManager.isEnableToken(token, PermissionType.CONNECT_WEBSOCKET)) {
                        authenticatedSessions.add(session)
                        session.sendMessage(TextMessage("{\"response_type\": 1,\"isAuthenticated\": true}"))
                        return
                    } else {
                        session.sendMessage(TextMessage("{\"response_type\": 1,\"isAuthenticated\": false,\"strike\": ${(strikeAddress[address]?:3)-1}}"))
                    }
                } else {
                    session.sendMessage(TextMessage("{\"response_type\": 1,\"isAuthenticated\": false,\"strike\": ${(strikeAddress[address]?:3)-1}}"))
                    session.close(CloseStatus.POLICY_VIOLATION)
                }
                strikeAddress[address] = strikeAddress[address]?.minus(1)?:2
                println("[重要] アドレス[${address}] ストライク[${strikeAddress[address]}]")
                if(strikeAddress[address]?:1<=0) {
                    println("[重要] アドレス[${address}] をブロックしました。")
                    blockAddress.add(address)
                    session.close(CloseStatus.POLICY_VIOLATION)
                }
                return
            } else {
                when (RequestType.getById(body["request_type"].asByte)) {
                    SET_PLAYER_DATA -> { requestSetPlayerData(session, body) }
                }
            }
        } catch (e: Exception) {
            session.sendMessage(TextMessage("{\"response_type\": 3,\"message\": \"${e.message}\"}"))
            return
        }
    }

    fun requestSetPlayerData(session: WebSocketSession, body: JsonObject) {
        body.addProperty("response_type", "update_player_data")
        val playerData = getPlayerData(body["player_unique_id"].asString)
        when (DataType.getById(body["data_type"].asByte)) {
            BALANCE -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asLong
                setType.apply { playerData.balance = playerData.balance.calc(value) }
                body.add("result", JsonPrimitive(playerData.balance))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            EXPERIENCE -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asLong
                setType.apply { playerData.balance = playerData.experience.calc(value) }
                body.add("result", JsonPrimitive(playerData.experience))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            LEVEL -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asInt
                setType.apply { playerData.level = playerData.level.calc(value) }
                body.add("result", JsonPrimitive(playerData.level))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            MAX_STAMINA -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asInt
                setType.apply { playerData.maxStamina = playerData.maxStamina.calc(value) }
                body.add("result", JsonPrimitive(playerData.maxStamina))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            MAX_HEALTH -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asInt
                setType.apply { playerData.maxHealth = playerData.maxHealth.calc(value) }
                body.add("result", JsonPrimitive(playerData.maxHealth))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            STATUS_POINT -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asInt
                setType.apply { playerData.statusPoint = playerData.statusPoint.calc(value) }
                body.add("result", JsonPrimitive(playerData.statusPoint))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            SKILL -> {
                val skills = body["skills"].asJsonObject
            }
            CUSTOM_DATA -> {

            }
            STAMINA -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asInt
                setType.apply { playerData.stamina = playerData.stamina.calc(value) }
                body.add("result", JsonPrimitive(playerData.stamina))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
            HEALTH -> {
                val setType = SetType.getById(body["set_type"].asByte)
                val value = body["value"].asDouble
                setType.apply { playerData.health = playerData.health.calc(value) }
                body.add("result", JsonPrimitive(playerData.health))
                sessions.forEach { it.sendMessage(TextMessage(gson.toJson(body))) }
            }
        }
    }

    init {
        println("インスタンスが生成されました。")
    }
}
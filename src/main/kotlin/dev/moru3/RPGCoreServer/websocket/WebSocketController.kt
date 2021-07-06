package dev.moru3.RPGCoreServer.websocket

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import dev.moru3.RPGCoreServer.websocket.data.DataType
import dev.moru3.RPGCoreServer.websocket.data.DataType.*
import dev.moru3.RPGCoreServer.websocket.data.RequestType
import dev.moru3.RPGCoreServer.websocket.data.RequestType.*
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.PongMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketController: TextWebSocketHandler() {
    val gson = Gson()
    val sessions = mutableListOf<WebSocketSession>()

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        exception.printStackTrace()
    }

    override fun handlePongMessage(session: WebSocketSession, message: PongMessage) {
        println(message)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("セッションが確立されました。")
        println(session)
        sessions.add(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
        println("${session.remoteAddress?.hostName?:"null"} からのセッションが切断されました。")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val body = gson.fromJson(message.payload, JsonObject::class.java)
            when(RequestType.getById(body["request_type"].asByte)) {
                SET_PLAYER_DATA -> {
                    when(DataType.getById(body["data_type"].asByte)) {
                        BALANCE -> {

                        }
                        EXPERIENCE -> {

                        }
                        LEVEL -> {

                        }
                        MAX_STAMINA -> {

                        }
                        MAX_HEALTH -> {

                        }
                        STATUS_POINT -> {

                        }
                        SKILL -> {

                        }
                        CUSTOM_DATA -> {

                        }
                        STAMINA -> {

                        }
                        HEALTH -> {

                        }
                    }
                }
            }
        } catch (e: Exception) { return }
    }

    init {
        println("インスタンスが生成されました。")
    }
}
package dev.moru3.RPGCoreServer.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.PongMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketController: TextWebSocketHandler() {
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
        println(Thread.currentThread().id)
        println(message.toString())
    }

    init {
        println("インスタンスが生成されました。")
    }
}
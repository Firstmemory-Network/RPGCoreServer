package dev.moru3.RPGCoreServer.websocket

import org.springframework.stereotype.Controller
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Controller
class WebSocketController: TextWebSocketHandler() {
    val sessions = mutableListOf<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) { sessions.add(session) }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) { sessions.remove(session) }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

    }
}
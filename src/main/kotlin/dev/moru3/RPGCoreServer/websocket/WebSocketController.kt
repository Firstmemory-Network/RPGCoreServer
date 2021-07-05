package dev.moru3.RPGCoreServer.websocket

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Controller
class WebSocketController: TextWebSocketHandler() {
    val sessions = mutableListOf<WebSocketSession>()
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }
}

class ConnectionCloseException(val reason: CloseStatus): Exception()
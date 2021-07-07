package dev.moru3.RPGCoreServer.websocket.config

import dev.moru3.RPGCoreServer.managers.SessionManager.Companion.blockAddress
import dev.moru3.RPGCoreServer.websocket.WebSocketController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor


@Configuration
@EnableWebSocket
class WebSocketConfig: WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(messageHandler(), "/firstmemory/websocket").addInterceptors(
            object: HttpSessionHandshakeInterceptor() {
                //@SECURITY
                override fun beforeHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, attributes: MutableMap<String, Any>): Boolean {
                    if(blockAddress.contains(request.remoteAddress.hostName)) { return false }
                    return true
                }
            }
        )
    }

    @Bean
    fun messageHandler(): WebSocketHandler {
        return WebSocketController()
    }
}
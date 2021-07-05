package dev.moru3.RPGCoreServer.websocket.config

import dev.moru3.RPGCoreServer.websocket.AuthChecker.Companion.checkAuthRegex
import dev.moru3.RPGCoreServer.websocket.AuthChecker.Companion.checkToken
import dev.moru3.RPGCoreServer.websocket.WebSocketController
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(WebSocketController(), "/firstmemory/websocket").addInterceptors(
            object: HttpSessionHandshakeInterceptor() {
                //@SECURITY
                override fun beforeHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, attributes: MutableMap<String, Any>): Boolean {
                    val authHeader = request.headers[HttpHeaders.AUTHORIZATION]?.getOrNull(0)
                    return when {
                        authHeader==null -> {
                            val result = "{\n\t\"response\": 401,\n\t\"message\": \"Please set Authorization in the header.\"\n}"
                            response.body.writer().apply { write(result);flush() }
                            false
                        }
                        !authHeader.checkAuthRegex() -> {
                            val result = "{\n\t\"response\": 401,\n\t\"message\": \"Authorization header is in the wrong format.\"\n}"
                            response.body.writer().apply { write(result);flush() }
                            false
                        }
                        else -> {
                            val token = authHeader.replace("Bearer ", "")
                            if(token.checkToken(request.remoteAddress)) {
                                true
                            } else {
                                val result = "{\n\t\"response\": 401,\n\t\"message\": \"Token is not correct. Please regenerate token..\"\n}"
                                response.body.writer().apply { write(result);flush() }
                                false
                            }
                        }
                    }
                }
            }
        )
    }
}
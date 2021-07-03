package dev.moru3.RPGCoreServer.websocket

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("/firstmemory/rpg/websocket")
class WebSocketController {
    @MessageMapping("/experience")
    @SendTo("/experience")
    fun experience() {

    }
}
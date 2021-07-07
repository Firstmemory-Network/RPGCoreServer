package dev.moru3.RPGCoreServer.restful

import dev.moru3.RPGCoreServer.managers.DataManager
import dev.moru3.RPGCoreServer.managers.PermissionType
import dev.moru3.RPGCoreServer.managers.RequestManager
import dev.moru3.RPGCoreServer.managers.SessionManager
import dev.moru3.RPGCoreServer.websocket.data.IPlayerData
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/firstmemory/restful")
class RestController {
    @RequestMapping("/playerdata", method = [RequestMethod.GET])
    fun getPlayerData(@RequestParam(name = "token", required = false) token: String?, @RequestParam(name = "uuid", required = true) uuid: String, request: HttpServletRequest): IPlayerData {
        if(token==null) {
            RequestManager.accessCountPerHours[request.remoteAddr] = (RequestManager.accessCountPerHours[request.remoteAddr]?:0)+1
            if(RequestManager.accessCountPerHours[request.remoteAddr]?:0>=1000) {
                throw ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests, please limit to 1000 requests per hour if no token has been issued.")
            }
        } else {
            if(!SessionManager.isEnableToken(token, PermissionType.UNLIMITED_REST_REQUEST)) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "The specified Token is not correct.")
            }
        }
        return DataManager.getPlayerData(uuid)
    }
}
package dev.moru3.RPGCoreServer

import me.moru3.sqlow.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.WebSocketConnectionManager
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.messaging.WebSocketStompClient

@SpringBootApplication
class RpgCoreServerApplication

fun main(args: Array<String>) {
	try {
		SQLow.connect(DatabaseType.MYSQL, "127.0.0.1", 3537, "rpgcoreapi", "Fs&l7TVYGuWWtDI=1q:-ZH=qiW!V[0g\$8,1ejrfM7mx<eG2e+b", "RPGCoreAPI", mutableMapOf<String, String>())
	} catch (e: Exception) {
		e.printStackTrace()
		throw Exception()
	}

	println("====================== テーブルを作成します ======================")
	Table("userdata").also {
		it.addColumn(Column("uuid", DataType.VARCHAR).setProperty(36).setPrimaryKey(true))
		it.addColumn(Column("money", DataType.BIGINT).setNotNull(true).setDefault(0))
		it.addColumn(Column("exp", DataType.INT).setNotNull(true).setDefault(0))
		it.addColumn(Column("level", DataType.INT).setNotNull(true).setDefault(1))
		it.addColumn(Column("last_level", DataType.INT).setNotNull(true).setDefault(1))
		it.addColumn(Column("status_point", DataType.INT).setNotNull(true).setDefault(0))
		it.addColumn(Column("max_stamina", DataType.INT).setNotNull(true).setDefault(100))
		it.addColumn(Column("max_health", DataType.INT).setNotNull(true).setDefault(100))
	}.apply {
		println(build(false))
		send(false)
	}

	Table("skills").also {
		it.addColumn(Column("uuid", DataType.VARCHAR).setProperty(36).setPrimaryKey(true))
		it.addColumn(Column("stamina", DataType.INT).setDefault(10).setNotNull(true))
		it.addColumn(Column("defence", DataType.INT).setDefault(10).setNotNull(true))
		it.addColumn(Column("strength", DataType.INT).setDefault(10).setNotNull(true))
		it.addColumn(Column("intelligence", DataType.INT).setDefault(10).setNotNull(true))
		it.addColumn(Column("vomiting", DataType.INT).setDefault(10).setNotNull(true))
	}.apply {
		println(build(false))
		send(false)
	}

	Table("custom_data").also {
		it.addColumn(Column("uuid", DataType.VARCHAR).setProperty(36).setNotNull(false))
		it.addColumn(Column("key", DataType.VARCHAR).setProperty(256).setPrimaryKey(true).setNotNull(true))
		it.addColumn(Column("value", DataType.VARCHAR).setProperty(256).setNotNull(false))
	}.apply {
		println(build(false))
		send(false)
	}

	println("====================== テーブルが完了しました- ======================")

	runApplication<RpgCoreServerApplication>(*args)
}

package org.example.project.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlinx.serialization.json.*

fun buildLokiPayload(logs: List<Pair<Long, String>>): JsonObject {
    return buildJsonObject {
        putJsonArray("streams") {
            addJsonObject {
                put("stream", buildJsonObject {
                    put("job", "admin-panel") // Название твоего приложения
                })
                put("values", buildJsonArray {
                    logs.forEach { (timestamp, message) ->
                        add(buildJsonArray {
                            add("${timestamp * 1_000_000}") // Loki требует наносекунды
                            add(message)
                        })
                    }
                })
            }
        }
    }
}

object LogManager {
    private val logs = mutableListOf<Pair<Long, String>>()
    private val mutex = Mutex()

    suspend fun addLog(message: String) {
        mutex.withLock {
            logs.add(Clock.System.now().toEpochMilliseconds() to message)
        }
    }

    suspend fun collectLogs(): List<Pair<Long, String>> {
        return mutex.withLock {
            val copy = logs.toList()
            logs.clear()
            copy
        }
    }
}

fun logMessage(message: String) {
    // Используем корутину для отправки логов в фоновый поток
    CoroutineScope(Dispatchers.Default).launch {
        // Переходим в Dispatchers.IO для работы с логами
        withContext(Dispatchers.Default) {
            // Здесь мы выполняем асинхронное логирование
            LogManager.addLog(message)
        }
    }
}
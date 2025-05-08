package org.example.project.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.*

val httpClient = HttpClient(CIO) {
}

suspend fun sendLogsToLoki(logs: List<Pair<Long, String>>) {
    if (logs.isEmpty()) return

    val payload = buildLokiPayload(logs)

    httpClient.post("http://localhost:3100/loki/api/v1/push") { // Адрес твоего Loki
        setBody(payload)
    }
}

fun startLogSender(scope: CoroutineScope) {
    scope.launch {
        while (true) {
            delay(10_000)
            val logs = LogManager.collectLogs()
            sendLogsToLoki(logs)
        }
    }
}
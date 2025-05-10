package org.example.project.service
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import org.example.project.dao.TokenHolder
import org.example.project.trackerServerUrl
import kotlin.time.Duration.Companion.minutes

object TokenRefresher {
    private var refreshJob: Job? = null

    fun startAutoRefresh() {
        refreshJob?.cancel()
        refreshJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(9.minutes)
                refreshAccessToken()
            }
        }
    }

    val client = HttpClient(CIO) {
    }

    suspend fun login(username : String, password : String) {
        try {
            val response = client.post("${trackerServerUrl}/login") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("username" to username,
                    "password" to password))
            }
            if (response.status == HttpStatusCode.Unauthorized) {
                throw IllegalArgumentException("Wrong username or password")
            }
            val json = response.bodyAsText()
            val data = Json.decodeFromString<Map<String, String>>(json)
            val newAccessToken = data["access_token"] ?: return
            val newRefreshToken = data["refresh_token"] ?: return

            TokenHolder.accessToken = newAccessToken
            TokenHolder.refreshToken = newRefreshToken

        } catch (er : IllegalArgumentException) {
            throw IllegalArgumentException("Wrong username or password")
        } catch (er : Exception) {
            println(er.message)
        }
    }

    private suspend fun refreshAccessToken() {
        val refreshToken = TokenHolder.refreshToken ?: return

        try {
            val response = client.post("http://localhost:8080/refresh") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("refresh_token" to refreshToken))
            }
            val json = response.bodyAsText()
            val data = Json.decodeFromString<Map<String, String>>(json)
            val newAccessToken = data["access_token"] ?: return

            TokenHolder.accessToken = newAccessToken
            println("✅ AccessToken обновлен!")
        } catch (e: Exception) {
            println("❌ Ошибка при обновлении токена: ${e.message}")
        }
    }

    fun stop() {
        refreshJob?.cancel()
    }
}
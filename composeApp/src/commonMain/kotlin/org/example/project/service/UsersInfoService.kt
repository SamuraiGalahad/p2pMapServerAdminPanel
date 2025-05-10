package org.example.project.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.trackerServerUrl

class UsersInfoService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json( Json { ignoreUnknownKeys = true })
        }
    }
    suspend fun getInfo() : List<String> {
        try {
            val resp = client.get("${trackerServerUrl}/charts/active") {
                contentType(ContentType.Application.Json)
            }

            val result = resp.body<List<String>>()
            return result
        } catch (er : Exception) {
            println(er.message)
        }
        return listOf()
    }
}
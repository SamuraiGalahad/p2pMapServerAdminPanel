package org.example.project.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import org.example.project.trackerServerUrl


@Serializable
data class Counters(
    val capabilities : Int,
    val ask : Int,
    val check : Int,
    val layer : Int,
    val tms : Int
)

class ChartsInfoService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json( Json { ignoreUnknownKeys = true })
        }
    }
    suspend fun getInfo() : Counters {
        try {
            val resp = client.get("${trackerServerUrl}/charts/counters") {
                contentType(ContentType.Application.Json)
            }

            val result = resp.body<Counters>()
            return result
        } catch (er : Exception) {
            println(er.message)
        }
        return Counters(0,0,0,0,0)
    }
}
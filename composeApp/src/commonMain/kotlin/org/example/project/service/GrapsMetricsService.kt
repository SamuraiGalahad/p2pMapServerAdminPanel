package org.example.project.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.project.trackerServerUrl

@Serializable
data class GraphResponse(
    val connectedComponentsParam : Int,
    val clusteringCoefficient : Double,
    val algebraicConnectivity : Double,
    val assortativityCoefficient : Double,
    val downloadSpeed: Long,
    val uploadSpeed: Long
)

class GrapsMetricsService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json( Json { ignoreUnknownKeys = true })
        }
    }
    suspend fun getInfo() : GraphResponse {
        try {
            val resp = client.get("${trackerServerUrl}/charts/graphMetrics") {
                contentType(ContentType.Application.Json)
            }

            val result = resp.body<GraphResponse>()
            return result
        } catch (er : Exception) {
            println(er.message)
        }
        return GraphResponse(
            0,
            0.0,
            0.0,
            0.0, 0, 0)
    }
}
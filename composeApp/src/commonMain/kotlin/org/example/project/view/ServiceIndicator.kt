package org.example.project.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json( Json { ignoreUnknownKeys = true })
    }
}

suspend fun checkService(url: String): Boolean = withContext(Dispatchers.Default) {
    try {
        val responseCode = client.get(url).status.value

        responseCode in 200..299 // Считаем сервис доступным, если код ответа успешный
    } catch (e: Exception) {
        false
    }
}

@Composable
fun ServiceStatusIndicator(url: String) {
    var isOnline by remember { mutableStateOf(false) }

    LaunchedEffect(url) {
        while (true) {
            isOnline = checkService(url)
            delay(5000) // Пингуем каждые 5 секунд
        }
    }

    Box(
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(25.dp)
                .background(color = Color.Transparent, shape = CircleShape)
        ) {
            drawCircle(
                color = if (isOnline) Color.Green else Color.Red,
                radius = size.minDimension / 2
            )
        }
    }
}
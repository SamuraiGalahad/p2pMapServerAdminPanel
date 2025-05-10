package org.example.project.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.*

private val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json( Json { ignoreUnknownKeys = true })
    }
}

@Composable
fun ServerPage(scope: CoroutineScope) {

    val localUriHandler = LocalUriHandler.current
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Server Controls", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Button(onClick = {
                scope.launch {
                    client.post("${trackerServerUrl}/server/stop")
                }
            }) {
                Text("Shutdown Server")
            }
            Button(onClick = {
                scope.launch {
                    client.post("${trackerServerUrl}/server/reload")
                }
            }) {
                Text("Reload Server")
            }
            Button(onClick = {
                scope.launch {
                    client.post("${trackerServerUrl}/peers/changealg")
                }
            }) {
                Text("Change Algorithm")
            }
        }

        Divider(Modifier.height(4.dp), color = Color.DarkGray)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Logging and metrics UI")
                Spacer(Modifier.height(10.dp))
                Text("Metrics scrapper")
                Spacer(Modifier.height(10.dp))
                Text("Database administration")
                Spacer(Modifier.height(10.dp))
                Text("Speed Network Test")
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1f)
            ) {
                OutlinedButton(onClick = {
                    localUriHandler.openUri(grafanaUrl)
                }) {
                    Text("Grafana")
                }
                OutlinedButton(onClick = {
                    localUriHandler.openUri(prometeusUrl)
                }) {
                    Text("Prometheus")
                }
                OutlinedButton(onClick = {
                    localUriHandler.openUri(pgAdminUrl)
                }) {
                    Text("PgAdmin")
                }
                OutlinedButton(onClick = {
                    localUriHandler.openUri(speedTestUrl)
                }) {
                    Text("SpeedTest")
                }
            }
        }
        Divider(Modifier.height(4.dp), color = Color.DarkGray)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Tracker Online")
                Spacer(Modifier.height(10.dp))
                Text("Udp Hole-punching Server Online")
                Spacer(Modifier.height(10.dp))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1f)
            ) {
                Spacer(Modifier.height(10.dp))
                ServiceStatusIndicator("${trackerServerUrl}/status/ping")
                Spacer(Modifier.height(10.dp))
                ServiceStatusIndicator("${holePunchUrl}/status")
            }
        }
    }
}
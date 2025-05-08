package org.example.project.view

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun AlertMessage() {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    AlertDialog(
        onDismissRequest = { errorMessage = null },
        confirmButton = {
            Button(onClick = { errorMessage = null }) {
                Text("OK")
            }
        },
        title = { Text("Ошибка") },
        text = { Text(errorMessage ?: "") }
    )
}
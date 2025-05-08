package org.example.project.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.project.dao.TokenHolder
import org.example.project.service.TokenRefresher

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold (
        topBar = {
            TopAppBar(title= { Text("Admin Panel", fontSize = 22.sp, fontStyle = FontStyle.Italic) })
        }
    ) {
        if (error != null) {
            AlertDialog(
                onDismissRequest = { error = null },
                confirmButton = {
                    Button(onClick = { error = null }) {
                        Text("OK")
                    }
                },
                title = { Text("Ошибка") },
                text = { Text(error ?: "") }
            )
        }
        Column(modifier = Modifier.padding(200.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(5.dp))
            TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = {
                scope.launch {
                    try {
                        TokenRefresher.login(username, password)
//                        if (TokenHolder.accessToken != null)
                        onLoginSuccess(
                            "token"
                        )
                        throw IllegalArgumentException()
                    } catch (e: Exception) {
                        error = "Неверный логин или пароль"
                    }
                }
            }) {
                Text("Login")
            }
//            error?.let { Text(it, color = Color.Red) }
        }
    }
}
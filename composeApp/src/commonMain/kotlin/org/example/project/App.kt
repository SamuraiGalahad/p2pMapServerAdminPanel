package org.example.project

import androidx.compose.runtime.*
import org.example.project.service.TokenRefresher
import org.example.project.service.startLogSender
import org.example.project.view.AdminScreen
import org.example.project.view.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class Screens {
    ADMIN, LOGIN
}


@Composable
@Preview
fun App() {
    var page by remember { mutableStateOf(Screens.LOGIN) }
    var token by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        startLogSender(this)
        TokenRefresher.startAutoRefresh()
    }
    when (page) {
        Screens.LOGIN -> LoginScreen(
            onLoginSuccess = {
                token = it
                page = Screens.ADMIN
            }
        )
        Screens.ADMIN -> AdminScreen(token!!)
    }
}

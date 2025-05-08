package org.example.project.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AdminPage {
    USERS, SERVER, CHARTS
}


@Composable
fun AdminScreen(token: String) {
    val scope = rememberCoroutineScope()
    var currentPage by remember { mutableStateOf(AdminPage.USERS) }


    Scaffold (
        topBar = {
            TopAppBar(title= { Text("Admin Panel", fontSize = 22.sp, fontStyle = FontStyle.Italic) },
                navigationIcon={ IconButton({ currentPage = AdminPage.SERVER }) { Icon(Icons.Filled.Settings, contentDescription = "Меню") } },
                actions={
                    IconButton(onClick = { currentPage = AdminPage.CHARTS }) { Icon(Icons.Filled.Info, contentDescription = "О приложении") }
                    IconButton(onClick = { currentPage = AdminPage.USERS }) { Icon(Icons.Filled.DateRange, contentDescription = "Поиск") }
                },
                backgroundColor = Color.White)
        },
        bottomBar = {

        }
    ) {
        Column(modifier = Modifier.padding(50.dp)) {
            Spacer(Modifier.height(8.dp))

            when (currentPage) {
                AdminPage.USERS -> UsersPage(scope)
                AdminPage.SERVER -> ServerPage(scope)
                AdminPage.CHARTS -> ChartsScopePage()
            }
        }
    }
}
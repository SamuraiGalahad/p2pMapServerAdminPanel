package org.example.project.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.dto.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlin.math.*

data class Node(
    val id: Int,
    var position: Offset,
    val info: String // Мета-информация
)

//@Composable
//fun InteractiveGraph() {
//    val nodeRadius = 20f
//
//    // Sample nodes and connections
//    var nodes = remember {
//        mutableStateListOf<Node>(
//            Node(0, Offset(100f, 100f), "Node 0 info"),
//            Node(1, Offset(300f, 200f), "Node 1 info"),
//            Node(2, Offset(200f, 400f), "Node 2 info")
//        )
//    }
//
//    var nodesBuffer = remember { mutableStateListOf<Node>() }
//
//    val connections = remember {mutableStateListOf(
//        0 to 1,
//        1 to 2,
//        2 to 0
//    )}
//
//    // Transformation state
//    var scale by remember { mutableStateOf(1f) }
//    var offset by remember { mutableStateOf(Offset.Zero) }
//
//    // Node dragging and info display
//    var draggingNodeId by remember { mutableStateOf<Int?>(null) }
//    var selectedNodeInfo by remember { mutableStateOf<String?>(null) }
//
//    var lastTapTime by remember { mutableStateOf(0L) }
//    val doubleTapThreshold = 300L
//
//    // State for showing/hiding dialog
//    var showDialog by remember { mutableStateOf(false) }
//
//    // Box to hold the graph and the dialog
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTransformGestures { _, pan, zoom, _ ->
//                    scale *= zoom
//                    offset += pan
//                }
//            }
//    ) {
//        // Canvas for the graph
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .pointerInput(Unit) {
//                    detectDragGestures(
//                        onDragStart = { dragStart ->
//                            // Check if we clicked on a node
//                            draggingNodeId = nodes.indexOfFirst {
//                                (it.position * scale + offset - dragStart).getDistance() <= nodeRadius
//                            }.takeIf { it >= 0 }
//                        },
//                        onDragEnd = {
//                            draggingNodeId = null
//                        },
//                        onDragCancel = {
//                            draggingNodeId = null
//                        },
//                        onDrag = { change, dragAmount ->
//                            draggingNodeId?.let { id ->
//                                val nodess = nodes.toMutableList().also { list ->
//                                    list[id] = list[id].copy(
//                                        position = list[id].position + (dragAmount / scale)
//                                    )
//                                }
//                                nodes.clear()
//                                nodes.addAll(nodess)
//                            }
//                        }
//                    )
//                }.pointerInput(Unit) {
//                    detectTapGestures(
//                        onTap = { clickOffset ->
//                            val currentTime = Clock.System.now().toEpochMilliseconds()
//
//                            // Проверка на двойной клик
//                            if (currentTime - lastTapTime < doubleTapThreshold) {
//                                // Проверка, был ли клик по узлу
//                                nodes.forEach { node ->
//                                    val distance = (node.position - clickOffset).getDistance()
//                                    if (distance <= nodeRadius) {
//                                        selectedNodeInfo = node.info
//                                        showDialog = true // Показываем диалог с информацией
//                                    }
//                                }
//                            }
//                            lastTapTime = currentTime // Обновляем время последнего клика
//                        }
//                    )
//                }
//        ) {
//            with(drawContext.canvas) {
//                save()
//                translate(offset.x, offset.y)
//                scale(scale, scale)
//
//                // Draw connections (simple line)
//                for ((from, to) in connections) {
//                    val start = nodes[from].position
//                    val end = nodes[to].position
//
//                    drawLine(
//                        color = Color.Gray,
//                        start = start,
//                        end = end,
//                        strokeWidth = 2f
//                    )
//                }
//
//                // Draw nodes (with click detection)
//                for (node in nodes) {
//                    drawCircle(
//                        color = Color.Cyan,
//                        radius = nodeRadius,
//                        center = node.position
//                    )
//                }
//
//                restore()
//            }
//        }
//
//        // Всплывающее окно с мета-информацией узла
//        if (showDialog) {
//            AlertDialog(
//                onDismissRequest = { showDialog = false },
//                title = { Text("Node Information") },
//                text = { Text(selectedNodeInfo ?: "No info available.") },
//                confirmButton = {
//                    Button(onClick = { showDialog = false }) {
//                        Text("Close")
//                    }
//                }
//            )
//        }
//    }
//    LaunchedEffect(Unit) {
//        val n = nodes.removeLast()
//        val r = connections.toList()
//        connections.clear()
//        nodes.addAll(connections.filter { it.first != n.id && it.second != n.id })
//
//    }
//}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun UsersPage(scope: CoroutineScope) {

    var name by remember { mutableStateOf("") }
    val users = remember { mutableStateListOf<User>() }

    LaunchedEffect(Unit) {
        while (true) {
            users.clear()
            val randInt = (1..2).shuffled().first()
            for (i in 0..randInt step 1) {
                // тут запрос в бд на топ клиентов с сортировкой по мета информации
                users.add(User(Uuid.random().toString(), "User"))
            }
            delay(5000)
        }
    }

    Column {
        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(users) { user ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(buildString {
                        append(user.id)
                        append(": ")
                        append(user.name)
                    }, modifier = Modifier.weight(1f))
                    Button(onClick = {
                        scope.launch {
                            users.clear()
                        }
                    }) {
                        Text("Delete")
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
        Spacer(Modifier.height(16.dp))
//        InteractiveGraph()
    }
}
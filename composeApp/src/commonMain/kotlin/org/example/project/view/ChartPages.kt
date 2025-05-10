package org.example.project.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import ir.ehsannarmani.compose_charts.RowChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import kotlinx.coroutines.*
import org.example.project.service.ChartsInfoService

enum class ChartsPage { PIE_CHART, DEFAULT}

@Composable
fun ChartsScopePage() {

    var currentPage by remember { mutableStateOf(ChartsPage.PIE_CHART) }

    var currentSavedPage by remember { mutableStateOf(ChartsPage.PIE_CHART) }

    val currentPieChartData = remember { mutableStateListOf<PieChartData>() }


    LaunchedEffect(Unit) {
        while (true) {
            //delay(10000)
            currentPage = ChartsPage.DEFAULT
            val info = withContext(Dispatchers.Default) {
                ChartsInfoService().getInfo()
            }

            print(info)

            val sum = listOf(info.capabilities, info.ask, info.check, info.layer, info.tms).sum()

            if (sum == 0) {
                delay(10000)
                continue
            }

            currentPage = currentSavedPage

            currentPieChartData.clear()
            currentPieChartData.add(PieChartData(
                partName = "Capabilities",
                data = info.capabilities.toDouble(),
                color = Color.Blue,
            ))
            currentPieChartData.add(PieChartData(
                partName = "Ask",
                data = info.ask.toDouble(),
                color = Color.Green,
            ))
            currentPieChartData.add(PieChartData(
                partName = "Check",
                data = info.check.toDouble(),
                color = Color.Red,
            ))
            currentPieChartData.add(PieChartData(
                partName = "Layer",
                data = info.layer.toDouble(),
                color = Color.Cyan,
            ))
            currentPieChartData.add(PieChartData(
                partName = "Tms",
                data = info.tms.toDouble(),
                color = Color.Magenta
            ))
            delay(10000)
        }
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Chart", style = MaterialTheme.typography.h5)

        when (currentPage) {
            ChartsPage.PIE_CHART -> PieChartSample(currentPieChartData)
            ChartsPage.DEFAULT -> DefaultPage()
        }
    }

}

@Composable
fun DefaultPage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Loading data...")
    }
}


@Composable
fun PieChartSample(testPieChartData : List<PieChartData>) {
    PieChart(
        modifier = Modifier.fillMaxSize(),
        pieChartData = testPieChartData,
        ratioLineColor = Color.LightGray,
        textRatioStyle = TextStyle(color = Color.Gray),
    )
}
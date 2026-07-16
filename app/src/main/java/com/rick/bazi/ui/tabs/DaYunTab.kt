package com.rick.bazi.ui.tabs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // IMPORT THIS FOR THE LIST
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rick.bazi.model.BaziDaYunLiuNianLiuYueData
import com.rick.bazi.ui.components.ChartDataPoint
import com.rick.bazi.ui.components.ScoreChart
import com.rick.bazi.ui.components.ScoreDetailItem

@Composable
fun DaYunTab(
    baZiData: BaziDaYunLiuNianLiuYueData,
    selectedIndex: Int,
    onSelectDaYun: (Int) -> Unit
) {
    // 🌟 THE COMPLETE FIX: Clamp the incoming infinite height from the parent screen 🌟
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 2500.dp) // Gives children a finite boundary constraint to map against
    ) {
        // 1. 标题
        Text(
            text = "大运走势",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 2. 曲线图 (ScoreChart handles its own height internally, but its outer wrapper Card needs this boundary)
        val chartData = baZiData.daYunList.mapIndexed { index, daYun ->
            ChartDataPoint(
                label = "${daYun.startAge}岁",
                value = daYun.overallScore,
                extraInfo = "${daYun.yearRange} ${daYun.ganZhi}"
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            ScoreChart(
                dataPoints = chartData,
                modifier = Modifier.padding(8.dp),
                onPointClick = { index -> onSelectDaYun(index) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. 选中大运的详细信息
        if (selectedIndex in baZiData.daYunList.indices) {
            val selectedDaYun = baZiData.daYunList[selectedIndex]

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "当前大运: ${selectedDaYun.yearRange} (${selectedDaYun.ganZhi})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "起运年龄: ${selectedDaYun.startAge}岁",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "综合评分: ${selectedDaYun.overallScore.toInt()}分",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = "详细评分",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 12.dp)
            )

            // Loop through progress blocks safely
            selectedDaYun.details.forEach { detail ->
                ScoreDetailItem(detail = detail)
            }
        }
    }
}
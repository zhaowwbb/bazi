package com.rick.bazi.ui.tabs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rick.bazi.data.MockDataGenerator
import com.rick.bazi.model.BaZiData
import com.rick.bazi.model.LiuNian
import com.rick.bazi.ui.components.ChartDataPoint
import com.rick.bazi.ui.components.ScoreChart
import com.rick.bazi.ui.components.ScoreDetailItem
import com.rick.bazi.ui.components.ScoreDetailPanel

@Composable
fun LiuYueTab(
    baZiData: BaZiData,
    selectedDaYunIndex: Int,
    selectedLiuNianIndex: Int
) {
    val mockData = remember { MockDataGenerator() }
    var selectedLiuYueIndex by remember { mutableStateOf(0) }

    val selectedDaYun = remember(selectedDaYunIndex) {
        baZiData.daYunList.getOrNull(selectedDaYunIndex)
    }

    val liuNianList = remember(selectedDaYun) {
        if (selectedDaYun != null) {
            mockData.generateLiuNianForDaYun(selectedDaYun)
        } else {
            emptyList<LiuNian>()
        }
    }

    val selectedLiuNian = remember(selectedLiuNianIndex, liuNianList) {
        liuNianList.getOrNull(selectedLiuNianIndex)
    }

    val liuYueList = remember(selectedLiuNian) {
        if (selectedLiuNian != null) {
            mockData.generateLiuYueForLiuNian(selectedLiuNian)
        } else {
            emptyList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 2500.dp)
    ) {
        // 标题

            Text(
                text = "流月走势",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (selectedDaYun != null && selectedLiuNian != null) {
                Text(
                    text = "大运: ${selectedDaYun.yearRange} (${selectedDaYun.ganZhi}) | 流年: ${selectedLiuNian.year}年 (${selectedLiuNian.ganZhi})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


        // 曲线图
            if (liuYueList.isNotEmpty()) {
                val chartData = liuYueList.map { liuYue ->
                    ChartDataPoint(
                        label = liuYue.monthName,
                        value = liuYue.overallScore,
                        extraInfo = liuYue.ganZhi
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    ScoreChart(
                        dataPoints = chartData,
                        modifier = Modifier.padding(8.dp),
                        onPointClick = { index ->
                            selectedLiuYueIndex = index
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text(
                    text = "请先选择有效的大运和流年",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }


        // 选中流月的详细信息
        if (selectedLiuYueIndex in liuYueList.indices) {
            val selectedLiuYue = liuYueList[selectedLiuYueIndex]

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${selectedLiuYue.monthName} (${selectedLiuYue.ganZhi})",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "综合评分: ${selectedLiuYue.overallScore.toInt()}分",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


            // 详细评分列表
            selectedLiuYue.details.forEach { detail ->
                ScoreDetailItem(detail = detail)
            }
//            item {
//                ScoreDetailPanel(details = selectedLiuYue.details)
//            }
        }
    }
}
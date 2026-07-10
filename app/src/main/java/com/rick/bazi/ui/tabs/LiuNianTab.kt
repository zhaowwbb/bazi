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
fun LiuNianTab(
    baZiData: BaZiData,
    selectedDaYunIndex: Int,
    selectedLiuNianIndex: Int,
    onSelectLiuNian: (Int) -> Unit
) {
    val mockData = remember { MockDataGenerator() }
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 2500.dp)
    ) {
        // 标题

            Text(
                text = "流年走势",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (selectedDaYun != null) {
                Text(
                    text = "基于大运: ${selectedDaYun.yearRange} (${selectedDaYun.ganZhi})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


        // 曲线图
            if (liuNianList.isNotEmpty()) {
                val chartData = liuNianList.map { liuNian ->
                    ChartDataPoint(
                        label = "${liuNian.year}",
                        value = liuNian.overallScore,
                        extraInfo = liuNian.ganZhi
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
                            onSelectLiuNian(index)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text(
                    text = "请先选择一个有效的大运",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }


        // 选中流年的详细信息
        if (selectedLiuNianIndex in liuNianList.indices) {
            val selectedLiuNian = liuNianList[selectedLiuNianIndex]


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${selectedLiuNian.year}年 (${selectedLiuNian.ganZhi})",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "综合评分: ${selectedLiuNian.overallScore.toInt()}分",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


            // 详细评分列表
            selectedLiuNian.details.forEach { detail ->
                ScoreDetailItem(detail = detail)
            }
//                ScoreDetailPanel(details = selectedLiuNian.details)

        }
    }
}
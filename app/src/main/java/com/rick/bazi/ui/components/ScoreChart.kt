package com.rick.bazi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChartDataPoint(
    val label: String,
    val value: Float,
    val extraInfo: String = ""
)

@Composable
fun ScoreChart(
    dataPoints: List<ChartDataPoint>,
    modifier: Modifier = Modifier,
    onPointClick: (Int) -> Unit = {}
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val textMeasurer = rememberTextMeasurer()
    val fillColor00 = MaterialTheme.colorScheme.primary
    val fillColor01 = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    val fillColor03 = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
    val fillColor07 = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)

    Column(modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .pointerInput(dataPoints) {
                    detectTapGestures { offset ->
                        if (size.width > 0 && dataPoints.isNotEmpty()) {
                            val stepX = size.width.toFloat() / (dataPoints.size - 1).coerceAtLeast(1)
                            val index = ((offset.x / stepX) + 0.5f).toInt()
                                .coerceIn(0, dataPoints.size - 1)
                            selectedIndex = index
                            onPointClick(index)
                        }
                    }
                }
        ) {
            if (dataPoints.isEmpty()) return@Canvas

            val paddingLeft = 80f
            val paddingRight = 40f
            val paddingTop = 30f
            val paddingBottom = 40f

            val chartWidth = size.width - paddingLeft - paddingRight
            val chartHeight = size.height - paddingTop - paddingBottom

            val minValue = 0f
            val maxValue = 110f

            // 绘制网格线和刻度
            val gridLines = 5
            for (i in 0..gridLines) {
                val y = paddingTop + chartHeight * (1 - i.toFloat() / gridLines)
                val value = minValue + (maxValue - minValue) * i / gridLines

                // 水平网格线
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(paddingLeft, y),
                    end = Offset(size.width - paddingRight, y),
                    strokeWidth = 1f
                )

                // Y轴标签
                val textLayoutResult = textMeasurer.measure(
                    text = value.toInt().toString(),
                    style = TextStyle(fontSize = 10.sp, color = Color.Gray)
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = paddingLeft - textLayoutResult.size.width - 8f,
                        y = y - textLayoutResult.size.height / 2f
                    )
                )
            }

            // 绘制曲线
            if (dataPoints.size >= 2) {
                val path = Path()
                val points = dataPoints.mapIndexed { index, point ->
                    val x = paddingLeft + chartWidth * index / (dataPoints.size - 1).coerceAtLeast(1)
                    val y = paddingTop + chartHeight * (1 - point.value / maxValue)
                    Offset(x, y)
                }

                // 填充区域
                val fillPath = Path()
                fillPath.moveTo(points.first().x, paddingTop + chartHeight)
                points.forEach { fillPath.lineTo(it.x, it.y) }
                fillPath.lineTo(points.last().x, paddingTop + chartHeight)
                fillPath.close()

                drawPath(
                    path = fillPath,
                    color = fillColor01
                )

                // 曲线
                path.moveTo(points.first().x, points.first().y)
                for (i in 1 until points.size) {
                    val prev = points[i - 1]
                    val curr = points[i]
                    val controlX1 = prev.x + (curr.x - prev.x) / 3
                    val controlX2 = prev.x + 2 * (curr.x - prev.x) / 3
                    path.cubicTo(controlX1, prev.y, controlX2, curr.y, curr.x, curr.y)
                }

                drawPath(
                    path = path,
                    color = fillColor00,
                    style = Stroke(width = 3f, cap = StrokeCap.Round)
                )

                // 绘制数据点和选中高亮
                points.forEachIndexed { index, point ->
                    val isSelected = index == selectedIndex
                    val radius = if (isSelected) 8f else 4f

                    // 外圈高亮
                    if (isSelected) {
                        drawCircle(
                            color = fillColor03,
                            radius = radius + 6f,
                            center = point
                        )
                    }

                    drawCircle(
                        color = if (isSelected) fillColor00
                        else fillColor07,
                        radius = radius,
                        center = point
                    )

                    // X轴标签
                    val labelText = dataPoints[index].label
                    val textLayoutResult = textMeasurer.measure(
                        text = labelText,
                        style = TextStyle(fontSize = 10.sp, color = Color.Gray)
                    )
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            x = point.x - textLayoutResult.size.width / 2f,
                            y = paddingTop + chartHeight + 10f
                        )
                    )
                }
            }
        }

        // 选中的数据点显示
        if (selectedIndex in dataPoints.indices) {
            val selectedPoint = dataPoints[selectedIndex]
            Text(
                text = "${selectedPoint.label}: ${selectedPoint.value.toInt()}分",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
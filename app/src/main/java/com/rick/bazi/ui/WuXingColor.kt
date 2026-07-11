package com.rick.bazi.ui

import androidx.compose.ui.graphics.Color

/** 天干/地支简单五行配色（木青 火红 土棕 金白/灰 水蓝） */
fun ganColor(gan: String): Color = when(gan) {
    "甲","乙" -> Color(0xFF2E7D32)  // 绿
    "丙","丁" -> Color(0xFFC62828)  // 红
    "戊","己" -> Color(0xFF795548)  // 棕
    "庚","辛" -> Color(0xFF616161)  // 灰
    "壬","癸" -> Color(0xFF1565C0)  // 蓝
    else -> Color.Unspecified
}
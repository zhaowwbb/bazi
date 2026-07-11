package com.rick.bazi.data

//package com.example.bazipan.data

import com.rick.bazi.model.*
import com.rick.bazi.model.BaZiDataMock

object BaZiMock {
    // 简化十神计算示意 — 实际应 (targetGanIndex - dayGanIndex + 10) % 10 映射
    private fun ss(day: String, gan: String) = when {
        day == gan -> "比肩"
        else -> "正官" // 占位，真实算法自行替换
    }

    fun sample(): BaZiDataMock {
        val dayMaster = "甲"
        return BaZiDataMock(
            name = "张三",
            gender = "男",
            birthTime = "1990年 01月 15日 08:30（公历·已校真太阳时）",
            dayMaster = dayMaster,
            pillars = listOf(
                Pillar("年柱", "己", ss(dayMaster,"己"), "巳",
                    listOf(HiddenGan("丙","食神"), HiddenGan("庚","七杀"), HiddenGan("戊","偏财"))),
                Pillar("月柱", "丁", ss(dayMaster,"丁"), "丑",
                    listOf(HiddenGan("己","正财"), HiddenGan("癸","正印"), HiddenGan("辛","正官"))),
                Pillar("日柱", "甲", "日主", "午",
                    listOf(HiddenGan("丁","伤官"), HiddenGan("己","正财"))),
                Pillar("时柱", "戊", ss(dayMaster,"戊"), "辰",
                    listOf(HiddenGan("戊","偏财"), HiddenGan("乙","劫财"), HiddenGan("癸","正印")))
            ),
            currentDaYun  = "庚辰大运（28–37岁）",
            currentLiuNian = "2026 丙午年"
        )
    }
}
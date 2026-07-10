package com.rick.bazi.data

import androidx.compose.runtime.Composable
import com.rick.bazi.model.*

//@Composable
class MockDataGenerator {

//    companion object {
        fun generateMockBaZiData(): BaZiData {
            return BaZiData(
                name = "张三",
                birthDate = "1990年1月15日 08:30",
                yearGanZhi = "己巳",
                monthGanZhi = "丁丑",
                dayGanZhi = "甲午",
                hourGanZhi = "戊辰",
                dayMaster = "甲木",
                daYunList = generateDaYunList(),
                currentDaYunIndex = 2
            )
        }

        private fun generateDaYunList(): List<DaYun> {
            val daYunList = mutableListOf<DaYun>()
            val ages = listOf(8, 18, 28, 38, 48, 58, 68, 78)
            val ganZhis = listOf("戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未", "甲申", "乙酉")
            val scores = listOf(65f, 72f, 85f, 55f, 90f, 70f, 60f, 75f)

            for (i in 0 until 8) {
                val startYear = 1990 + ages[i]
                val endYear = startYear + 9
                daYunList.add(
                    DaYun(
                        yearRange = "$startYear-$endYear",
                        startAge = ages[i],
                        ganZhi = ganZhis[i],
                        overallScore = scores[i],
                        details = generateScoreDetails(scores[i], i)
                    )
                )
            }
            return daYunList
        }

        private fun generateScoreDetails(baseScore: Float, index: Int): List<ScoreDetail> {
            val logics = mapOf(
                ScoreDimension.HEALTH to listOf(
                    "日主甲木得令，身体基础良好",
                    "庚辰大运金旺克木，需注意肝胆健康",
                    "辛巳大运火旺泄木，精力消耗较大",
                    "壬午大运水火既济，身体状况改善"
                ),
                ScoreDimension.WEALTH to listOf(
                    "正财透出，财运稳定增长",
                    "偏财逢冲，投资需谨慎",
                    "财星得地，财富积累加速",
                    "财库被冲，有意外之财但也易破财"
                ),
                ScoreDimension.CAREER to listOf(
                    "官星得力，事业发展顺利",
                    "七杀攻身，职场压力增大",
                    "印星护身，贵人相助",
                    "食伤生财，创意带来机遇"
                ),
                ScoreDimension.FAMILY to listOf(
                    "夫妻宫稳定，家庭和睦",
                    "子午冲导致家庭冲突更激烈",
                    "印星受损，长辈健康需关注",
                    "家庭运势平稳上升"
                ),
                ScoreDimension.LOVE to listOf(
                    "桃花入命，感情机会增多",
                    "夫妻宫逢冲，感情波动较大",
                    "红鸾星动，适合婚嫁",
                    "感情趋于稳定成熟"
                )
            )

            return ScoreDimension.values().map { dim ->
                val adjustedScore = (baseScore + (Math.random() * 40 - 20)).toFloat()
                    .coerceIn(0f, 100f)
                val logicList = logics[dim] ?: listOf("暂无详细解释")
                val logicIndex = index.coerceAtMost(logicList.size - 1)

                ScoreDetail(
                    dimension = dim,
                    score = adjustedScore,
                    logicExplanation = logicList[logicIndex]
                )
            }
        }

        fun generateLiuNianForDaYun(daYun: DaYun): List<LiuNian> {
            val startYear = daYun.yearRange.split("-")[0].toInt()
            return (0 until 10).map { offset ->
                val year = startYear + offset
                val baseScore = daYun.overallScore + (Math.random() * 50 - 25).toFloat()
                LiuNian(
                    year = year,
                    ganZhi = calculateYearGanZhi(year),
                    overallScore = baseScore.coerceIn(0f, 100f),
                    details = generateScoreDetails(baseScore.coerceIn(0f, 100f), offset)
                )
            }
        }

        fun generateLiuYueForLiuNian(liuNian: LiuNian): List<LiuYue> {
            val monthNames = listOf(
                "正月", "二月", "三月", "四月", "五月", "六月",
                "七月", "八月", "九月", "十月", "冬月", "腊月"
            )
            val monthGanZhis = listOf(
                "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未",
                "壬申", "癸酉", "甲戌", "乙亥", "丙子", "丁丑"
            )

            return (0 until 12).map { month ->
                val baseScore = liuNian.overallScore + (Math.random() * 40 - 20).toFloat()
                LiuYue(
                    month = month + 1,
                    monthName = monthNames[month],
                    ganZhi = monthGanZhis[month],
                    overallScore = baseScore.coerceIn(0f, 100f),
                    details = generateScoreDetails(baseScore.coerceIn(0f, 100f), month)
                )
            }
        }

        private fun calculateYearGanZhi(year: Int): String {
            val tianGan = listOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
            val diZhi =
                listOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")
            val ganIndex = (year - 4) % 10
            val zhiIndex = (year - 4) % 12
            return "${tianGan[ganIndex]}${diZhi[zhiIndex]}"
        }

//    }
}
package com.rick.bazi.data

import androidx.compose.runtime.Composable
//import com.rick.bazi.data.BaZiMock.ss
import com.rick.bazi.model.*
import com.rick.bazi.util.BaziFormatter.calcYearMonthGanZhi
import com.rick.bazi.util.DaYunScoringSystem
import com.rick.bazi.util.WuXingExt.getWuXingText
import com.rick.bazi.util.WuXingUtil
import com.rick.bazi.util.BaziFormatter.formatTianganDizhi
import com.rick.bazi.util.BaziFormatter.formatBirthDateTime
import com.rick.bazi.util.BaziFormatter.formatTianGanWithWuxing
import com.rick.bazi.util.BaziFormatter.formatGender
import com.rick.bazi.util.BaziFormatter.convertDizhiToChar
import com.rick.bazi.util.BaziFormatter.convertTianganToChar
import com.rick.bazi.util.BaziFormatter.convertToShiShenCn
import com.rick.bazi.util.BaziFormatter.getHiddenGanList
import com.rick.bazi.util.BaziFormatter.getCurrentYearGanZhi
import com.rick.bazi.util.BaziFormatter.currentDaYunWithAge
import com.rick.bazi.util.BaziFormatter.getCurrentDaYunIndex
import com.rick.bazi.util.BaziFormatter.getGanZhiByYear
import com.rick.bazi.util.DaYunUtil
import com.rick.bazi.util.DiZhiUtil
import com.rick.bazi.util.LiuNianScoringSystem
import com.rick.bazi.util.LiuNianUtil
import com.rick.bazi.util.LiuYueScoringSystem
import com.rick.bazi.util.ShiShenUtil
import com.rick.bazi.util.TianGanUtil

//@Composable
class MockDataGenerator {

    fun generateBaziPanData(data: BaziData): BaZiDataMock {
        val dayMaster = "甲"
        return BaZiDataMock(
            name = "张三",
            gender = formatGender(data),
            birthTime = formatBirthDateTime(data),
            dayMaster = formatTianGanWithWuxing(data.dayTiangan),
            pillars = listOf(
                Pillar(
                    "年柱",
                    convertTianganToChar(data.yearTiangan),
                    convertToShiShenCn(data.dayTiangan, data.yearTiangan),
                    convertDizhiToChar(data.yearDizhi),
                    getHiddenGanList(data.yearDizhi, data.dayTiangan)
                ),
                Pillar(
                    "月柱",
                    convertTianganToChar(data.monthTiangan),
                    convertToShiShenCn(data.dayTiangan, data.monthTiangan),
                    convertDizhiToChar(data.monthDizhi),
                    getHiddenGanList(data.monthDizhi, data.dayTiangan)
                ),
                Pillar(
                    "日柱",
                    convertTianganToChar(data.dayTiangan),
                    "日主",
                    convertDizhiToChar(data.dayDizhi),
                    getHiddenGanList(data.dayDizhi, data.dayTiangan)
//                    listOf(HiddenGan("丁", "伤官"), HiddenGan("己", "正财"))
                ),
                Pillar(
                    "时柱",
                    convertTianganToChar(data.hourTiangan),
                    convertToShiShenCn(data.dayTiangan, data.hourTiangan),
                    convertDizhiToChar(data.hourDizhi),
                    getHiddenGanList(data.hourDizhi, data.dayTiangan)
//                    listOf(
//                        HiddenGan("戊", "偏财"),
//                        HiddenGan("乙", "劫财"),
//                        HiddenGan("癸", "正印")
//                    )
                )
            ),
            currentDaYun = currentDaYunWithAge(data),
            currentLiuNian = getCurrentYearGanZhi()
        )
    }

    //    @Composable
    fun generateRealBaZiData(data: BaziData): BaZiData {
        val daYunList = DaYunScoringSystem.generateDaYunList(data)

        return BaZiData(
            name = "张三",
            birthDate = formatBirthDateTime(data),
            yearGanZhi = formatTianganDizhi(data.yearTiangan, data.yearDizhi),
            monthGanZhi = formatTianganDizhi(data.monthTiangan, data.monthDizhi),
            dayGanZhi = formatTianganDizhi(data.dayTiangan, data.dayDizhi),
            hourGanZhi = formatTianganDizhi(data.hourTiangan, data.hourDizhi),
            dayMaster = formatTianGanWithWuxing(data.dayTiangan),
            daYunList = daYunList,
            currentDaYunIndex = 0
        )
    }

    //    companion object {
//    fun generateMockBaZiData(): BaZiData {
//        return BaZiData(
//            name = "张三",
//            birthDate = "1990年1月15日 08:30",
//            yearGanZhi = "己巳",
//            monthGanZhi = "丁丑",
//            dayGanZhi = "甲午",
//            hourGanZhi = "戊辰",
//            dayMaster = "甲木",
//            daYunList = generateDaYunList(),
//            currentDaYunIndex = 2
//        )
//    }

//    private fun generateDaYunList(): List<DaYun> {
//        val daYunList = mutableListOf<DaYun>()
//        val ages = listOf(8, 18, 28, 38, 48, 58, 68, 78)
//        val ganZhis = listOf("戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未", "甲申", "乙酉")
//        val scores = listOf(65f, 72f, 85f, 55f, 90f, 70f, 60f, 75f)
//
//        for (i in 0 until 8) {
//            val startYear = 1990 + ages[i]
//            val endYear = startYear + 9
//            daYunList.add(
//                DaYun(
//                    yearRange = "$startYear-$endYear",
//                    startAge = ages[i],
//                    ganZhi = ganZhis[i],
//                    overallScore = scores[i],
//                    details = generateScoreDetails(scores[i], i)
//                )
//            )
//        }
//        return daYunList
//    }

    private fun generateScoreDetails(baseScore: Float, index: Int): List<ScoreDetail> {
        val logics = mapOf(
            ScoreDimension.LUCK to listOf(
                "日主甲木得令，身体基础良好",
                "庚辰大运金旺克木，需注意肝胆健康",
                "辛巳大运火旺泄木，精力消耗较大",
                "壬午大运水火既济，身体状况改善"
            ),
//            ScoreDimension.HEALTH to listOf(
//                "日主甲木得令，身体基础良好",
//                "庚辰大运金旺克木，需注意肝胆健康",
//                "辛巳大运火旺泄木，精力消耗较大",
//                "壬午大运水火既济，身体状况改善"
//            ),
//            ScoreDimension.WEALTH to listOf(
//                "正财透出，财运稳定增长",
//                "偏财逢冲，投资需谨慎",
//                "财星得地，财富积累加速",
//                "财库被冲，有意外之财但也易破财"
//            ),
//            ScoreDimension.CAREER to listOf(
//                "官星得力，事业发展顺利",
//                "七杀攻身，职场压力增大",
//                "印星护身，贵人相助",
//                "食伤生财，创意带来机遇"
//            ),
//            ScoreDimension.FAMILY to listOf(
//                "夫妻宫稳定，家庭和睦",
//                "子午冲导致家庭冲突更激烈",
//                "印星受损，长辈健康需关注",
//                "家庭运势平稳上升"
//            ),
//            ScoreDimension.LOVE to listOf(
//                "桃花入命，感情机会增多",
//                "夫妻宫逢冲，感情波动较大",
//                "红鸾星动，适合婚嫁",
//                "感情趋于稳定成熟"
//            )
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

    fun generateLiuNianForDaYun(daYun: DaYun, baziData : BaziData): List<LiuNian> {
        val startYear = daYun.yearRange.split("-")[0].toInt()
        return (0 until 10).map { offset ->
            val year = startYear + offset
//            val baseScore = daYun.overallScore + (Math.random() * 50 - 25).toFloat()
            val baseScore = daYun.overallScore

            val logicDescriptions = mutableListOf<String>()

            val (tianGan, diZhi) = getGanZhiByYear(year)
            val liuNianScore = LiuNianScoringSystem.calcLiuNianScore(
                data = baziData,
                liuNianTg = tianGan,
                liuNianDz = diZhi,
                daYunScore = baseScore,
                logicDescriptions = logicDescriptions
            )

            val adviceText = logicDescriptions.joinToString(separator = "\n")
            val scoreDetails = listOf(            ScoreDetail(
                dimension = ScoreDimension.LUCK,
                score = liuNianScore,
                logicExplanation = adviceText
            ))

            LiuNian(
                year = year,
                ganZhi = calculateYearGanZhi(year),
                overallScore = liuNianScore.coerceIn(0f, 100f),
                details = scoreDetails
//                details = generateScoreDetails(liuNianScore.coerceIn(0f, 100f), offset)
            )
        }
    }

    fun generateLiuYueForLiuNian(liuNian: LiuNian, baziData : BaziData): List<LiuYue> {
        val monthNames = listOf(
            "正月", "二月", "三月", "四月", "五月", "六月",
            "七月", "八月", "九月", "十月", "冬月", "腊月"
        )
//        val monthGanZhis = listOf(
//            "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未",
//            "壬申", "癸酉", "甲戌", "乙亥", "丙子", "丁丑"
//        )

        val months = calcYearMonthGanZhi(liuNian.year)

        return (0 until 12).map { month ->
//            val baseScore = liuNian.overallScore + (Math.random() * 40 - 20).toFloat()
//            val baseScore = liuNian.overallScore
            val tg = months[month].first
            val dz = months[month].second

            val logicDescriptions = mutableListOf<String>()
            val baseScore = LiuYueScoringSystem.calcLiuYueScore(
                data = baziData,
                daYunTg = tg,
                daYunDz = dz,

                liuNianTg = tg,
                liuNianDz = dz,

                liuYueTg = tg,
                liuYueDz = dz,

                liuNianScore = liuNian.overallScore,
                logicDescriptions = logicDescriptions
            )

            val adviceText = logicDescriptions.joinToString(separator = "\n")
            val scoreDetails = listOf(            ScoreDetail(
                dimension = ScoreDimension.LUCK,
                score = baseScore,
                logicExplanation = adviceText
            ))

            LiuYue(
                month = month + 1,
                monthName = monthNames[month],
                ganZhi = convertTianganToChar(tg) + convertDizhiToChar(dz),
                overallScore = baseScore.coerceIn(0f, 100f),
                details = scoreDetails
//                details = generateScoreDetails(baseScore.coerceIn(0f, 100f), month)
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
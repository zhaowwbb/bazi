package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.WuXing
import com.rick.bazi.util.WuXingExt.isKe
import com.rick.bazi.util.WuXingExt.isSheng
import com.rick.bazi.util.WuXingExt.tgWxIsSameAsDayMaster


import com.rick.bazi.data.*
import com.rick.bazi.util.BaziFormatter.getCangGanFromDizhi
//import com.rick.bazi.util.WuXingWeightCalculator.calculateTongRootLevel
import com.rick.bazi.util.WuXingWeightCalculator.calculateTotalWuXingWeights
//import com.rick.bazi.util.WuXingWeightCalculator.getCangGanFromDizhi
import com.rick.bazi.util.WuXingWeightCalculator.getDiZhiSeasonElement
import com.rick.bazi.util.WuXingWeightCalculator.getTianGanWuxing
import com.rick.bazi.util.WuXingWeightCalculator.isSeasonStrongForTian

/**
 * 位置因子数据类
 * 用于存储四柱各位置的五行力量系数
 */
data class PositionFactor(
    val position: String,      // 位置名称：年干/年支/月干/月支/日干/日支/时干/时支
    val pillar: String,        // 所属柱：年/月/日/时
    val isTianGan: Boolean,    // 是否为天干
    val baseCoefficient: Float // 基础系数（未考虑得令/通根修正）
)

/**
 * 获取指定位置的基础系数（未考虑得令/通根修正）
 */
fun getPositionBaseCoefficient(position: String): Float = when (position) {
    "年干" -> 0.5f
    "年支" -> 0.5f
    "月干" -> 1.5f
    "月支" -> 1.2f  // 非得令时的基础系数，得令时会覆盖
    "日干" -> 0f    // 日干不参与外力评分
    "日支" -> 1.5f
    "时干" -> 1.0f
    "时支" -> 1.0f
    else -> 0.5f
}

/**
 * 获取根系数（根据地支位置和得令状态）
 *
 * @param position 位置名称（年支/月支/日支/时支）
 * @param isLingType 得令类型：SAME（当令同气）, BORN（相令生助）, NONE（不得令）
 * @return 修正后的系数
 */
fun getRootFactor(position: String, isLingType: String = "NONE"): Float {
    // 先获取基础系数
    val base = getPositionBaseCoefficient(position)

    // 月支特殊处理：得令时大幅提升
    if (position == "月支") {
        return when (isLingType) {
            "SAME" -> 3.0f  // 当令同气
            "BORN" -> 2.2f  // 相令生助
            else -> base    // 不得令，使用基础系数1.2
        }
    }

    // 其他地支保持基础系数
    return base
}

/**
 * 获取天干位置系数（不考虑通根修正）
 */
fun getTianGanCoefficient(position: String): Float {
    return getPositionBaseCoefficient(position)
}

/**
 * 计算八字五行总权重 V2
 *
 * 以 1.0 为基准系数，所有分数直接用系数累加
 * 不再使用 0~50 分档
 *
 * @param baziData 八字数据
 * @return Map<WuXing, Float> 五行 -> 总权重系数
 */
fun calculateTotalWuXingWeightsV2(baziData: BaziData): Map<WuXing, Float> {

    // 初始化五行权重（全部从 0 开始累加）
    val weights = mutableMapOf(
        WuXing.WUXING_JIN to 0f,
        WuXing.WUXING_MU to 0f,
        WuXing.WUXING_SHUI to 0f,
        WuXing.WUXING_HUO to 0f,
        WuXing.WUXING_TU to 0f
    )

    // 获取日主
    val dayMasterTg = baziData.dayTiangan
    val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(dayMasterTg)

    // 获取月支五行
    val monthDz = baziData.monthDizhi
    val monthWx = WuXingWeightCalculator.getDiZhiMainElement(monthDz)

    // 判断得令类型
    val lingType = when {
        monthWx == dayMasterWx -> "SAME"   // 当令同气
        isSheng(monthWx, dayMasterWx) -> "BORN"  // 相令生助
        else -> "NONE"  // 不得令
    }

    val diZhiUtil = DiZhiUtil()

    // ==============================
    // 1. 天干权重累加
    // ==============================

    // 年干
    val yearGanWx = WuXingWeightCalculator.getTianGanWuxing(baziData.yearTiangan)
    val yearGanCoef = getTianGanCoefficient("年干")
    weights[yearGanWx] = weights[yearGanWx]!! + yearGanCoef

    // 月干
    val monthGanWx = WuXingWeightCalculator.getTianGanWuxing(baziData.monthTiangan)
    val monthGanCoef = getTianGanCoefficient("月干")
    weights[monthGanWx] = weights[monthGanWx]!! + monthGanCoef

    // 日干（日主自身，权重设为 2.0 表示核心地位）
    val dayGanWx = WuXingWeightCalculator.getTianGanWuxing(baziData.dayTiangan)
    weights[dayGanWx] = weights[dayGanWx]!! + 2.0f

    // 时干
    val hourGanWx = WuXingWeightCalculator.getTianGanWuxing(baziData.hourTiangan)
    val hourGanCoef = getTianGanCoefficient("时干")
    weights[hourGanWx] = weights[hourGanWx]!! + hourGanCoef

    // ==============================
    // 2. 地支权重累加（含藏干拆分）
    // ==============================

    // 定义四柱地支及其位置系数
    val diZhiPositions = listOf(
        Triple(baziData.yearDizhi, getRootFactor("年支"), "年支"),
        Triple(baziData.monthDizhi, getRootFactor("月支", lingType), "月支"),
        Triple(baziData.dayDizhi, getRootFactor("日支"), "日支"),
        Triple(baziData.hourDizhi, getRootFactor("时支"), "时支")
    )

    for ((dz, posCoef, posName) in diZhiPositions) {
        // 地支主气五行
        val dzMainWx = WuXingWeightCalculator.getDiZhiMainElement(dz)

        // 主气权重累加（主气占 70%）
        weights[dzMainWx] = weights[dzMainWx]!! + posCoef * 0.7f

        // 获取藏干
        val cangGans = diZhiUtil.getCanggan(dz)

        if (cangGans.size >= 2) {
            // 中气（占 30%）
            val zhongQiWx = WuXingWeightCalculator.getTianGanWuxing(cangGans[1])
            weights[zhongQiWx] = weights[zhongQiWx]!! + posCoef * 0.3f
        }

        if (cangGans.size >= 3) {
            // 余气（占 15%）
            val yuQiWx = WuXingWeightCalculator.getTianGanWuxing(cangGans[2])
            weights[yuQiWx] = weights[yuQiWx]!! + posCoef * 0.15f
        }

        // 特殊：日支额外加 0.3 表示贴身影响力
        if (posName == "日支") {
            weights[dzMainWx] = weights[dzMainWx]!! + 0.3f
        }
    }

    // ==============================
    // 3. 得令修正（月支对日主的影响）
    // ==============================

    when (lingType) {
        "SAME" -> {
            // 当令同气：日主五行额外 +1.0
            weights[dayMasterWx] = weights[dayMasterWx]!! + 1.0f
        }
        "BORN" -> {
            // 相令生助：日主五行额外 +0.6
            weights[dayMasterWx] = weights[dayMasterWx]!! + 0.6f
        }
        "NONE" -> {
            // 不得令：日主五行额外 -0.3
            weights[dayMasterWx] = weights[dayMasterWx]!! - 0.3f
        }
    }

    // ==============================
    // 4. 刑冲合害修正
    // ==============================

    // 日支与月支相冲：日主五行减 20%
    if (diZhiUtil.isDiZhiChong(baziData.dayDizhi, baziData.monthDizhi)) {
        weights[dayMasterWx] = weights[dayMasterWx]!! * 0.8f
    }

    // 日支与年支相冲：日主五行减 10%
    if (diZhiUtil.isDiZhiChong(baziData.dayDizhi, baziData.yearDizhi)) {
        weights[dayMasterWx] = weights[dayMasterWx]!! * 0.9f
    }

    // 日支与月支相合：日主五行加 10%
    if (diZhiUtil.isDiZhiHai(baziData.dayDizhi, baziData.monthDizhi)) {
        weights[dayMasterWx] = weights[dayMasterWx]!! * 1.1f
    }

    // ==============================
    // 5. 调候修正（寒暖燥湿）
    // ==============================

    val monthIndex = when (baziData.monthDizhi) {
        DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHOU, DiZhi.DIZHI_YIN -> 0  // 冬季
        DiZhi.DIZHI_SI, DiZhi.DIZHI_WU, DiZhi.DIZHI_WEI -> 1   // 夏季
        else -> 2  // 春秋
    }

    when (monthIndex) {
        0 -> { // 冬月（亥子丑）：金 +0.3，火 -0.2
            weights[WuXing.WUXING_JIN] = weights[WuXing.WUXING_JIN]!! + 0.3f
            weights[WuXing.WUXING_HUO] = weights[WuXing.WUXING_HUO]!! - 0.2f
        }
        1 -> { // 夏月（巳午未）：水 +0.3，金 -0.2
            weights[WuXing.WUXING_SHUI] = weights[WuXing.WUXING_SHUI]!! + 0.3f
            weights[WuXing.WUXING_JIN] = weights[WuXing.WUXING_JIN]!! - 0.2f
        }
    }

    // ==============================
    // 6. 归一化处理（可选，保证总和为正）
    // ==============================

    // 确保所有权重为非负（最小值为 0）
    for (key in weights.keys) {
        if (weights[key]!! < 0f) {
            weights[key] = 0f
        }
    }

    return weights
}

// ==============================
// 辅助函数
// ==============================

/**
 * 判断五行相生
 */
private fun isSheng(a: WuXing, b: WuXing): Boolean = when (a) {
    WuXing.WUXING_MU -> b == WuXing.WUXING_HUO
    WuXing.WUXING_HUO -> b == WuXing.WUXING_TU
    WuXing.WUXING_TU -> b == WuXing.WUXING_JIN
    WuXing.WUXING_JIN -> b == WuXing.WUXING_SHUI
    WuXing.WUXING_SHUI -> b == WuXing.WUXING_MU
    else -> false
}

/**
 * 判断五行相克
 */
private fun isKe(a: WuXing, b: WuXing): Boolean = when (a) {
    WuXing.WUXING_MU -> b == WuXing.WUXING_TU
    WuXing.WUXING_TU -> b == WuXing.WUXING_SHUI
    WuXing.WUXING_SHUI -> b == WuXing.WUXING_HUO
    WuXing.WUXING_HUO -> b == WuXing.WUXING_JIN
    WuXing.WUXING_JIN -> b == WuXing.WUXING_MU
    else -> false
}

/**
 * 计算通根级别
 */
private fun calculateTongRootLevel(baziData: BaziData, dayMasterWuxing: WuXing): Int {
    val dayMasterTiangan = baziData.dayTiangan

    // 检查年、月、时地支是否通根
    val branches = listOf(baziData.yearDizhi, baziData.monthDizhi, baziData.hourDizhi)

    var tongRootLevel = 0

    branches.forEach { branch ->
        val cangGan = DiZhiUtil().getCanggan(branch)
        if (cangGan.isNotEmpty()) {
            // 检查藏干中是否有与日主相同五行的天干
            val hasSameElement = cangGan.any { cangGanTiangan ->
                val cangGanWuxing = getTianGanWuxing(cangGanTiangan)
                cangGanWuxing == dayMasterWuxing
            }
            if (hasSameElement) {
                tongRootLevel += 2
            }
        }
    }

    return tongRootLevel
}

/**
 * 计算日主旺衰
 *
 * 评分维度：
 * 1. 得令（0~6分）：月令对日主的生助程度
 * 2. 得地（0~6分）：地支通根的数量与质量
 * 3. 得势（0~5分）：天干生扶的力量
 * 4. 通根强度（0~3分）：最高级别通根的品质
 *
 * 总分范围：0~20分
 * 判定标准：
 *   ≥ 10分：VERY_STRONG（极强）
 *   7~9分：STRONG（偏强）
 *   4~6分：MEDIUM（中和）
 *   2~3分：WEAK（偏弱）
 *   0~1分：VERY_WEAK（极弱）
 *
 * @param baziData 八字数据
 * @return DayMasterStrength 日主旺衰结果
 */
fun calculateDayMasterStrength(baziData: BaziData): DayMasterStrength {

    val dayMasterTg = baziData.dayTiangan
    val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(dayMasterTg)

    val diZhiUtil = DiZhiUtil()

    // ==============================
    // 1. 得令评分（0~6分）
    // ==============================
    val monthDz = baziData.monthDizhi
    val monthWx = WuXingWeightCalculator.getDiZhiMainElement(monthDz)

    val seasonScore = when {
        // 当令同气：月支五行与日主相同
        monthWx == dayMasterWx -> 6f
        // 相令生助：月支生日主
        isSheng(monthWx, dayMasterWx) -> 3f
        // 不得令
        else -> 0f
    }

    val isSeasonStrong = seasonScore >= 3f

    // ==============================
    // 2. 得地评分（0~6分）
    // ==============================
    var rootScore = 0f

    // 检查四个地支
    val allDizhi = listOf(
        Triple(baziData.yearDizhi, getRootFactor("年支"), "年支"),
        Triple(baziData.monthDizhi, getRootFactor("月支"), "月支"),
        Triple(baziData.dayDizhi, getRootFactor("日支"), "日支"),
        Triple(baziData.hourDizhi, getRootFactor("时支"), "时支")
    )

    var strongRootCount = 0
    var mediumRootCount = 0
    var weakRootCount = 0

    for ((dz, factor, posName) in allDizhi) {
        val dzMainWx = WuXingWeightCalculator.getDiZhiMainElement(dz)
        val cangGans = diZhiUtil.getCanggan(dz)

        // 本气根：+2分
        if (dzMainWx == dayMasterWx) {
            rootScore += 2f
            strongRootCount++

            // 日支本气根额外+1分
            if (posName == "日支") {
                rootScore += 1f
            }

            // 月支本气根且当令额外+1分
            if (posName == "月支" && seasonScore >= 3f) {
                rootScore += 1f
            }
        }

        // 中气根：+1分
        if (cangGans.size >= 2) {
            val zhongQiWx = WuXingWeightCalculator.getTianGanWuxing(cangGans[1])
            if (zhongQiWx == dayMasterWx) {
                rootScore += 1f
                mediumRootCount++
            }
        }

        // 余气根：+0.5分
        if (cangGans.size >= 3) {
            val yuQiWx = WuXingWeightCalculator.getTianGanWuxing(cangGans[2])
            if (yuQiWx == dayMasterWx) {
                rootScore += 0.5f
                weakRootCount++
            }
        }
    }

    // 得地评分上限6分
    rootScore = minOf(rootScore, 6f)

    val totalRootCount = strongRootCount + mediumRootCount + weakRootCount

// ==============================
// 3. 得势评分（0~5分）- 含地支藏干
// ==============================
    var supportScore = 0f

// ---- 3.1 天干得势评分 ----
    val tianGanItems = listOf(
        Triple(baziData.yearTiangan, getRootFactor("年干"), "年干"),
        Triple(baziData.monthTiangan, getRootFactor("月干"), "月干"),
        Triple(baziData.hourTiangan, getRootFactor("时干"), "时干")
    )

    for ((tg, factor, posName) in tianGanItems) {
        val tgWx = WuXingWeightCalculator.getTianGanWuxing(tg)

        // 比劫（同我）
        if (tgWx == dayMasterWx) {
            when (posName) {
                "月干", "时干" -> supportScore += 2f  // 紧贴+2分
                "年干" -> supportScore += 1f          // 远隔+1分
            }
        }
        // 印星（生我）
        else if (isSheng(tgWx, dayMasterWx)) {
            when (posName) {
                "月干", "时干" -> supportScore += 1.5f  // 紧贴+1.5分
                "年干" -> supportScore += 0.5f          // 远隔+0.5分
            }
        }
    }

// ---- 3.2 地支藏干得势评分 ----
// 检查四个地支的藏干中是否有比劫或印星
    val diZhiItems = listOf(
        Triple(baziData.yearDizhi, getRootFactor("年支"), "年支"),
        Triple(baziData.monthDizhi, getRootFactor("月支"), "月支"),
        Triple(baziData.dayDizhi, getRootFactor("日支"), "日支"),
        Triple(baziData.hourDizhi, getRootFactor("时支"), "时支")
    )

    for ((dz, factor, posName) in diZhiItems) {
        val dzMainWx = WuXingWeightCalculator.getDiZhiMainElement(dz)
        val cangGans = diZhiUtil.getCanggan(dz)

        // 遍历藏干
        for (i in cangGans.indices) {
            val cg = cangGans[i]
            val cgWx = WuXingWeightCalculator.getTianGanWuxing(cg)

            // 藏干权重系数：本气0.7，中气0.3，余气0.15
            val cangWeight = when (i) {
                0 -> 0.7f  // 本气
                1 -> 0.3f  // 中气
                else -> 0.15f  // 余气
            }

            // 比劫（同我）
            if (cgWx == dayMasterWx) {
                when (posName) {
                    "日支" -> supportScore += 1.5f * cangWeight  // 日支贴身+1.5分
                    "月支" -> supportScore += 1.0f * cangWeight  // 月支+1分
                    "时支" -> supportScore += 0.8f * cangWeight  // 时支+0.8分
                    "年支" -> supportScore += 0.5f * cangWeight  // 年支远隔+0.5分
                }
            }
            // 印星（生我）
            else if (isSheng(cgWx, dayMasterWx)) {
                when (posName) {
                    "日支" -> supportScore += 1.2f * cangWeight  // 日支贴身+1.2分
                    "月支" -> supportScore += 0.8f * cangWeight  // 月支+0.8分
                    "时支" -> supportScore += 0.6f * cangWeight  // 时支+0.6分
                    "年支" -> supportScore += 0.4f * cangWeight  // 年支远隔+0.4分
                }
            }
        }
    }

// 得势评分上限5分
    supportScore = minOf(supportScore, 5f)

    // ==============================
    // 4. 通根强度评分（0~3分）
    // ==============================
    val tongRootScore = when {
        strongRootCount > 0 -> 3f  // 有本气根
        mediumRootCount > 0 -> 2f  // 有中气根
        weakRootCount > 0 -> 1f    // 有余气根
        else -> 0f                  // 无根
    }

    // ==============================
    // 5. 刑冲修正
    // ==============================
    var penaltyScore = 0f

    // 日支被月支或年支冲：-2分
    if (diZhiUtil.isDiZhiChong(baziData.dayDizhi, baziData.monthDizhi) ||
        diZhiUtil.isDiZhiChong(baziData.dayDizhi, baziData.yearDizhi)) {
        penaltyScore -= 2f
    }

    // 日支与月支合：+1分
    if (diZhiUtil.isDiZhiHai(baziData.dayDizhi, baziData.monthDizhi)) {
        penaltyScore += 1f
    }

    // ==============================
    // 6. 综合计算总分
    // ==============================
    var totalScore = seasonScore + rootScore + supportScore + tongRootScore + penaltyScore

    // 确保分数在0~20范围内
    totalScore = totalScore.coerceIn(0f, 20f)

    // ==============================
    // 7. 判定旺衰等级
    // ==============================
    val strengthLevel = when {
        totalScore >= 10f -> StrengthLevel.VERY_STRONG
        totalScore >= 7f -> StrengthLevel.STRONG
        totalScore >= 4f -> StrengthLevel.MEDIUM
        totalScore >= 2f -> StrengthLevel.WEAK
        else -> StrengthLevel.VERY_WEAK
    }

    return DayMasterStrength(
        wuxing = dayMasterWx,
        score = totalScore.toInt(),
        isSeasonStrong = isSeasonStrong,
        rootCount = totalRootCount,
        supportWeight = supportScore,
        strengthLevel = strengthLevel
    )
}


/**
 * 计算生助日主的五行权重
 */
private fun calculateSupportWeight(baziData: BaziData, dayMasterWuxing: WuXing): Float {
    val totalWeights = calculateTotalWuXingWeights(baziData)

    // 计算生扶的五行权重
    return when (dayMasterWuxing) {
        WuXing.WUXING_MU -> {
            // 木生火，水生木
            (totalWeights[WuXing.WUXING_MU] ?: 0f) + (totalWeights[WuXing.WUXING_SHUI] ?: 0f)
        }
        WuXing.WUXING_HUO -> {
            // 火生土，木生火
            (totalWeights[WuXing.WUXING_HUO] ?: 0f) + (totalWeights[WuXing.WUXING_MU] ?: 0f)
        }
        WuXing.WUXING_TU -> {
            // 土生金，火生土
            (totalWeights[WuXing.WUXING_TU] ?: 0f) + (totalWeights[WuXing.WUXING_HUO] ?: 0f)
        }
        WuXing.WUXING_JIN -> {
            // 金生水，土生金
            (totalWeights[WuXing.WUXING_JIN] ?: 0f) + (totalWeights[WuXing.WUXING_TU] ?: 0f)
        }
        WuXing.WUXING_SHUI -> {
            // 水生木，金生水
            (totalWeights[WuXing.WUXING_SHUI] ?: 0f) + (totalWeights[WuXing.WUXING_JIN] ?: 0f)
        }
    }
}
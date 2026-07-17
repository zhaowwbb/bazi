package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.util.BaziFormatter.getBeiShengWuXing
import kotlin.math.roundToInt

object WuXingWeightCalculator {

    /**
     * 计算八字五行总权重
     */
    fun calculateTotalWuXingWeights(baziData: BaziData): Map<WuXing, Float> {
        val weights = mutableMapOf<WuXing, Float>()

        // 初始化权重
        WuXing.values().forEach { wuxing ->
            weights[wuxing] = 0.0f
        }

        // 1. 计算年柱权重
        val yearWeight = calculatePillarWeight(
            tiangan = baziData.yearTiangan,
            dizhi = baziData.yearDizhi,
            isMonthPillar = false,
            monthDizhi = baziData.monthDizhi
        )
        addWeights(weights, yearWeight)

        // 2. 计算月柱权重
        val monthWeight = calculatePillarWeight(
            tiangan = baziData.monthTiangan,
            dizhi = baziData.monthDizhi,
            isMonthPillar = true,
            monthDizhi = baziData.monthDizhi
        )
        addWeights(weights, monthWeight)

        // 3. 计算日柱权重
        val dayWeight = calculatePillarWeight(
            tiangan = baziData.dayTiangan,
            dizhi = baziData.dayDizhi,
            isMonthPillar = false,
            monthDizhi = baziData.monthDizhi
        )
        addWeights(weights, dayWeight)

        // 4. 计算时柱权重
        val hourWeight = calculatePillarWeight(
            tiangan = baziData.hourTiangan,
            dizhi = baziData.hourDizhi,
            isMonthPillar = false,
            monthDizhi = baziData.monthDizhi
        )
        addWeights(weights, hourWeight)

        baziData.jinWeight = weights[WuXing.WUXING_JIN] ?: 0f
        baziData.muWeight = weights[WuXing.WUXING_MU] ?: 0f
        baziData.shuiWeight = weights[WuXing.WUXING_SHUI] ?: 0f
        baziData.huoWeight = weights[WuXing.WUXING_HUO] ?: 0f
        baziData.tuWeight = weights[WuXing.WUXING_TU] ?: 0f

        return weights
    }

    /**
     * 计算单个柱的五行权重
     */
    private fun calculatePillarWeight(
        tiangan: TianGan,
        dizhi: DiZhi,
        isMonthPillar: Boolean,
        monthDizhi: DiZhi
    ): Map<WuXing, Float> {
        val weights = mutableMapOf<WuXing, Float>()

        // 初始化权重
        WuXing.values().forEach { wuxing ->
            weights[wuxing] = 0.0f
        }

        // 1. 天干权重
        val tianganWuxing = getTianGanWuxing(tiangan)
        var tianganWeight = 1.0f

        // 天干得月令判断
        if (isMonthPillar) {
            // 月柱天干得月令，权重加倍
            val monthElement = getDiZhiSeasonElement(dizhi)
            if (tianganWuxing == monthElement) {
                tianganWeight *= 2.0f
            }
        }

        weights[tianganWuxing] = weights.getOrDefault(tianganWuxing, 0.0f) + tianganWeight

        // 2. 地支本气权重
        val dizhiMainWuxing = getDiZhiMainElement(dizhi)
        val dizhiMainWeight = getDiZhiMainWeight(dizhi)

        // 地支得月令判断
        val monthElement = getDiZhiSeasonElement(monthDizhi)
        var finalMainWeight = dizhiMainWeight
        if (dizhiMainWuxing == monthElement) {
            finalMainWeight *= 1.5f
        }

        weights[dizhiMainWuxing] = weights.getOrDefault(dizhiMainWuxing, 0.0f) + finalMainWeight

        // 3. 地支余气权重
        val dizhiResidualWuxing = getDiZhiResidualElement(dizhi)
        val dizhiResidualWeight = getDiZhiResidualWeight(dizhi)

        if (dizhiResidualWuxing != null) {
            var finalResidualWeight = dizhiResidualWeight
            if (dizhiResidualWuxing == monthElement) {
                finalResidualWeight *= 1.5f
            }
            weights[dizhiResidualWuxing] = weights.getOrDefault(dizhiResidualWuxing, 0.0f) + finalResidualWeight
        }

        // 4. 地支杂气权重
        val dizhiRemainWuxing = getDiZhiRemainElement(dizhi)
        val dizhiRemainWeight = getDiZhiRemainWeight(dizhi)

        if (dizhiRemainWuxing != null) {
            var finalRemainWeight = dizhiRemainWeight
            if (dizhiRemainWuxing == monthElement) {
                finalRemainWeight *= 1.5f
            }
            weights[dizhiRemainWuxing] = weights.getOrDefault(dizhiRemainWuxing, 0.0f) + finalRemainWeight
        }

        return weights
    }

    /**
     * 天干对应的五行
     */
    fun getTianGanWuxing(tiangan: TianGan): WuXing {
        return when (tiangan) {
            TianGan.TIANGAN_JIA, TianGan.TIANGAN_YI -> WuXing.WUXING_MU
            TianGan.TIANGAN_BING, TianGan.TIANGAN_DING -> WuXing.WUXING_HUO
            TianGan.TIANGAN_WU, TianGan.TIANGAN_JI -> WuXing.WUXING_TU
            TianGan.TIANGAN_GENG, TianGan.TIANGAN_XIN -> WuXing.WUXING_JIN
            TianGan.TIANGAN_REN, TianGan.TIANGAN_GUI -> WuXing.WUXING_SHUI
        }
    }

    /**
     * 地支对应的季节五行（月令）
     */
    fun getDiZhiSeasonElement(dizhi: DiZhi): WuXing {
        return when (dizhi) {
            DiZhi.DIZHI_ZI -> WuXing.WUXING_SHUI  // 冬
            DiZhi.DIZHI_CHOU -> WuXing.WUXING_TU  // 季冬
            DiZhi.DIZHI_YIN -> WuXing.WUXING_MU   // 春
            DiZhi.DIZHI_MOU -> WuXing.WUXING_MU   // 春
            DiZhi.DIZHI_CHEN -> WuXing.WUXING_TU  // 季春
            DiZhi.DIZHI_SI -> WuXing.WUXING_HUO   // 夏
            DiZhi.DIZHI_WU -> WuXing.WUXING_HUO   // 夏
            DiZhi.DIZHI_WEI -> WuXing.WUXING_TU   // 季夏
            DiZhi.DIZHI_SHEN -> WuXing.WUXING_JIN  // 秋
            DiZhi.DIZHI_YOU -> WuXing.WUXING_JIN  // 秋
            DiZhi.DIZHI_XU -> WuXing.WUXING_TU    // 季秋
            DiZhi.DIZHI_HAI -> WuXing.WUXING_SHUI  // 冬
        }
    }

    /**
     * 地支本气对应的五行
     */
    fun getDiZhiMainElement(dizhi: DiZhi): WuXing {
        return when (dizhi) {
            DiZhi.DIZHI_ZI -> WuXing.WUXING_SHUI
            DiZhi.DIZHI_CHOU -> WuXing.WUXING_TU
            DiZhi.DIZHI_YIN -> WuXing.WUXING_MU
            DiZhi.DIZHI_MOU -> WuXing.WUXING_MU
            DiZhi.DIZHI_CHEN -> WuXing.WUXING_TU
            DiZhi.DIZHI_SI -> WuXing.WUXING_HUO
            DiZhi.DIZHI_WU -> WuXing.WUXING_HUO
            DiZhi.DIZHI_WEI -> WuXing.WUXING_TU
            DiZhi.DIZHI_SHEN -> WuXing.WUXING_JIN
            DiZhi.DIZHI_YOU -> WuXing.WUXING_JIN
            DiZhi.DIZHI_XU -> WuXing.WUXING_TU
            DiZhi.DIZHI_HAI -> WuXing.WUXING_SHUI
        }
    }

    /**
     * 地支本气权重
     */
    fun getDiZhiMainWeight(dizhi: DiZhi): Float {
        return when (dizhi) {
            // 子、午、卯、酉 - 只有本气，权重较高
            DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU,
            DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU -> 1.0f

            // 寅、申、巳、亥 - 有本气、余气、杂气
            DiZhi.DIZHI_YIN, DiZhi.DIZHI_SHEN,
            DiZhi.DIZHI_SI, DiZhi.DIZHI_HAI -> 0.6f

            // 辰、戌、丑、未 - 有本气、余气、杂气
            DiZhi.DIZHI_CHEN, DiZhi.DIZHI_XU,
            DiZhi.DIZHI_CHOU, DiZhi.DIZHI_WEI -> 0.5f
        }
    }

    /**
     * 地支余气对应的五行
     */
    fun getDiZhiResidualElement(dizhi: DiZhi): WuXing? {
        return when (dizhi) {
            DiZhi.DIZHI_YIN -> WuXing.WUXING_HUO  // 寅中丙火
            DiZhi.DIZHI_SI -> WuXing.WUXING_JIN   // 巳中庚金
            DiZhi.DIZHI_SHEN -> WuXing.WUXING_SHUI // 申中壬水
            DiZhi.DIZHI_HAI -> WuXing.WUXING_MU   // 亥中甲木

            DiZhi.DIZHI_CHEN -> WuXing.WUXING_MU  // 辰中乙木
            DiZhi.DIZHI_XU -> WuXing.WUXING_JIN   // 戌中辛金
            DiZhi.DIZHI_CHOU -> WuXing.WUXING_SHUI // 丑中癸水
            DiZhi.DIZHI_WEI -> WuXing.WUXING_HUO  // 未中了火

            // 子、午、卯、酉无余气
            else -> null
        }
    }

    /**
     * 地支余气权重
     */
    fun getDiZhiResidualWeight(dizhi: DiZhi): Float {
        return when (dizhi) {
            DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI,
            DiZhi.DIZHI_SHEN, DiZhi.DIZHI_HAI -> 0.3f

            DiZhi.DIZHI_CHEN, DiZhi.DIZHI_XU,
            DiZhi.DIZHI_CHOU, DiZhi.DIZHI_WEI -> 0.3f

            else -> 0.0f
        }
    }

    /**
     * 地支杂气对应的五行
     */
    fun getDiZhiRemainElement(dizhi: DiZhi): WuXing? {
        return when (dizhi) {
            DiZhi.DIZHI_YIN -> WuXing.WUXING_TU   // 寅中戊土
            DiZhi.DIZHI_SI -> WuXing.WUXING_TU    // 巳中戊土
            DiZhi.DIZHI_SHEN -> WuXing.WUXING_TU  // 申中戊土
            DiZhi.DIZHI_HAI -> null  // 亥中无杂气

            DiZhi.DIZHI_CHEN -> WuXing.WUXING_SHUI  // 辰中癸水
            DiZhi.DIZHI_XU -> WuXing.WUXING_HUO     // 戌中丁火
            DiZhi.DIZHI_CHOU -> WuXing.WUXING_JIN   // 丑中辛金
            DiZhi.DIZHI_WEI -> WuXing.WUXING_MU     // 未中乙木

            else -> null
        }
    }

    /**
     * 地支杂气权重
     */
    fun getDiZhiRemainWeight(dizhi: DiZhi): Float {
        return when (dizhi) {
            DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI,
            DiZhi.DIZHI_SHEN -> 0.1f

            DiZhi.DIZHI_CHEN, DiZhi.DIZHI_XU,
            DiZhi.DIZHI_CHOU, DiZhi.DIZHI_WEI -> 0.2f

            else -> 0.0f
        }
    }

    fun isSeasonStrongForTian(tiangan: TianGan, monthDizhi: DiZhi): Boolean {
        val dayMasterWuxing = getTianGanWuxing(tiangan)
        val monthElement = getDiZhiSeasonElement(monthDizhi)
        val isSeasonStrong = (dayMasterWuxing == monthElement || getBeiShengWuXing(monthElement) == dayMasterWuxing)
        return isSeasonStrong
    }

    /**
     * 计算日主（日干）强度
     */
    fun calculateDayMasterStrength(baziData: BaziData): DayMasterStrength {
        val dayMasterWuxing = getTianGanWuxing(baziData.dayTiangan)

        // 1. 计算得月令情况
        val monthElement = getDiZhiSeasonElement(baziData.monthDizhi)
        val isSeasonStrong = isSeasonStrongForTian(baziData.dayTiangan, baziData.monthDizhi)

        // 2. 计算得地情况（地支有无根）
        val rootCount = baziData.strongRootCount + baziData.mediumRootCount + baziData.weakRootCount

        // 3. 计算得势情况（生助的五行）
        val supportWeight = calculateSupportWeight(baziData, dayMasterWuxing)

        // 4. 计算通根情况
        val tongRootLevel = calculateTongRootLevel(baziData, dayMasterWuxing)

        // 综合计算日主强度
        var strengthScore = 0

        // 得月令 +3分
        if (isSeasonStrong) strengthScore += 3

        // 有根 +2分/个
        strengthScore += rootCount * 2

        // 有生助 +1分/0.2权重
        strengthScore += (supportWeight / 0.2f).toInt()

        // 通根 +1-3分
        strengthScore += tongRootLevel

        return DayMasterStrength(
            wuxing = dayMasterWuxing,
            score = strengthScore,
            isSeasonStrong = isSeasonStrong,
            rootCount = rootCount,
            supportWeight = supportWeight,
            strengthLevel = when {
                strengthScore >= 8 -> StrengthLevel.VERY_STRONG
                strengthScore >= 6 -> StrengthLevel.STRONG
                strengthScore >= 4 -> StrengthLevel.MEDIUM
                strengthScore >= 2 -> StrengthLevel.WEAK
                else -> StrengthLevel.VERY_WEAK
            }
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

    /**
     * 计算通根级别
     */
    private fun calculateTongRootLevel(baziData: BaziData, dayMasterWuxing: WuXing): Int {
        val dayMasterTiangan = baziData.dayTiangan

        // 检查年、月、时地支是否通根
        val branches = listOf(baziData.yearDizhi, baziData.monthDizhi, baziData.hourDizhi)

        var tongRootLevel = 0

        branches.forEach { branch ->
            val cangGan = getCangGanFromDizhi(branch)
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
     * 添加权重
     */
    private fun addWeights(target: MutableMap<WuXing, Float>, source: Map<WuXing, Float>) {
        source.forEach { (wuxing, weight) ->
            target[wuxing] = target.getOrDefault(wuxing, 0.0f) + weight
        }
    }

    /**
     * 获取地支藏干
     */
    private fun getCangGanFromDizhi(dizhi: DiZhi): List<TianGan> {
        return when (dizhi) {
            DiZhi.DIZHI_ZI -> listOf(TianGan.TIANGAN_GUI)
            DiZhi.DIZHI_CHOU -> listOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_GUI, TianGan.TIANGAN_XIN)
            DiZhi.DIZHI_YIN -> listOf(TianGan.TIANGAN_JIA, TianGan.TIANGAN_BING, TianGan.TIANGAN_WU)
            DiZhi.DIZHI_MOU -> listOf(TianGan.TIANGAN_YI)
            DiZhi.DIZHI_CHEN -> listOf(TianGan.TIANGAN_YI, TianGan.TIANGAN_WU, TianGan.TIANGAN_GUI)
            DiZhi.DIZHI_SI -> listOf(TianGan.TIANGAN_BING, TianGan.TIANGAN_GENG, TianGan.TIANGAN_WU)
            DiZhi.DIZHI_WU -> listOf(TianGan.TIANGAN_DING, TianGan.TIANGAN_JI)
            DiZhi.DIZHI_WEI -> listOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_DING, TianGan.TIANGAN_YI)
            DiZhi.DIZHI_SHEN -> listOf(TianGan.TIANGAN_GENG, TianGan.TIANGAN_REN, TianGan.TIANGAN_WU)
            DiZhi.DIZHI_YOU -> listOf(TianGan.TIANGAN_XIN)
            DiZhi.DIZHI_XU -> listOf(TianGan.TIANGAN_XIN, TianGan.TIANGAN_DING, TianGan.TIANGAN_WU)
            DiZhi.DIZHI_HAI -> listOf(TianGan.TIANGAN_REN, TianGan.TIANGAN_JIA)
        }
    }
}

/**
 * 日主强度数据类
 */
data class DayMasterStrength(
    val wuxing: WuXing,
    val score: Int,
    val isSeasonStrong: Boolean,
    val rootCount: Int,
    val supportWeight: Float,
    val strengthLevel: StrengthLevel
)

enum class StrengthLevel {
    VERY_WEAK,   // 很弱
    WEAK,        // 弱
    MEDIUM,      // 中等
    STRONG,      // 强
    VERY_STRONG  // 很强
}

/**
 * 五行平衡分析
 */
class WuXingBalanceAnalyzer {

    fun analyzeBalance(weights: Map<WuXing, Float>): WuXingBalance {
        val totalWeight = weights.values.sum()
        val averageWeight = totalWeight / 5

        val excessElements = mutableListOf<WuXing>()
        val deficientElements = mutableListOf<WuXing>()
        val balancedElements = mutableListOf<WuXing>()

        weights.forEach { (wuxing, weight) ->
            when {
                weight > averageWeight * 1.5 -> excessElements.add(wuxing)
                weight < averageWeight * 0.5 -> deficientElements.add(wuxing)
                else -> balancedElements.add(wuxing)
            }
        }

        return WuXingBalance(
            weights = weights,
            totalWeight = totalWeight,
            averageWeight = averageWeight,
            excessElements = excessElements,
            deficientElements = deficientElements,
            balancedElements = balancedElements,
            balanceScore = calculateBalanceScore(weights, averageWeight)
        )
    }

    private fun calculateBalanceScore(weights: Map<WuXing, Float>, averageWeight: Float): Float {
        if (averageWeight == 0f) return 0f

        var variance = 0f
        weights.values.forEach { weight ->
            val diff = weight - averageWeight
            variance += diff * diff
        }
        variance /= weights.size

        // 标准差越小，平衡性越好
        val stdDev = kotlin.math.sqrt(variance.toDouble()).toFloat()

        // 标准化到0-100分
        val maxStdDev = averageWeight * 2
        val balanceScore = ((maxStdDev - stdDev) / maxStdDev * 100).coerceIn(0f, 100f)

        return balanceScore
    }
}

data class WuXingBalance(
    val weights: Map<WuXing, Float>,
    val totalWeight: Float,
    val averageWeight: Float,
    val excessElements: List<WuXing>,
    val deficientElements: List<WuXing>,
    val balancedElements: List<WuXing>,
    val balanceScore: Float
)

/**
 * 使用示例
 */
fun main() {
    // 假设有一个BaziData实例
    val baziData = BaziData(
        yearTiangan = TianGan.TIANGAN_JIA,
        yearDizhi = DiZhi.DIZHI_ZI,
        monthTiangan = TianGan.TIANGAN_BING,
        monthDizhi = DiZhi.DIZHI_YIN,
        dayTiangan = TianGan.TIANGAN_WU,
        dayDizhi = DiZhi.DIZHI_CHEN,
        hourTiangan = TianGan.TIANGAN_GENG,
        hourDizhi = DiZhi.DIZHI_SHEN,
        gender = "Male"
    )

    // 1. 计算五行权重
    val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(baziData)
    println("五行权重: $weights")

    // 2. 计算日主强度
    val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(baziData)
    println("日主强度: $dayMasterStrength")

    // 3. 分析五行平衡
    val analyzer = WuXingBalanceAnalyzer()
    val balance = analyzer.analyzeBalance(weights)
    println("五行平衡分析: $balance")

    // 4. 格式化显示
    val formattedWeights = formatWeightsForDisplay(weights)
    println("格式化权重: $formattedWeights")
}

fun formatWeightsForDisplay(weights: Map<WuXing, Float>): String {
    return weights.entries.joinToString(" | ") { (wuxing, weight) ->
        val wuxingChar = when (wuxing) {
            WuXing.WUXING_JIN -> "金"
            WuXing.WUXING_MU -> "木"
            WuXing.WUXING_SHUI -> "水"
            WuXing.WUXING_HUO -> "火"
            WuXing.WUXING_TU -> "土"
        }
        "$wuxingChar: ${String.format("%.2f", weight)}"
    }
}


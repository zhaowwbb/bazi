package com.rick.bazi.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import com.rick.bazi.data.*
import java.util.Calendar

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziFormatter
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.ConstUtil
import com.rick.bazi.util.DayMasterStrength
import com.rick.bazi.util.StrengthLevel
import com.rick.bazi.util.TaoHuaUtil
import com.rick.bazi.util.WuXingUtil
import com.rick.bazi.util.WuXingWeightCalculator
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.Date


/**
 * 大运流年健康变化分析器
 */
class GrandLuckHealthAnalyzer {

    /**
     * 分析未来两步大运和流年的健康变化
     */
    fun analyzeGrandLuckHealthChanges(
        baziData: BaziData,
        yearsAhead: Int = 10
    ): GrandLuckHealthAnalysis {
        // 获取当前年份
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        // 计算大运
        val grandLuckList = calculateGrandLuck(baziData, currentYear, yearsAhead)

        // 计算流年
        val flowYearList = calculateFlowYears(currentYear, yearsAhead)

        // 分析基础五行
        val baseWeights = WuXingWeightCalculator.calculateTotalWuXingWeights(baziData)

        // 分析大运五行变化
        val grandLuckAnalysis = grandLuckList.map { grandLuck ->
            analyzeSingleGrandLuck(grandLuck, baziData, baseWeights)
        }

        // 分析流年五行变化
        val flowYearAnalysis = flowYearList.map { flowYear ->
            analyzeSingleFlowYear(flowYear, baziData, baseWeights)
        }

        // 分析综合风险
        val combinedRisks = analyzeCombinedRisks(grandLuckAnalysis, flowYearAnalysis, baziData)

        return GrandLuckHealthAnalysis(
            baseWeights = baseWeights,
            grandLuckAnalysis = grandLuckAnalysis,
            flowYearAnalysis = flowYearAnalysis,
            combinedRisks = combinedRisks,
            warningMessages = generateWarnings(combinedRisks, baziData)
        )
    }

    /**
     * 计算未来大运
     */
    private fun calculateGrandLuck(
        baziData: BaziData,
        currentYear: Int,
        yearsAhead: Int
    ): List<GrandLuckInfo> {
        val grandLuckList = mutableListOf<GrandLuckInfo>()

        val startYear = baziData.daYunFirstYear
        if (startYear <= 0) return grandLuckList

        // 计算要显示的大运步数
        val steps = (yearsAhead + 9) / 10  // 向上取整

        for (step in 0 until steps) {
            val grandLuckYear = startYear + step * 10

            // 计算大运天干地支
            val celestial = getNextTiangan(baziData.monthTiangan, step, baziData.dayunForward)
            val terrestrial = getNextDizhi(baziData.monthDizhi, step, baziData.dayunForward)

            val startAge = baziData.daYunStartYear + step * 10

            grandLuckList.add(
                GrandLuckInfo(
                    celestial = celestial,
                    terrestrial = terrestrial,
                    startYear = grandLuckYear,
                    endYear = grandLuckYear + 9,
                    startAge = startAge,
                    endAge = startAge + 9,
                    step = step
                )
            )
        }

        return grandLuckList
    }

    /**
     * 计算未来流年
     */
    private fun calculateFlowYears(currentYear: Int, yearsAhead: Int): List<FlowYearInfo> {
        val flowYearList = mutableListOf<FlowYearInfo>()

        for (i in 0 until yearsAhead) {
            val year = currentYear + i

            // 计算流年天干地支
            val celestial = getYearTiangan(year)
            val terrestrial = getYearDizhi(year)

            flowYearList.add(
                FlowYearInfo(
                    year = year,
                    celestial = celestial,
                    terrestrial = terrestrial
                )
            )
        }

        return flowYearList
    }

    /**
     * 分析单步大运的健康影响
     */
    private fun analyzeSingleGrandLuck(
        grandLuck: GrandLuckInfo,
        baziData: BaziData,
        baseWeights: Map<WuXing, Float>
    ): GrandLuckHealthChange {
        // 计算大运五行
        val luckWeights = calculateGrandLuckWeights(grandLuck, baziData)

        // 计算五行变化
        val weightChanges = calculateWeightChanges(baseWeights, luckWeights)

        // 分析刑冲
        val conflicts = analyzeGrandLuckConflicts(grandLuck, baziData)

        // 分析用神忌神影响
        val yongjiEffect = analyzeYongJiEffect(grandLuck, baziData)

        // 计算健康风险
        val healthRisk = calculateGrandLuckHealthRisk(weightChanges, conflicts, yongjiEffect)

        return GrandLuckHealthChange(
            grandLuck = grandLuck,
            weights = luckWeights,
            weightChanges = weightChanges,
            conflicts = conflicts,
            yongjiEffect = yongjiEffect,
            healthRisk = healthRisk
        )
    }

    /**
     * 分析单个流年的健康影响
     */
    private fun analyzeSingleFlowYear(
        flowYear: FlowYearInfo,
        baziData: BaziData,
        baseWeights: Map<WuXing, Float>
    ): FlowYearHealthChange {
        // 计算流年五行
        val flowYearWeights = calculateFlowYearWeights(flowYear, baziData)

        // 计算五行变化
        val weightChanges = calculateWeightChanges(baseWeights, flowYearWeights)

        // 分析刑冲
        val conflicts = analyzeFlowYearConflicts(flowYear, baziData)

        // 分析用神忌神影响
        val yongjiEffect = analyzeYongJiEffect(flowYear, baziData)

        // 计算健康风险
        val healthRisk = calculateFlowYearHealthRisk(weightChanges, conflicts, yongjiEffect)

        return FlowYearHealthChange(
            flowYear = flowYear,
            weights = flowYearWeights,
            weightChanges = weightChanges,
            conflicts = conflicts,
            yongjiEffect = yongjiEffect,
            healthRisk = healthRisk
        )
    }

    /**
     * 计算大运五行权重
     */
    private fun calculateGrandLuckWeights(
        grandLuck: GrandLuckInfo,
        baziData: BaziData
    ): Map<WuXing, Float> {
        val weights = mutableMapOf<WuXing, Float>().withDefault { 0f }

        // 大运天干权重
        val celestialWuxing = getTianganWuxing(grandLuck.celestial)
        weights[celestialWuxing] = weights.getValue(celestialWuxing) + 1.0f

        // 大运地支权重
        val terrestrialWuxing = getDizhiWuxing(grandLuck.terrestrial)
        weights[terrestrialWuxing] = weights.getValue(terrestrialWuxing) + 1.0f

        // 地支藏干权重
        val cangGanList = getDizhiCangGan(grandLuck.terrestrial)
        cangGanList.forEach { tiangan ->
            val wuxing = getTianganWuxing(tiangan)
            weights[wuxing] = weights.getValue(wuxing) + 0.3f
        }

        return weights
    }

    /**
     * 计算流年五行权重
     */
    private fun calculateFlowYearWeights(
        flowYear: FlowYearInfo,
        baziData: BaziData
    ): Map<WuXing, Float> {
        val weights = mutableMapOf<WuXing, Float>().withDefault { 0f }

        // 流年天干权重
        val celestialWuxing = getTianganWuxing(flowYear.celestial)
        weights[celestialWuxing] = weights.getValue(celestialWuxing) + 0.5f

        // 流年地支权重
        val terrestrialWuxing = getDizhiWuxing(flowYear.terrestrial)
        weights[terrestrialWuxing] = weights.getValue(terrestrialWuxing) + 0.5f

        return weights
    }

    /**
     * 计算五行变化
     */
    private fun calculateWeightChanges(
        baseWeights: Map<WuXing, Float>,
        luckWeights: Map<WuXing, Float>
    ): Map<WuXing, WeightChange> {
        val changes = mutableMapOf<WuXing, WeightChange>()

        WuXing.values().forEach { wuxing ->
            val baseWeight = baseWeights[wuxing] ?: 0f
            val luckWeight = luckWeights[wuxing] ?: 0f
            val changeValue = luckWeight - baseWeight

            val changeType = when {
                changeValue > 0.3 -> ChangeType.STRONG_INCREASE
                changeValue > 0.1 -> ChangeType.MODERATE_INCREASE
                changeValue < -0.3 -> ChangeType.STRONG_DECREASE
                changeValue < -0.1 -> ChangeType.MODERATE_DECREASE
                else -> ChangeType.STABLE
            }

            changes[wuxing] = WeightChange(
                element = wuxing,
                baseWeight = baseWeight,
                newWeight = luckWeight,
                change = changeValue,
                changeType = changeType
            )
        }

        return changes
    }

    /**
     * 分析大运刑冲
     */
    private fun analyzeGrandLuckConflicts(
        grandLuck: GrandLuckInfo,
        baziData: BaziData
    ): List<ConflictAnalysis> {
        val conflicts = mutableListOf<ConflictAnalysis>()

        // 获取八字地支
        val branches = listOf(
            baziData.yearDizhi,
            baziData.monthDizhi,
            baziData.dayDizhi,
            baziData.hourDizhi
        )

        // 检查六冲
        branches.forEach { branch ->
            if (isSixClash(grandLuck.terrestrial, branch)) {
                conflicts.add(
                    createConflictAnalysis(
                        type = ConflictType.CLASH,
                        source = "大运${getDizhiName(grandLuck.terrestrial)}",
                        target = "命局${getDizhiName(branch)}",
                        severity = 4
                    )
                )
            }
        }

        // 检查三刑
        if (isThreePenalty(grandLuck.terrestrial, branches)) {
            conflicts.add(
                createConflictAnalysis(
                    type = ConflictType.PUNISHMENT,
                    source = "大运${getDizhiName(grandLuck.terrestrial)}",
                    target = "命局地支",
                    severity = 5
                )
            )
        }

        return conflicts
    }

    /**
     * 分析流年刑冲
     */
    private fun analyzeFlowYearConflicts(
        flowYear: FlowYearInfo,
        baziData: BaziData
    ): List<ConflictAnalysis> {
        val conflicts = mutableListOf<ConflictAnalysis>()

        // 获取八字地支
        val branches = listOf(
            baziData.yearDizhi,
            baziData.monthDizhi,
            baziData.dayDizhi,
            baziData.hourDizhi
        )

        // 检查六冲
        branches.forEach { branch ->
            if (isSixClash(flowYear.terrestrial, branch)) {
                conflicts.add(
                    createConflictAnalysis(
                        type = ConflictType.CLASH,
                        source = "流年${getDizhiName(flowYear.terrestrial)}",
                        target = "命局${getDizhiName(branch)}",
                        severity = 3
                    )
                )
            }
        }

        return conflicts
    }

    /**
     * 分析用神忌神影响
     */
    private fun analyzeYongJiEffect(
        grandLuck: GrandLuckInfo,
        baziData: BaziData
    ): YongJiEffect {
        val celestialWuxing = getTianganWuxing(grandLuck.celestial)
        val terrestrialWuxing = getDizhiWuxing(grandLuck.terrestrial)

        return analyzeYongJiEffect(celestialWuxing, terrestrialWuxing, baziData)
    }

    private fun analyzeYongJiEffect(
        flowYear: FlowYearInfo,
        baziData: BaziData
    ): YongJiEffect {
        val celestialWuxing = getTianganWuxing(flowYear.celestial)
        val terrestrialWuxing = getDizhiWuxing(flowYear.terrestrial)

        return analyzeYongJiEffect(celestialWuxing, terrestrialWuxing, baziData)
    }

    private fun analyzeYongJiEffect(
        celestialWuxing: WuXing,
        terrestrialWuxing: WuXing,
        baziData: BaziData
    ): YongJiEffect {
        val effects = mutableListOf<YongJiEffectType>()
        var severity = 0

        // 检查是否是用神
        baziData.yongShenList.forEach { yongShen ->
            val yongShenWuxing = getShiShenWuxing(yongShen)

            if (celestialWuxing == yongShenWuxing || terrestrialWuxing == yongShenWuxing) {
                effects.add(YongJiEffectType.YONGSHEN_SUPPORT)
                severity += 2
            }
        }

        // 检查是否是忌神
        baziData.jiShenList.forEach { jiShen ->
            val jiShenWuxing = getShiShenWuxing(jiShen)

            if (celestialWuxing == jiShenWuxing || terrestrialWuxing == jiShenWuxing) {
                effects.add(YongJiEffectType.JISHEN_ATTACK)
                severity += 3
            }
        }

        // 检查用神忌神组合
        if (effects.contains(YongJiEffectType.JISHEN_ATTACK)) {
            // 检查是否刑冲用神
            baziData.yongShenList.forEach { yongShen ->
                val yongShenWuxing = getShiShenWuxing(yongShen)

                // 检查五行相克
                if (isOvercoming(celestialWuxing, yongShenWuxing) ||
                    isOvercoming(terrestrialWuxing, yongShenWuxing)
                ) {
                    effects.add(YongJiEffectType.JISHEN_ATTACK_YONGSHEN)
                    severity += 5
                }
            }
        }

        return YongJiEffect(
            effects = effects,
            severity = severity
        )
    }

    /**
     * 计算大运健康风险
     */
    private fun calculateGrandLuckHealthRisk(
        weightChanges: Map<WuXing, WeightChange>,
        conflicts: List<ConflictAnalysis>,
        yongjiEffect: YongJiEffect
    ): HealthRisk {
        var riskScore = 0

        // 五行大幅变化风险
        weightChanges.values.forEach { change ->
            when (change.changeType) {
                ChangeType.STRONG_INCREASE -> riskScore += 2
                ChangeType.STRONG_DECREASE -> riskScore += 3
                ChangeType.MODERATE_DECREASE -> riskScore += 1
                else -> {}
            }
        }

        // 刑冲风险
        conflicts.forEach { conflict ->
            riskScore += conflict.severity
        }

        // 用神忌神风险
        riskScore += yongjiEffect.severity

        val riskLevel = when {
            riskScore >= 15 -> RiskLevel.CRITICAL
            riskScore >= 10 -> RiskLevel.HIGH
            riskScore >= 6 -> RiskLevel.MEDIUM
            riskScore >= 3 -> RiskLevel.LOW
            else -> RiskLevel.VERY_LOW
        }

        return HealthRisk(
            level = riskLevel,
            score = riskScore,
            description = generateRiskDescription(riskLevel, conflicts, yongjiEffect)
        )
    }

    /**
     * 计算流年健康风险
     */
    private fun calculateFlowYearHealthRisk(
        weightChanges: Map<WuXing, WeightChange>,
        conflicts: List<ConflictAnalysis>,
        yongjiEffect: YongJiEffect
    ): HealthRisk {
        var riskScore = 0

        // 五行变化风险
        weightChanges.values.forEach { change ->
            if (change.changeType == ChangeType.STRONG_DECREASE) {
                riskScore += 2
            }
        }

        // 刑冲风险
        conflicts.forEach { conflict ->
            riskScore += conflict.severity
        }

        // 用神忌神风险
        riskScore += yongjiEffect.severity

        val riskLevel = when {
            riskScore >= 12 -> RiskLevel.CRITICAL
            riskScore >= 8 -> RiskLevel.HIGH
            riskScore >= 5 -> RiskLevel.MEDIUM
            riskScore >= 3 -> RiskLevel.LOW
            else -> RiskLevel.VERY_LOW
        }

        return HealthRisk(
            level = riskLevel,
            score = riskScore,
            description = generateRiskDescription(riskLevel, conflicts, yongjiEffect)
        )
    }

    /**
     * 分析综合风险
     */
    private fun analyzeCombinedRisks(
        grandLuckAnalysis: List<GrandLuckHealthChange>,
        flowYearAnalysis: List<FlowYearHealthChange>,
        baziData: BaziData
    ): List<CombinedRisk> {
        val combinedRisks = mutableListOf<CombinedRisk>()

        // 分析大运和流年的叠加效应
        flowYearAnalysis.forEach { flowYear ->
            val correspondingGrandLuck = findCorrespondingGrandLuck(
                flowYear.flowYear.year,
                grandLuckAnalysis
            )

            if (correspondingGrandLuck != null) {
                val combinedRisk = analyzeCombinedRisk(
                    flowYear = flowYear,
                    grandLuck = correspondingGrandLuck,
                    baziData = baziData
                )
                combinedRisks.add(combinedRisk)
            }
        }

        return combinedRisks
    }

    /**
     * 分析单一年份的综合风险
     */
    private fun analyzeCombinedRisk(
        flowYear: FlowYearHealthChange,
        grandLuck: GrandLuckHealthChange,
        baziData: BaziData
    ): CombinedRisk {
        var combinedScore = flowYear.healthRisk.score + grandLuck.healthRisk.score

        // 检查是否忌神攻击用神
        val isCritical = checkCriticalCondition(flowYear, grandLuck, baziData)
        if (isCritical) {
            combinedScore += 10
        }

        val combinedConflicts = (flowYear.conflicts + grandLuck.conflicts).distinct()

        val riskLevel = when {
            isCritical -> CombinedRiskLevel.CRITICAL
            combinedScore >= 20 -> CombinedRiskLevel.VERY_HIGH
            combinedScore >= 15 -> CombinedRiskLevel.HIGH
            combinedScore >= 10 -> CombinedRiskLevel.MEDIUM
            combinedScore >= 5 -> CombinedRiskLevel.LOW
            else -> CombinedRiskLevel.VERY_LOW
        }

        return CombinedRisk(
            year = flowYear.flowYear.year,
            flowYear = flowYear,
            grandLuck = grandLuck,
            combinedScore = combinedScore,
            riskLevel = riskLevel,
            isCritical = isCritical,
            conflicts = combinedConflicts,
            warnings = generateCombinedWarnings(flowYear, grandLuck, isCritical)
        )
    }

    /**
     * 检查最高级别警告条件
     */
    private fun checkCriticalCondition(
        flowYear: FlowYearHealthChange,
        grandLuck: GrandLuckHealthChange,
        baziData: BaziData
    ): Boolean {
        // 条件1：大运或流年是忌神
        val isJiShen = flowYear.yongjiEffect.effects.contains(YongJiEffectType.JISHEN_ATTACK) ||
                grandLuck.yongjiEffect.effects.contains(YongJiEffectType.JISHEN_ATTACK)

        if (!isJiShen) return false

        // 条件2：刑冲用神
        val hasYongShenAttack =
            flowYear.yongjiEffect.effects.contains(YongJiEffectType.JISHEN_ATTACK_YONGSHEN) ||
                    grandLuck.yongjiEffect.effects.contains(YongJiEffectType.JISHEN_ATTACK_YONGSHEN)

        // 条件3：严重刑冲
        val hasSevereConflict = flowYear.conflicts.any { it.severity >= 4 } ||
                grandLuck.conflicts.any { it.severity >= 4 }

        return hasYongShenAttack || hasSevereConflict
    }

    /**
     * 生成警告信息
     */
    private fun generateWarnings(
        combinedRisks: List<CombinedRisk>,
        baziData: BaziData
    ): List<WarningMessage> {
        val warnings = mutableListOf<WarningMessage>()

        // 检查关键年份
        combinedRisks.forEach { risk ->
            when (risk.riskLevel) {
                CombinedRiskLevel.CRITICAL -> {
                    warnings.add(
                        WarningMessage(
                            year = risk.year,
                            level = WarningLevel.CRITICAL,
                            message = "${risk.year}年：⚠️⚠️⚠️ 最高级别警告！忌神攻击用神，健康风险极高，务必注意！",
                            details = risk.warnings
                        )
                    )
                }

                CombinedRiskLevel.VERY_HIGH -> {
                    warnings.add(
                        WarningMessage(
                            year = risk.year,
                            level = WarningLevel.HIGH,
                            message = "${risk.year}年：⚠️⚠️ 高风险年份，注意健康变化",
                            details = risk.warnings
                        )
                    )
                }

                CombinedRiskLevel.HIGH -> {
                    warnings.add(
                        WarningMessage(
                            year = risk.year,
                            level = WarningLevel.MEDIUM,
                            message = "${risk.year}年：⚠️ 中等风险，建议加强健康监测",
                            details = risk.warnings
                        )
                    )
                }

                else -> {}
            }
        }

        return warnings
    }

    // 工具函数...
    private fun generateRiskDescription(
        level: RiskLevel,
        conflicts: List<ConflictAnalysis>,
        yongjiEffect: YongJiEffect
    ): String {
        val descriptions = mutableListOf<String>()

        descriptions.add("风险等级：$level")

        if (conflicts.isNotEmpty()) {
            descriptions.add("存在${conflicts.size}处刑冲")
        }

        if (yongjiEffect.severity > 0) {
            descriptions.add("用神忌神影响明显")
        }

        return descriptions.joinToString("，")
    }

    private fun generateCombinedWarnings(
        flowYear: FlowYearHealthChange,
        grandLuck: GrandLuckHealthChange,
        isCritical: Boolean
    ): List<String> {
        val warnings = mutableListOf<String>()

        if (isCritical) {
            warnings.add("忌神攻用神，健康风险极高！")
        }

        val allConflicts = flowYear.conflicts + grandLuck.conflicts
        if (allConflicts.any { it.severity >= 4 }) {
            warnings.add("存在严重刑冲关系")
        }

        if (flowYear.yongjiEffect.effects.contains(YongJiEffectType.JISHEN_ATTACK) ||
            grandLuck.yongjiEffect.effects.contains(YongJiEffectType.JISHEN_ATTACK)
        ) {
            warnings.add("忌神当值，需特别谨慎")
        }

        return warnings
    }

    private fun findCorrespondingGrandLuck(
        year: Int,
        grandLuckAnalysis: List<GrandLuckHealthChange>
    ): GrandLuckHealthChange? {
        return grandLuckAnalysis.find {
            year in it.grandLuck.startYear..it.grandLuck.endYear
        }
    }

    private fun createConflictAnalysis(
        type: ConflictType,
        source: String,
        target: String,
        severity: Int
    ): ConflictAnalysis {
        return ConflictAnalysis(
            type = type,
            source = source,
            target = target,
            severity = severity,
            description = when (type) {
                ConflictType.CLASH -> "$source" + "冲" + "$target，易引发波动"
                ConflictType.PUNISHMENT -> "$source" + "刑" + "$target，需防隐患"
                else -> "$source" + "影响" + "$target"
            }
        )
    }
}

/**
 * 数据模型
 */
data class GrandLuckHealthAnalysis(
    val baseWeights: Map<WuXing, Float>,
    val grandLuckAnalysis: List<GrandLuckHealthChange>,
    val flowYearAnalysis: List<FlowYearHealthChange>,
    val combinedRisks: List<CombinedRisk>,
    val warningMessages: List<WarningMessage>
)

data class GrandLuckInfo(
    val celestial: TianGan,
    val terrestrial: DiZhi,
    val startYear: Int,
    val endYear: Int,
    val startAge: Int,
    val endAge: Int,
    val step: Int
)

data class FlowYearInfo(
    val year: Int,
    val celestial: TianGan,
    val terrestrial: DiZhi
)

data class GrandLuckHealthChange(
    val grandLuck: GrandLuckInfo,
    val weights: Map<WuXing, Float>,
    val weightChanges: Map<WuXing, WeightChange>,
    val conflicts: List<ConflictAnalysis>,
    val yongjiEffect: YongJiEffect,
    val healthRisk: HealthRisk
)

data class FlowYearHealthChange(
    val flowYear: FlowYearInfo,
    val weights: Map<WuXing, Float>,
    val weightChanges: Map<WuXing, WeightChange>,
    val conflicts: List<ConflictAnalysis>,
    val yongjiEffect: YongJiEffect,
    val healthRisk: HealthRisk
)

data class WeightChange(
    val element: WuXing,
    val baseWeight: Float,
    val newWeight: Float,
    val change: Float,
    val changeType: ChangeType
)

enum class ChangeType {
    STRONG_INCREASE,    // 大幅增强
    MODERATE_INCREASE,  // 适度增强
    STABLE,            // 稳定
    MODERATE_DECREASE, // 适度减弱
    STRONG_DECREASE    // 大幅减弱
}

data class ConflictAnalysis(
    val type: ConflictType,
    val source: String,
    val target: String,
    val severity: Int,  // 1-5
    val description: String
)

enum class ConflictType {
    CLASH,      // 冲
    PUNISHMENT, // 刑
    OVERCOME,   // 克
    DRAIN       // 泄
}

data class YongJiEffect(
    val effects: List<YongJiEffectType>,
    val severity: Int
)

enum class YongJiEffectType {
    YONGSHEN_SUPPORT,      // 用神扶持
    JISHEN_ATTACK,         // 忌神攻击
    JISHEN_ATTACK_YONGSHEN // 忌神攻击用神
}

data class HealthRisk(
    val level: RiskLevel,
    val score: Int,
    val description: String
)

enum class RiskLevel {
    VERY_LOW,  // 很低
    LOW,       // 低
    MEDIUM,    // 中
    HIGH,      // 高
    CRITICAL   // 危险
}

data class CombinedRisk(
    val year: Int,
    val flowYear: FlowYearHealthChange,
    val grandLuck: GrandLuckHealthChange,
    val combinedScore: Int,
    val riskLevel: CombinedRiskLevel,
    val isCritical: Boolean,
    val conflicts: List<ConflictAnalysis>,
    val warnings: List<String>
)

enum class CombinedRiskLevel {
    VERY_LOW,  // 很低
    LOW,       // 低
    MEDIUM,    // 中
    HIGH,      // 高
    VERY_HIGH, // 很高
    CRITICAL   // 危险
}

data class WarningMessage(
    val year: Int,
    val level: WarningLevel,
    val message: String,
    val details: List<String>
)

enum class WarningLevel {
    LOW,      // 低
    MEDIUM,   // 中
    HIGH,     // 高
    CRITICAL  // 严重
}

/**
 * UI显示函数
 */
@Composable
fun GrandLuckHealthDisplay(
    baziData: BaziData,
    modifier: Modifier = Modifier
) {
    val analyzer = remember { GrandLuckHealthAnalyzer() }
    val analysis = remember(baziData) {
        analyzer.analyzeGrandLuckHealthChanges(baziData, 30)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        Column() {
//            // 标题
//            Text(
//                text = "大运流年健康分析",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF2196F3)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "分析未来10年健康趋势",
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }

//        // 警告信息
//        if (analysis.warningMessages.isNotEmpty()) {
//            Column() {
//                WarningSection(analysis.warningMessages)
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }

//        // 五行基础权重
//        Column() {
//            BaseWeightsCard(analysis.baseWeights)
//            Spacer(modifier = Modifier.height(16.dp))
//        }

        // 大运分析
//        Column()
        Column() {
            analysis.grandLuckAnalysis.forEach { grandLuck ->
                GrandLuckCard(grandLuck)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // 综合风险年份
        Column() {
            Text(
                text = "高风险年份分析",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Column(){
            analysis.combinedRisks.filter { it.riskLevel >= CombinedRiskLevel.MEDIUM }
                .forEach { risk ->
                    CombinedRiskCard(risk)
                    Spacer(modifier = Modifier.height(12.dp))
                }
        }


//        Column() {
//            DisclaimerCard()
//        }
    }
}

@Composable
fun WarningSection(warnings: List<WarningMessage>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        border = BorderStroke(2.dp, Color.Red)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "警告",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(
                    text = "健康预警",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            warnings.forEach { warning ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (warning.level) {
                            WarningLevel.CRITICAL -> Color(0xFFD32F2F)
                            WarningLevel.HIGH -> Color(0xFFFF9800)
                            WarningLevel.MEDIUM -> Color(0xFFFFC107)
                            WarningLevel.LOW -> Color(0xFF4CAF50)
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = warning.message,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        if (warning.details.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            warning.details.forEach { detail ->
                                Text(
                                    text = "• $detail",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BaseWeightsCard(weights: Map<WuXing, Float>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "八字基础五行权重",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                weights.entries.sortedByDescending { it.value }.forEach { (wuxing, weight) ->
                    WeightRow(wuxing, weight)
                }
            }
        }
    }
}

@Composable
fun WeightRow(wuxing: WuXing, weight: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = when (wuxing) {
                WuXing.WUXING_JIN -> "金"
                WuXing.WUXING_MU -> "木"
                WuXing.WUXING_SHUI -> "水"
                WuXing.WUXING_HUO -> "火"
                WuXing.WUXING_TU -> "土"
            },
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 进度条
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFE0E0E0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width((weight / 3).coerceAtMost(1f).dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            when (wuxing) {
                                WuXing.WUXING_JIN -> Color(0xFFFFF3E0)
                                WuXing.WUXING_MU -> Color(0xFFE8F5E9)
                                WuXing.WUXING_SHUI -> Color(0xFFE3F2FD)
                                WuXing.WUXING_HUO -> Color(0xFFFFEBEE)
                                WuXing.WUXING_TU -> Color(0xFFFFF3E0)
                            }
                        )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = String.format("%.2f", weight),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun GrandLuckCard(analysis: GrandLuckHealthChange) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (analysis.healthRisk.level) {
                RiskLevel.CRITICAL -> Color(0xFFFFEBEE)
                RiskLevel.HIGH -> Color(0xFFFFF3E0)
                RiskLevel.MEDIUM -> Color(0xFFFFF9C4)
                RiskLevel.LOW -> Color(0xFFE8F5E9)
                RiskLevel.VERY_LOW -> Color(0xFFE3F2FD)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 大运信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${analysis.grandLuck.startYear}-${analysis.grandLuck.endYear}年",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "(${analysis.grandLuck.startAge}-${analysis.grandLuck.endAge}岁)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Chip(
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = when (analysis.healthRisk.level) {
                            RiskLevel.CRITICAL -> Color.Red
                            RiskLevel.HIGH -> Color(0xFFFF9800)
                            RiskLevel.MEDIUM -> Color(0xFFFFC107)
                            RiskLevel.LOW -> Color(0xFF4CAF50)
                            RiskLevel.VERY_LOW -> Color(0xFF2196F3)
                        }
                    ),
                    shape = RoundedCornerShape(16.dp),
                    label = {
                        Text(
                            text = when (analysis.healthRisk.level) {
                                RiskLevel.CRITICAL -> "危险"
                                RiskLevel.HIGH -> "高"
                                RiskLevel.MEDIUM -> "中"
                                RiskLevel.LOW -> "低"
                                RiskLevel.VERY_LOW -> "很低"
                            },
                            color = Color.White
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 大运柱
            Text(
                text = "大运：${getTianganName(analysis.grandLuck.celestial)}${getDizhiName(analysis.grandLuck.terrestrial)}",
                style = MaterialTheme.typography.bodyLarge
            )

            // 用神忌神影响
            if (analysis.yongjiEffect.effects.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                YongJiEffectDisplay(analysis.yongjiEffect)
            }

            // 刑冲信息
            if (analysis.conflicts.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                ConflictsDisplay(analysis.conflicts)
            }

            // 五行变化
            Spacer(modifier = Modifier.height(8.dp))
            WeightChangesDisplay(analysis.weightChanges)
        }
    }
}

@Composable
fun YongJiEffectDisplay(effect: YongJiEffect) {
    Column {
        Text(
            text = "用神忌神影响：",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )

        effect.effects.forEach { effectType ->
            Text(
                text = when (effectType) {
                    YongJiEffectType.YONGSHEN_SUPPORT -> "✓ 用神扶持，有益健康"
                    YongJiEffectType.JISHEN_ATTACK -> "⚠ 忌神当值，需谨慎"
                    YongJiEffectType.JISHEN_ATTACK_YONGSHEN -> "❌ 忌神攻击用神，风险高"
                },
                style = MaterialTheme.typography.bodySmall,
                color = when (effectType) {
                    YongJiEffectType.YONGSHEN_SUPPORT -> Color.Green
                    YongJiEffectType.JISHEN_ATTACK -> Color(0xFFFF9800)
                    YongJiEffectType.JISHEN_ATTACK_YONGSHEN -> Color.Red
                }
            )
        }
    }
}

@Composable
fun ConflictsDisplay(conflicts: List<ConflictAnalysis>) {
    Column {
        Text(
            text = "刑冲关系：",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )

        conflicts.forEach { conflict ->
            Text(
                text = "• ${conflict.description}",
                style = MaterialTheme.typography.bodySmall,
                color = when (conflict.severity) {
                    5 -> Color.Red
                    4 -> Color(0xFFFF9800)
                    3 -> Color(0xFFFFC107)
                    else -> Color.Gray
                }
            )
        }
    }
}

@Composable
fun WeightChangesDisplay(changes: Map<WuXing, WeightChange>) {
    Column {
        Text(
            text = "五行变化：",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )

        val significantChanges = changes.values
            .filter { it.changeType != ChangeType.STABLE }
            .sortedByDescending { abs(it.change) }
            .take(3)

        significantChanges.forEach { change ->
            Text(
                text = "${getWuxingName(change.element)} ${formatChange(change.change)} (${
                    formatChangeType(
                        change.changeType
                    )
                })",
                style = MaterialTheme.typography.bodySmall,
                color = when (change.changeType) {
                    ChangeType.STRONG_DECREASE -> Color.Red
                    ChangeType.MODERATE_DECREASE -> Color(0xFFFF9800)
                    ChangeType.STRONG_INCREASE -> Color.Green
                    ChangeType.MODERATE_INCREASE -> Color(0xFF4CAF50)
                    else -> Color.Gray
                }
            )
        }
    }
}

@Composable
fun CombinedRiskCard(risk: CombinedRisk) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (risk.riskLevel) {
                CombinedRiskLevel.CRITICAL -> Color(0xFFFFEBEE)
                CombinedRiskLevel.VERY_HIGH -> Color(0xFFFFCDD2)
                CombinedRiskLevel.HIGH -> Color(0xFFFFF3E0)
                else -> Color(0xFFFFF9C4)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${risk.year}年 综合健康风险",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = when (risk.riskLevel) {
                        CombinedRiskLevel.CRITICAL -> Color.Red
                        CombinedRiskLevel.VERY_HIGH -> Color(0xFFD32F2F)
                        CombinedRiskLevel.HIGH -> Color(0xFFFF9800)
                        else -> Color(0xFFFFC107)
                    }
                )

                if (risk.isCritical) {
                    Chip(
                        onClick = {},
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color.Red
                        ),
                        label = {
                            Text(
                                text = "最高警告",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 流年大运信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "流年：${getTianganName(risk.flowYear.flowYear.celestial)}${
                        getDizhiName(
                            risk.flowYear.flowYear.terrestrial
                        )
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "大运：${getTianganName(risk.grandLuck.grandLuck.celestial)}${
                        getDizhiName(
                            risk.grandLuck.grandLuck.terrestrial
                        )
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // 风险详情
            if (risk.warnings.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                risk.warnings.forEach { warning ->
                    Text(
                        text = "• $warning",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red
                    )
                }
            }

            // 建议
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "建议：${generateSuggestion(risk)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DisclaimerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3E0)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "免责声明",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "本分析基于传统八字理论，结果仅供参考。预测结果会随时间变化，建议定期关注身体状况。如有健康问题请及时就医。",
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 16.sp
            )
        }
    }
}

// 工具函数
fun getWuxingName(wuxing: WuXing): String = when (wuxing) {
    WuXing.WUXING_JIN -> "金"
    WuXing.WUXING_MU -> "木"
    WuXing.WUXING_SHUI -> "水"
    WuXing.WUXING_HUO -> "火"
    WuXing.WUXING_TU -> "土"
}

fun formatChange(change: Float): String {
    val sign = if (change > 0) "+" else ""
    return "$sign${String.format("%.2f", change)}"
}

fun formatChangeType(changeType: ChangeType): String = when (changeType) {
    ChangeType.STRONG_INCREASE -> "大幅增强"
    ChangeType.MODERATE_INCREASE -> "适度增强"
    ChangeType.STABLE -> "稳定"
    ChangeType.MODERATE_DECREASE -> "适度减弱"
    ChangeType.STRONG_DECREASE -> "大幅减弱"
}

fun generateSuggestion(risk: CombinedRisk): String = when (risk.riskLevel) {
    CombinedRiskLevel.CRITICAL -> "健康风险极高，建议全面体检，加强健康监测，避免劳累"
    CombinedRiskLevel.VERY_HIGH -> "高风险年份，建议增加体检频率，注意生活习惯"
    CombinedRiskLevel.HIGH -> "中等偏高风险，建议关注身体变化，及时调整"
    else -> "保持良好生活习惯，注意季节变化"
}

// 需要在GrandLuckHealthAnalyzer中添加的工具函数
private fun getTianganWuxing(tiangan: TianGan): WuXing {
    return when (tiangan) {
        TianGan.TIANGAN_JIA, TianGan.TIANGAN_YI -> WuXing.WUXING_MU
        TianGan.TIANGAN_BING, TianGan.TIANGAN_DING -> WuXing.WUXING_HUO
        TianGan.TIANGAN_WU, TianGan.TIANGAN_JI -> WuXing.WUXING_TU
        TianGan.TIANGAN_GENG, TianGan.TIANGAN_XIN -> WuXing.WUXING_JIN
        TianGan.TIANGAN_REN, TianGan.TIANGAN_GUI -> WuXing.WUXING_SHUI
    }
}

private fun getDizhiWuxing(dizhi: DiZhi): WuXing {
    return when (dizhi) {
        DiZhi.DIZHI_ZI, DiZhi.DIZHI_HAI -> WuXing.WUXING_SHUI
        DiZhi.DIZHI_YIN, DiZhi.DIZHI_MOU -> WuXing.WUXING_MU
        DiZhi.DIZHI_SI, DiZhi.DIZHI_WU -> WuXing.WUXING_HUO
        DiZhi.DIZHI_SHEN, DiZhi.DIZHI_YOU -> WuXing.WUXING_JIN
        DiZhi.DIZHI_CHOU, DiZhi.DIZHI_CHEN,
        DiZhi.DIZHI_XU, DiZhi.DIZHI_WEI -> WuXing.WUXING_TU
    }
}

private fun getDizhiCangGan(dizhi: DiZhi): List<TianGan> {
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

private fun getShiShenWuxing(shiShen: ShiShen): WuXing {
    // 这里需要根据你的ShiShen枚举来映射五行
    // 假设有一个工具函数可以获取十神的五行
    return WuXing.WUXING_MU  // 示例
}

private fun isOvercoming(attacker: WuXing, target: WuXing): Boolean {
    return when (attacker) {
        WuXing.WUXING_MU -> target == WuXing.WUXING_TU
        WuXing.WUXING_TU -> target == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> target == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> target == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> target == WuXing.WUXING_MU
    }
}

private fun isSixClash(dizhi1: DiZhi, dizhi2: DiZhi): Boolean {
    val sixClashes = mapOf(
        DiZhi.DIZHI_ZI to DiZhi.DIZHI_WU,
        DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WEI,
        DiZhi.DIZHI_YIN to DiZhi.DIZHI_SHEN,
        DiZhi.DIZHI_MOU to DiZhi.DIZHI_YOU,
        DiZhi.DIZHI_CHEN to DiZhi.DIZHI_XU,
        DiZhi.DIZHI_SI to DiZhi.DIZHI_HAI
    )
    return sixClashes[dizhi1] == dizhi2 || sixClashes[dizhi2] == dizhi1
}

private fun isThreePenalty(dizhi: DiZhi, branches: List<DiZhi>): Boolean {
    val penaltySets = listOf(
        setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI, DiZhi.DIZHI_SHEN),
        setOf(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_XU, DiZhi.DIZHI_WEI),
        setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_MOU)
    )

    return penaltySets.any { penaltySet ->
        val intersection = branches.filter { it in penaltySet }.toSet() + dizhi
        intersection.size >= 2 && penaltySet.containsAll(intersection)
    }
}

private fun getTianganName(tiangan: TianGan): String = when (tiangan) {
    TianGan.TIANGAN_JIA -> "甲"
    TianGan.TIANGAN_YI -> "乙"
    TianGan.TIANGAN_BING -> "丙"
    TianGan.TIANGAN_DING -> "丁"
    TianGan.TIANGAN_WU -> "戊"
    TianGan.TIANGAN_JI -> "己"
    TianGan.TIANGAN_GENG -> "庚"
    TianGan.TIANGAN_XIN -> "辛"
    TianGan.TIANGAN_REN -> "壬"
    TianGan.TIANGAN_GUI -> "癸"
}

private fun getDizhiName(dizhi: DiZhi): String = when (dizhi) {
    DiZhi.DIZHI_ZI -> "子"
    DiZhi.DIZHI_CHOU -> "丑"
    DiZhi.DIZHI_YIN -> "寅"
    DiZhi.DIZHI_MOU -> "卯"
    DiZhi.DIZHI_CHEN -> "辰"
    DiZhi.DIZHI_SI -> "巳"
    DiZhi.DIZHI_WU -> "午"
    DiZhi.DIZHI_WEI -> "未"
    DiZhi.DIZHI_SHEN -> "申"
    DiZhi.DIZHI_YOU -> "酉"
    DiZhi.DIZHI_XU -> "戌"
    DiZhi.DIZHI_HAI -> "亥"
}

private fun getYearTiangan(year: Int): TianGan {
    val index = (year - 4) % 10
    return TianGan.values()[index]
}

private fun getYearDizhi(year: Int): DiZhi {
    val index = (year - 4) % 12
    return DiZhi.values()[index]
}

private fun getNextTiangan(current: TianGan, steps: Int, isForward: Boolean): TianGan {
    val allTiangan = TianGan.values()
    val currentIndex = allTiangan.indexOf(current)
    val newIndex = if (isForward) {
        (currentIndex + steps) % 10
    } else {
        (currentIndex - steps + 10) % 10
    }
    return allTiangan[newIndex]
}

private fun getNextDizhi(current: DiZhi, steps: Int, isForward: Boolean): DiZhi {
    val allDizhi = DiZhi.values()
    val currentIndex = allDizhi.indexOf(current)
    val newIndex = if (isForward) {
        (currentIndex + steps) % 12
    } else {
        (currentIndex - steps + 12) % 12
    }
    return allDizhi[newIndex]
}
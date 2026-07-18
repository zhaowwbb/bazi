package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.model.StrengthLevel
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing
import com.rick.bazi.util.WuXingWeightCalculator
import com.rick.bazi.util.RootCounter

/**
 * 核心用神选择器
 * 从多个候选用神中，通过权重+根气+调候+病药分析，收敛到唯一核心用神
 */
object PrimaryYongShenSelector {

    // 权重阈值常量
    private const val STRONG_THRESHOLD = 2.0f  // 强根阈值
    private const val MEDIUM_THRESHOLD = 1.0f  // 中根阈值

    /**
     * 选择唯一核心用神，并更新到 BaziData
     * @param data BaziData 对象（会被直接修改）
     */
    fun selectAndUpdate(data: BaziData) {
        val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(data)
        val dayMasterWx = getTianGanWuxing(data.dayTiangan)
        val monthDz = data.monthDizhi
        val dayMaster = data.dayTiangan

        // 1. 先计算所有候选用神及其评分
        val candidates = mutableListOf<Pair<ShiShen, Float>>()

        // 2. 调候优先判断
        val tiaoHouCandidates = evaluateTiaoHouCandidates(data, weights, dayMasterWx, monthDz)
        candidates.addAll(tiaoHouCandidates)
        println("调候候选：$tiaoHouCandidates")

        // 3. 通关用神判断
        val tongGuanCandidates = evaluateTongGuanCandidates(data, weights, dayMasterWx)
        candidates.addAll(tongGuanCandidates)
        println("通关候选：$tongGuanCandidates")

        // 4. 扶抑用神判断
        val fuYiCandidates = evaluateFuYiCandidates(data, weights, dayMasterWx, monthDz)
        candidates.addAll(fuYiCandidates)
        println("扶抑候选：$fuYiCandidates")

        println("所有候选：$candidates")

        // 5. 按评分排序，取最高分作为核心用神
        val sortedCandidates = candidates.distinct().sortedByDescending { it.second }

        println("排序后：$sortedCandidates")

        if (sortedCandidates.isEmpty()) {
            // 无合适用神，保持默认
            return
        }

        // 6. 选择核心用神（最高分）
        val primaryYongShen = sortedCandidates.first().first
        data.yongShenList = listOf(primaryYongShen)

        // 7. 计算喜神（生助核心用神的五行）
        val xiShenSet = calculateXiShen(primaryYongShen, dayMaster)
        data.xiyongShenSet = xiShenSet.toSet()

        // 8. 计算忌神（与用神相反的五行）
        val jiShenList = calculateJiShen(data, primaryYongShen, dayMasterWx, weights)

        // 9. 处理调候/通关用神与忌神的冲突
        val adjustedJiShen = handleConflictWithTiaoHouTongGuan(
            data, jiShenList, primaryYongShen, weights, dayMasterWx
        )
        data.jiShenList = adjustedJiShen

        // 10. 更新 allYongShenList（用神+喜神）
        data.allYongShenList = (listOf(primaryYongShen) + xiShenSet).distinct()
    }

    /**
     * 评估调候用神候选
     */
    private fun evaluateTiaoHouCandidates(
        data: BaziData,
        weights: Map<WuXing, Float>,
        dayMasterWx: WuXing,
        monthDz: DiZhi
    ): List<Pair<ShiShen, Float>> {
        val candidates = mutableListOf<Pair<ShiShen, Float>>()
        val month = data.birthDateMonth

        // 判断是否为极端气候
        val isExtremeCold = month in 10..12 || month == 1  // 亥子丑寅月（冬季+初春）
        val isExtremeHot = month in 4..6  // 巳午未月（夏季）

        if (!isExtremeCold && !isExtremeHot) {
            return candidates  // 非极端气候，调候不是首要因素
        }

        if (isExtremeCold) {
            // 寒冬喜火调候
            val huoWeight = weights[WuXing.WUXING_HUO] ?: 0f
            val huoScore = 100f + huoWeight * 10f  // 调候用神基础分很高

            // 火的十神：食神/伤官（日主生火）或正财/偏财（火生日主）
            when (dayMasterWx) {
                WuXing.WUXING_MU -> {  // 木生火 = 食伤
                    candidates.add(Pair(ShiShen.SHISHEN_SHI_SHEN, huoScore))
                    candidates.add(Pair(ShiShen.SHISHEN_SHANG_GUAN, huoScore - 5f))
                }
                WuXing.WUXING_HUO -> {  // 火本身 = 比劫
                    candidates.add(Pair(ShiShen.SHISHEN_BI_JIAN, huoScore))
                }
                WuXing.WUXING_TU -> {  // 火生土 = 印星
                    candidates.add(Pair(ShiShen.SHISHEN_ZHENG_YIN, huoScore))
                    candidates.add(Pair(ShiShen.SHISHEN_PIAN_YIN, huoScore - 5f))
                }
                WuXing.WUXING_JIN -> {  // 火克金 = 官杀
                    candidates.add(Pair(ShiShen.SHISHEN_ZHENG_GUAN, huoScore))
                    candidates.add(Pair(ShiShen.SHISHEN_QI_SHA, huoScore - 5f))
                }
                WuXing.WUXING_SHUI -> {  // 水克火 = 财星（水火既济）
                    candidates.add(Pair(ShiShen.SHISHEN_ZHENG_CAI, huoScore))
                    candidates.add(Pair(ShiShen.SHISHEN_PIAN_CAI, huoScore - 5f))
                }
            }
        }

        if (isExtremeHot) {
            // 炎夏喜水调候
            val shuiWeight = weights[WuXing.WUXING_SHUI] ?: 0f
            val shuiScore = 100f + shuiWeight * 10f

            when (dayMasterWx) {
                WuXing.WUXING_MU -> {  // 水生木 = 印星
                    candidates.add(Pair(ShiShen.SHISHEN_ZHENG_YIN, shuiScore))
                    candidates.add(Pair(ShiShen.SHISHEN_PIAN_YIN, shuiScore - 5f))
                }
                WuXing.WUXING_HUO -> {  // 水克火 = 官杀
                    candidates.add(Pair(ShiShen.SHISHEN_ZHENG_GUAN, shuiScore))
                    candidates.add(Pair(ShiShen.SHISHEN_QI_SHA, shuiScore - 5f))
                }
                WuXing.WUXING_TU -> {  // 土克水 = 财星
                    candidates.add(Pair(ShiShen.SHISHEN_ZHENG_CAI, shuiScore))
                    candidates.add(Pair(ShiShen.SHISHEN_PIAN_CAI, shuiScore - 5f))
                }
                WuXing.WUXING_JIN -> {  // 金生水 = 食伤
                    candidates.add(Pair(ShiShen.SHISHEN_SHI_SHEN, shuiScore))
                    candidates.add(Pair(ShiShen.SHISHEN_SHANG_GUAN, shuiScore - 5f))
                }
                WuXing.WUXING_SHUI -> {  // 水本身 = 比劫
                    candidates.add(Pair(ShiShen.SHISHEN_BI_JIAN, shuiScore))
                }
            }
        }

        return candidates
    }

    /**
     * 评估通关用神候选
     */
    private fun evaluateTongGuanCandidates(
        data: BaziData,
        weights: Map<WuXing, Float>,
        dayMasterWx: WuXing
    ): List<Pair<ShiShen, Float>> {
        val candidates = mutableListOf<Pair<ShiShen, Float>>()

        // 检查是否存在明显的五行相克冲突
        val conflictPairs = findMajorConflicts(data, weights)

        for ((keWx, beiKeWx) in conflictPairs) {
            val tongGuanWx = getTongGuanWuXing(keWx, beiKeWx) ?: continue
            val tongGuanWeight = weights[tongGuanWx] ?: 0f

            // 通关用神基础分 80，加上权重
            val score = 80f + tongGuanWeight * 8f

            // 将通关五行转换为十神
            val shiShens = wuXingToShiShenList(tongGuanWx, data.dayTiangan)
            for (ss in shiShens) {
                candidates.add(Pair(ss, score))
            }
        }

        return candidates
    }

    /**
     * 评估扶抑用神候选
     */
    private fun evaluateFuYiCandidates(
        data: BaziData,
        weights: Map<WuXing, Float>,
        dayMasterWx: WuXing,
        monthDz: DiZhi
    ): List<Pair<ShiShen, Float>> {
        val candidates = mutableListOf<Pair<ShiShen, Float>>()

        val strength = WuXingWeightCalculator.calculateDayMasterStrength(data)
        val isStrong = strength.strengthLevel == StrengthLevel.STRONG ||
                strength.strengthLevel == StrengthLevel.VERY_STRONG
        val isWeak = strength.strengthLevel == StrengthLevel.WEAK ||
                strength.strengthLevel == StrengthLevel.VERY_WEAK

        if (!isStrong && !isWeak) {
            return candidates  // 中和，无需扶抑
        }

        if (isStrong) {
            // 身强：官杀 > 食伤 > 财
            // 官杀（克日主）
            val guanShaWx = getKeWuXing(dayMasterWx)
            val guanShaWeight = weights[guanShaWx] ?: 0f
            val guanShaRoot = getRootScore(data, guanShaWx)
            val guanShaScore = 70f + guanShaWeight * 5f + guanShaRoot * 10f

            candidates.add(Pair(ShiShen.SHISHEN_ZHENG_GUAN, guanShaScore))
            candidates.add(Pair(ShiShen.SHISHEN_QI_SHA, guanShaScore - 3f))

            // 食伤（泄日主）
            val shiShangWx = getBeiShengWuXing(dayMasterWx)
            val shiShangWeight = weights[shiShangWx] ?: 0f
            val shiShangRoot = getRootScore(data, shiShangWx)
            val shiShangScore = 60f + shiShangWeight * 5f + shiShangRoot * 10f

            candidates.add(Pair(ShiShen.SHISHEN_SHI_SHEN, shiShangScore))
            candidates.add(Pair(ShiShen.SHISHEN_SHANG_GUAN, shiShangScore - 3f))

            // 财星（耗日主）
            val caiWx = getBeiKeWuXing(dayMasterWx)
            val caiWeight = weights[caiWx] ?: 0f
            val caiRoot = getRootScore(data, caiWx)
            val caiScore = 50f + caiWeight * 5f + caiRoot * 10f

            candidates.add(Pair(ShiShen.SHISHEN_ZHENG_CAI, caiScore))
            candidates.add(Pair(ShiShen.SHISHEN_PIAN_CAI, caiScore - 3f))
        }

        if (isWeak) {
            // 身弱：印星 > 比劫
            // 印星（生日主）
            val yinWx = getShengWuXing(dayMasterWx)
            val yinWeight = weights[yinWx] ?: 0f
            val yinRoot = getRootScore(data, yinWx)
            val yinScore = 70f + yinWeight * 5f + yinRoot * 10f

            candidates.add(Pair(ShiShen.SHISHEN_ZHENG_YIN, yinScore))
            candidates.add(Pair(ShiShen.SHISHEN_PIAN_YIN, yinScore - 3f))

            // 比劫（同日主）
            val biJieWeight = weights[dayMasterWx] ?: 0f
            val biJieRoot = getRootScore(data, dayMasterWx)
            val biJieScore = 60f + biJieWeight * 5f + biJieRoot * 10f

            candidates.add(Pair(ShiShen.SHISHEN_BI_JIAN, biJieScore))
            candidates.add(Pair(ShiShen.SHISHEN_JIE_CAI, biJieScore - 3f))
        }

        return candidates
    }

    /**
     * 计算喜神（生助核心用神的五行）
     */
    private fun calculateXiShen(primaryYongShen: ShiShen, dayMaster: TianGan): List<ShiShen> {
        val xiShenList = mutableListOf<ShiShen>()
        val yongShenWx = shiShenToWuXing(primaryYongShen)

        // 生助用神的五行 = 喜神
        val shengYongWx = getShengWuXing(yongShenWx)
        val xiShens = wuXingToShiShenList(shengYongWx, dayMaster)

        // 取前两个作为喜神
        xiShenList.addAll(xiShens.take(2))

        return xiShenList
    }

    /**
     * 计算忌神
     */
    private fun calculateJiShen(
        data: BaziData,
        primaryYongShen: ShiShen,
        dayMasterWx: WuXing,
        weights: Map<WuXing, Float>
    ): List<ShiShen> {
        val jiShenList = mutableListOf<ShiShen>()
        val yongShenWx = shiShenToWuXing(primaryYongShen)
        val dayMaster =  data.dayTiangan

        // 忌神 = 克用神的五行 + 用神克的五行
        val keYongWx = getKeWuXing(yongShenWx)
        val beiKeYongWx = getBeiKeWuXing(yongShenWx)

        jiShenList.addAll(wuXingToShiShenList(keYongWx, dayMaster))
        jiShenList.addAll(wuXingToShiShenList(beiKeYongWx, dayMaster))

        return jiShenList.distinct()
    }

    /**
     * 处理调候/通关用神与忌神的冲突
     * 如果忌神同时也是调候或通关用神，根据实际情况决定是否移除
     */
    private fun handleConflictWithTiaoHouTongGuan(
        data: BaziData,
        jiShenList: List<ShiShen>,
        primaryYongShen: ShiShen,
        weights: Map<WuXing, Float>,
        dayMasterWx: WuXing
    ): List<ShiShen> {
        val adjustedJiShen = jiShenList.toMutableList()
        val dayMaster = data.dayTiangan

        // 获取调候和通关用神
        val tiaoHouTongGuanList = mutableListOf<ShiShen>()

        // 检查调候用神
        val month = data.birthDateMonth
        val isExtremeCold = month in 10..12 || month == 1
        val isExtremeHot = month in 4..6

        if (isExtremeCold) {
            val huoShiShens = wuXingToShiShenList(WuXing.WUXING_HUO, dayMaster)
            tiaoHouTongGuanList.addAll(huoShiShens)
        }
        if (isExtremeHot) {
            val shuiShiShens = wuXingToShiShenList(WuXing.WUXING_SHUI, dayMaster)
            tiaoHouTongGuanList.addAll(shuiShiShens)
        }

        // 检查通关用神
        val conflicts = findMajorConflicts(data, weights)
        for ((keWx, beiKeWx) in conflicts) {
            val tongGuanWx = getTongGuanWuXing(keWx, beiKeWx)
            if (tongGuanWx != null) {
                tiaoHouTongGuanList.addAll(wuXingToShiShenList(tongGuanWx, dayMaster))
            }
        }

        // 对于调候/通关用神中的忌神，根据规则处理：
        // 规则1：如果调候用神与忌神冲突，保留调候用神，移除对应的忌神
        // 规则2：如果通关用神与忌神冲突，保留通关用神，移除对应的忌神
        for (tt in tiaoHouTongGuanList) {
            if (adjustedJiShen.contains(tt)) {
                // 检查这个调候/通关用神是否真的必要
                val ttWx = shiShenToWuXing(tt)
                val ttWeight = weights[ttWx] ?: 0f

                // 如果调候/通关用神的权重足够高，说明它在命局中很重要
                // 此时将其从忌神中移除
                if (ttWeight >= MEDIUM_THRESHOLD) {
                    adjustedJiShen.remove(tt)
                }
            }
        }

        return adjustedJiShen.distinct()
    }

    /**
     * 检查是否存在明显的五行相克冲突
     */
    private fun findMajorConflicts(
        data: BaziData,
        weights: Map<WuXing, Float>
    ): List<Pair<WuXing, WuXing>> {
        val conflicts = mutableListOf<Pair<WuXing, WuXing>>()

        // 检查四柱天干中是否存在明显相克
        val tianGanUtil = TianGanUtil()
        val gans = listOf(data.yearTiangan, data.monthTiangan, data.dayTiangan, data.hourTiangan)

        for (i in gans.indices) {
            for (j in i + 1 until gans.size) {
                if (tianGanUtil.isTianGanKe(gans[i], gans[j])) {
                    val keWx = getTianGanWuxing(gans[i])
                    val beiKeWx = getTianGanWuxing(gans[j])

                    // 只有当相克双方的权重都超过阈值时才视为严重冲突
                    val keWeight = weights[keWx] ?: 0f
                    val beiKeWeight = weights[beiKeWx] ?: 0f

                    if (keWeight >= MEDIUM_THRESHOLD && beiKeWeight >= MEDIUM_THRESHOLD) {
                        conflicts.add(Pair(keWx, beiKeWx))
                    }
                }
            }
        }

        return conflicts.distinct()
    }

    /**
     * 获取五行在地支的根气评分
     */
    private fun getRootScore(data: BaziData, wx: WuXing): Float {
        // 使用已有的 RootCounter 计算
        val dizhis = listOf(data.yearDizhi, data.monthDizhi, data.dayDizhi, data.hourDizhi)
        var score = 0f

        for (dz in dizhis) {
            val cangGans = DiZhiUtil().getCanggan(dz)
            for (cg in cangGans) {
                val cgWx = getTianGanWuxing(cg)
                if (cgWx == wx) {
                    when {
                        cg == cangGans[0] -> score += 1.0f  // 本气根
                        cg == cangGans[1] -> score += 0.5f  // 中气根
                        else -> score += 0.3f  // 余气根
                    }
                }
            }
        }

        return score
    }

    /**
     * 获取通关五行
     */
    private fun getTongGuanWuXing(ke: WuXing, beiKe: WuXing): WuXing? {
        return when {
            ke == WuXing.WUXING_MU && beiKe == WuXing.WUXING_TU -> WuXing.WUXING_HUO
            ke == WuXing.WUXING_HUO && beiKe == WuXing.WUXING_JIN -> WuXing.WUXING_TU
            ke == WuXing.WUXING_TU && beiKe == WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
            ke == WuXing.WUXING_JIN && beiKe == WuXing.WUXING_MU -> WuXing.WUXING_SHUI
            ke == WuXing.WUXING_SHUI && beiKe == WuXing.WUXING_HUO -> WuXing.WUXING_MU
            else -> null
        }
    }

    /**
     * 五行 → 十神列表（根据日主天干）
     */
    private fun wuXingToShiShenList(wx: WuXing, dayMaster: TianGan): List<ShiShen> {
        val dayMasterWx = getTianGanWuxing(dayMaster)
        val dayMasterIndex = dayMaster.ordinal
        val dayMasterIsYang = dayMasterIndex % 2 == 0

        return when {
            wx == dayMasterWx -> {
                if (dayMasterIsYang) listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                else listOf(ShiShen.SHISHEN_JIE_CAI, ShiShen.SHISHEN_BI_JIAN)
            }
            isSheng(dayMasterWx, wx) -> {
                if (dayMasterIsYang) listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                else listOf(ShiShen.SHISHEN_SHANG_GUAN, ShiShen.SHISHEN_SHI_SHEN)
            }
            isKe(dayMasterWx, wx) -> {
                if (dayMasterIsYang) listOf(ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_CAI)
                else listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
            isKe(wx, dayMasterWx) -> {
                if (dayMasterIsYang) listOf(ShiShen.SHISHEN_QI_SHA, ShiShen.SHISHEN_ZHENG_GUAN)
                else listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }
            isSheng(wx, dayMasterWx) -> {
                if (dayMasterIsYang) listOf(ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_ZHENG_YIN)
                else listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
            }
            else -> emptyList()
        }
    }

    // ========== 五行生克关系函数 ==========

    private fun isSheng(sheng: WuXing, beiSheng: WuXing): Boolean = when (sheng) {
        WuXing.WUXING_MU -> beiSheng == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> beiSheng == WuXing.WUXING_TU
        WuXing.WUXING_TU -> beiSheng == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> beiSheng == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> beiSheng == WuXing.WUXING_MU
    }

    private fun isKe(ke: WuXing, beiKe: WuXing): Boolean = when (ke) {
        WuXing.WUXING_MU -> beiKe == WuXing.WUXING_TU
        WuXing.WUXING_TU -> beiKe == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> beiKe == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> beiKe == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> beiKe == WuXing.WUXING_MU
    }

    private fun getShengWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_SHUI
        WuXing.WUXING_HUO -> WuXing.WUXING_MU
        WuXing.WUXING_TU -> WuXing.WUXING_HUO
        WuXing.WUXING_JIN -> WuXing.WUXING_TU
        WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
    }

    private fun getKeWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_JIN
        WuXing.WUXING_HUO -> WuXing.WUXING_SHUI
        WuXing.WUXING_TU -> WuXing.WUXING_MU
        WuXing.WUXING_JIN -> WuXing.WUXING_HUO
        WuXing.WUXING_SHUI -> WuXing.WUXING_TU
    }

    private fun getBeiShengWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> WuXing.WUXING_TU
        WuXing.WUXING_TU -> WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> WuXing.WUXING_MU
    }

    private fun getBeiKeWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_TU
        WuXing.WUXING_HUO -> WuXing.WUXING_JIN
        WuXing.WUXING_TU -> WuXing.WUXING_SHUI
        WuXing.WUXING_JIN -> WuXing.WUXING_MU
        WuXing.WUXING_SHUI -> WuXing.WUXING_HUO
    }

    private fun shiShenToWuXing(shiShen: ShiShen): WuXing = when (shiShen) {
        ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI -> WuXing.WUXING_MU
        ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN -> WuXing.WUXING_SHUI
        ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN -> WuXing.WUXING_HUO
        ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI -> WuXing.WUXING_TU
        ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA -> WuXing.WUXING_JIN
    }
}
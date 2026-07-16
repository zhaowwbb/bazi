package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.model.*
import com.rick.bazi.util.WuXingExt.isSheng
import com.rick.bazi.util.WuXingExt.isKe
import com.rick.bazi.util.WuXingExt.getWuXingText
import com.rick.bazi.util.WuXingExt.getWeight
import com.rick.bazi.util.BaziFormatter.convertTianganToChar
import com.rick.bazi.util.BaziFormatter.convertDizhiToChar

/**
 * 大运评分系统
 */
object DaYunScoringSystem {

    /**
     * 生成8个连续大运的评分列表
     */
    fun generateDaYunList(data: BaziData): List<DaYun> {
        val list = mutableListOf<DaYun>()

        val firstYear = data.daYunFirstYear
        val startAge = data.daYunStartYear

        for (i in 0 until 8) {
            val yearStart = firstYear + i * 10
            val yearEnd = yearStart + 9
            val ageStart = startAge + i * 10

            // 获取大运干支
            val tgdz = getDaYun(i + 1, data)
            val ganZhiStr = "${convertTianganToChar(tgdz.tg)}${convertDizhiToChar(tgdz.dz)}"

            // 计算综合评分
            val overallScore = calcDaYunOverallScore(data, tgdz.tg, tgdz.dz)

            // 构建5维明细评分
            val details = buildScoreDetails(data, tgdz, overallScore)

            list.add(
                DaYun(
                    yearRange = "$yearStart-$yearEnd",
                    startAge = ageStart,
                    ganZhi = ganZhiStr,
                    overallScore = overallScore,
                    details = details
                )
            )
        }

        return list
    }

    /**
     * 计算大运综合评分（核心算法）
     */
    private fun calcDaYunOverallScore(
        data: BaziData,
        daYunTg: TianGan,
        daYunDz: DiZhi
    ): Float {
        var score = 55f // 基础分

        val tgWx = WuXingWeightCalculator.getTianGanWuxing(daYunTg)
        val dzWx = WuXingWeightCalculator.getDiZhiMainElement(daYunDz)

        // 用神/忌神集合
        val yongSet = data.yongShenList.toSet()
        val jiSet = data.jiShenList.toSet()
        val xiYongSet = data.xiyongShenSet
        val tongGuanSet = data.tongguanShenList.toSet()

        // === 1. 天干评分（权重40%）===
        val tgScore = calcGanScore(data, daYunTg, tgWx, yongSet, jiSet, xiYongSet, tongGuanSet)
        score += tgScore * 0.4f

        // === 2. 地支评分（权重60%）===
        val dzScore = calcZhiScore(data, daYunDz, dzWx, yongSet, jiSet, xiYongSet, tongGuanSet)
        score += dzScore * 0.6f

        // === 3. 干支关系修正 ===
        score += calcGanZhiRelationAdjustment(tgWx, dzWx, yongSet, jiSet)

        // === 4. 日主强弱修正 ===
        score += calcDayMasterAdjustment(data, tgWx, dzWx)

        // === 5. 五行平衡修正 ===
        score += calcBalanceAdjustment(data, tgWx, dzWx)

        return score.coerceIn(0f, 100f)
    }

    /**
     * 天干评分
     */
    private fun calcGanScore(
        data: BaziData,
        gan: TianGan,
        wx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        xiYongSet: Set<ShiShen>,
        tongGuanSet: Set<ShiShen>
    ): Float {
        var score = 0f

        // 用神天干：+25~35分
        if (yongSet.contains(wxToShiShen(wx))) {
            score += 30f
            // 天干得月令加成
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) score += 5f
        }
        // 喜神天干：+10~15分
        else if (xiYongSet.contains(wxToShiShen(wx))) {
            score += 12f
        }
        // 忌神天干：-20~-30分
        else if (jiSet.contains(wxToShiShen(wx))) {
            score -= 25f
            // 忌神得月令加重
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) score -= 5f
        }
        // 闲神：轻微负面影响
        else {
            score -= 3f
        }

        // 通关用神修正
        if (tongGuanSet.contains(wxToShiShen(wx))) {
            score += 8f
        }

        // 天干五合修正（大运天干与原局天干相合）
        val tianGanUtil = TianGanUtil()
        if (tianGanUtil.isTianGanHe(gan, data.yearTiangan) ||
            tianGanUtil.isTianGanHe(gan, data.monthTiangan) ||
            tianGanUtil.isTianGanHe(gan, data.dayTiangan) ||
            tianGanUtil.isTianGanHe(gan, data.hourTiangan)
        ) {
            score += 3f // 相合有缓和作用
        }

        // 天干相克修正
        if (tianGanUtil.isTianGanKe(gan, data.dayTiangan)) {
            score -= 5f // 克日主不利
        }
        if (tianGanUtil.isTianGanSheng(gan, data.dayTiangan)) {
            score += 5f // 生日主有利
        }

        return score
    }

    /**
     * 地支评分
     */
    private fun calcZhiScore(
        data: BaziData,
        zhi: DiZhi,
        wx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        xiYongSet: Set<ShiShen>,
        tongGuanSet: Set<ShiShen>
    ): Float {
        var score = 0f

        // 用神地支：+30~45分（地支力量大于天干）
        if (yongSet.contains(wxToShiShen(wx))) {
            score += 38f
            // 地支得月令加成
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) score += 7f
        }
        // 喜神地支：+12~18分
        else if (xiYongSet.contains(wxToShiShen(wx))) {
            score += 15f
        }
        // 忌神地支：-25~-40分
        else if (jiSet.contains(wxToShiShen(wx))) {
            score -= 32f
            // 忌神得月令加重
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) score -= 8f
        }
        // 闲神：轻微负面影响
        else {
            score -= 5f
        }

        // 通关用神修正
        if (tongGuanSet.contains(wxToShiShen(wx))) {
            score += 10f
        }

        // 地支刑冲合害修正
        val diZhiUtil = DiZhiUtil()

        // 地支相冲（大运地支冲日支或月支影响较大）
        if (diZhiUtil.isDiZhiChong(zhi, data.dayDizhi)) {
            score -= 8f // 冲日支，根基动摇
        }
        if (diZhiUtil.isDiZhiChong(zhi, data.monthDizhi)) {
            score -= 5f // 冲月支，环境变动
        }

        // 地支相合（大运地支与日支或月支相合有利）
        if (diZhiUtil.isDiZhiHai(zhi, data.dayDizhi) ||
            diZhiUtil.isDiZhiHai(zhi, data.monthDizhi)
        ) {
            score += 4f
        }

        // 地支相刑
        if (diZhiUtil.isDiZhiXiangXing(zhi, data.dayDizhi)) {
            score -= 3f
        }

        // 地支藏干分析（看藏干中是否有用神）
        val cangGans = diZhiUtil.getCanggan(zhi)
        for (cg in cangGans) {
            val cgWx = WuXingWeightCalculator.getTianGanWuxing(cg)
            if (yongSet.contains(wxToShiShen(cgWx))) {
                score += 3f // 藏干中有用神，加分
            }
        }

        return score
    }

    /**
     * 干支关系修正
     */
    private fun calcGanZhiRelationAdjustment(
        tgWx: WuXing,
        dzWx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>
    ): Float {
        var adjustment = 0f

        val tgIsYong = yongSet.contains(wxToShiShen(tgWx))
        val dzIsYong = yongSet.contains(wxToShiShen(dzWx))
        val tgIsJi = jiSet.contains(wxToShiShen(tgWx))
        val dzIsJi = jiSet.contains(wxToShiShen(dzWx))

        when {
            // 干支同为用神：强力加分
            tgIsYong && dzIsYong -> adjustment += 10f
            // 干支同为忌神：强力减分
            tgIsJi && dzIsJi -> adjustment -= 12f
            // 天干用神、地支忌神：矛盾，略有负面影响
            tgIsYong && dzIsJi -> adjustment -= 5f
            // 天干忌神、地支用神：矛盾，略有负面影响
            tgIsJi && dzIsYong -> adjustment -= 5f
            // 天干用神、地支喜神：较好
            tgIsYong && !dzIsJi -> adjustment += 3f
            // 天干喜神、地支用神：较好
            !tgIsJi && dzIsYong -> adjustment += 3f
        }

        // 干支五行关系：相生加分，相克减分
        if (isSheng(tgWx, dzWx)) {
            adjustment += 3f // 天干生地支，力量顺畅
        } else if (isSheng(dzWx, tgWx)) {
            adjustment += 2f // 地支生天干，根基稳固
        } else if (isKe(tgWx, dzWx)) {
            adjustment -= 2f // 天干克地支，外强中干
        } else if (isKe(dzWx, tgWx)) {
            adjustment -= 3f // 地支克天干，根基受损
        }

        return adjustment
    }

    /**
     * 日主强弱修正
     */
    private fun calcDayMasterAdjustment(
        data: BaziData,
        tgWx: WuXing,
        dzWx: WuXing
    ): Float {
        var adjustment = 0f

        val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(data.dayTiangan)
        val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(data)

        // 日主过强时，喜克泄耗
        if (dayMasterStrength.strengthLevel == StrengthLevel.VERY_STRONG ||
            dayMasterStrength.strengthLevel == StrengthLevel.STRONG
        ) {
            // 克日主的五行加分
            if (isKe(tgWx, dayMasterWx) || isKe(dzWx, dayMasterWx)) {
                adjustment += 5f
            }
            // 生日主的五行减分
            if (isSheng(dayMasterWx, tgWx) || isSheng(dayMasterWx, dzWx)) {
                adjustment -= 3f
            }
        }

        // 日主过弱时，喜生扶
        if (dayMasterStrength.strengthLevel == StrengthLevel.VERY_WEAK ||
            dayMasterStrength.strengthLevel == StrengthLevel.WEAK
        ) {
            // 生日主的五行加分
            if (isSheng(dayMasterWx, tgWx) || isSheng(dayMasterWx, dzWx)) {
                adjustment += 5f
            }
            // 克日主的五行减分
            if (isKe(tgWx, dayMasterWx) || isKe(dzWx, dayMasterWx)) {
                adjustment -= 3f
            }
        }

        return adjustment
    }

    /**
     * 五行平衡修正
     */
    private fun calcBalanceAdjustment(
        data: BaziData,
        tgWx: WuXing,
        dzWx: WuXing
    ): Float {
        var adjustment = 0f

        // 获取五行权重
        val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(data)
        val analyzer = WuXingBalanceAnalyzer()
        val balance = analyzer.analyzeBalance(weights)

        // 补充过弱的五行加分
        for (deficient in balance.deficientElements) {
            if (tgWx == deficient || dzWx == deficient) {
                adjustment += 6f // 补充弱势五行，平衡命局
            }
        }

        // 加剧过强的五行减分
        for (excess in balance.excessElements) {
            if (tgWx == excess || dzWx == excess) {
                adjustment -= 6f // 加剧强势五行，破坏平衡
            }
        }

        return adjustment
    }

    /**
     * 构建5维评分明细
     */
    private fun buildScoreDetails(
        data: BaziData,
        tgdz: TianGanDiZhi,
        overallScore: Float
    ): List<ScoreDetail> {
        val tgWx = WuXingWeightCalculator.getTianGanWuxing(tgdz.tg)
        val dzWx = WuXingWeightCalculator.getDiZhiMainElement(tgdz.dz)

        val yongSet = data.yongShenList.toSet()
        val jiSet = data.jiShenList.toSet()
        val xiSet = data.xiyongShenSet

        fun explain(dim: ScoreDimension): String {
            val sb = StringBuilder()
            sb.append("大运天干${getWuXingText(tgWx)}，地支${getWuXingText(dzWx)}；")

            when {
                yongSet.contains(wxToShiShen(tgWx)) || yongSet.contains(wxToShiShen(dzWx)) ->
                    sb.append("属于用神范畴，增强命局平衡，利于${dim.displayName}方面发展")

                jiSet.contains(wxToShiShen(tgWx)) || jiSet.contains(wxToShiShen(dzWx)) ->
                    sb.append("属于忌神范畴，加重五行失衡，需注意${dim.displayName}方面压力")

                xiSet.contains(wxToShiShen(tgWx)) || xiSet.contains(wxToShiShen(dzWx)) ->
                    sb.append("属于喜神范畴，间接生助用神，对${dim.displayName}有温和助益")

                else ->
                    sb.append("为闲神，对${dim.displayName}影响较中性")
            }

            return sb.toString()
        }

//        fun dimScore(dim: ScoreDimension): Float {
//            var score = overallScore
//            // 各维度微调
//            when (dim) {
//                ScoreDimension.HEALTH -> {
//                    // 木火通明利健康，金水相生也不错
//                    if (tgWx == WuXing.WUXING_MU || tgWx == WuXing.WUXING_HUO) score += 3f
//                    if (jiSet.contains(wxToShiShen(WuXing.WUXING_JIN))) score -= 3f
//                }
//                ScoreDimension.WEALTH -> {
//                    // 财星旺利财富
//                    if (tgWx == WuXing.WUXING_TU || tgWx == WuXing.WUXING_JIN) score += 3f
//                    if (yongSet.contains(wxToShiShen(tgWx))) score += 4f
//                }
//                ScoreDimension.CAREER -> {
//                    // 官杀旺利事业
//                    if (tgWx == WuXing.WUXING_HUO || tgWx == WuXing.WUXING_TU) score += 3f
//                    if (yongSet.contains(wxToShiShen(tgWx))) score += 4f
//                }
//                ScoreDimension.FAMILY -> {
//                    // 地支影响家庭
//                    if (jiSet.contains(wxToShiShen(dzWx))) score -= 4f
//                    val diZhiUtil = DiZhiUtil()
//                    if (diZhiUtil.isDiZhiChong(tgdz.dz, data.dayDizhi)) score -= 5f
//                    if (diZhiUtil.isDiZhiHai(tgdz.dz, data.dayDizhi)) score += 3f
//                }
//                ScoreDimension.LOVE -> {
//                    // 桃花星影响感情
//                    if (tgdz.dz == DiZhi.DIZHI_ZI || tgdz.dz == DiZhi.DIZHI_WU ||
//                        tgdz.dz == DiZhi.DIZHI_MOU || tgdz.dz == DiZhi.DIZHI_YOU) {
//                        score += 2f // 桃花运
//                    }
//                    if (data.tongguanShenList.isNotEmpty()) score += 2f
//                }
//            }
//            return score.coerceIn(0f, 100f)
//        }

        return ScoreDimension.values().map { dim ->
            ScoreDetail(
                dimension = dim,
//                score = dimScore(dim),
                score = overallScore,
                logicExplanation = explain(dim)
            )
        }
    }

    /**
     * WuXing → ShiShen 映射（简化版，实际应根据日主计算）
     */
    private fun wxToShiShen(wx: WuXing): ShiShen {
        return when (wx) {
            WuXing.WUXING_JIN -> ShiShen.SHISHEN_ZHENG_GUAN
            WuXing.WUXING_MU -> ShiShen.SHISHEN_BI_JIAN
            WuXing.WUXING_SHUI -> ShiShen.SHISHEN_ZHENG_YIN
            WuXing.WUXING_HUO -> ShiShen.SHISHEN_SHANG_GUAN
            WuXing.WUXING_TU -> ShiShen.SHISHEN_ZHENG_CAI
        }
    }

    /**
     * 大运干支获取（复用你已有的 getDaYun 函数）
     */
    private fun getDaYun(index: Int, data: BaziData): TianGanDiZhi {
        // 这里调用你已有的 getDaYun 函数
        return DaYunUtil().getDaYun(index, data)
    }

}
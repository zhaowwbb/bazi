package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.model.*
import com.rick.bazi.util.WuXingExt.isSheng
import com.rick.bazi.util.WuXingExt.isKe
import com.rick.bazi.util.WuXingExt.getWuXingText
import com.rick.bazi.util.WuXingExt.getWeight
import com.rick.bazi.util.BaziFormatter.convertTianganToChar
import com.rick.bazi.util.BaziFormatter.convertDizhiToChar
import com.rick.bazi.util.BaziFormatter.getDiZhiMainElement
import com.rick.bazi.util.BaziFormatter.getDiZhiSeasonElement
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing

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

            // 计算综合评分（带日志）
            val logMessages = mutableListOf<String>()
            val overallScore = calcDaYunOverallScore(data, tgdz.tg, tgdz.dz, logMessages)

            // 构建5维明细评分
            val details = buildScoreDetails(data, tgdz, overallScore, logMessages)

            list.add(
                DaYun(
                    yearRange = "$yearStart-$yearEnd",
                    startAge = ageStart,
                    ganZhi = ganZhiStr,
                    overallScore = overallScore,
                    details = details,
                    logMessages = logMessages  // 保存日志信息
                )
            )
        }

        return list
    }

    /**
     * 计算大运综合评分（核心算法）- 带日志记录
     *
     * @param data BaziData 八字数据
     * @param daYunTg 大运天干
     * @param daYunDz 大运地支
     * @param logMessages 日志列表（用于记录每一步计算逻辑）
     * @return Float 综合评分（0-100）
     */
    private fun calcDaYunOverallScore(
        data: BaziData,
        daYunTg: TianGan,
        daYunDz: DiZhi,
        logMessages: MutableList<String>
    ): Float {
        var score = 55f // 基础分
        logMessages.add("【初始】基础分设为55分（中等水平）")

        val tgWx = getTianGanWuxing(daYunTg)
        val dzWx = getDiZhiMainElement(daYunDz)

        // 用神/忌神集合
        val yongSet = data.yongShenList.toSet()
        val jiSet = data.jiShenList.toSet()
        val xiYongSet = data.xiyongShenSet
        val tongGuanSet = data.tongguanShenList.toSet()

        // === 1. 天干评分（权重40%）===
        logMessages.add("【天干评分】开始计算天干${convertTianganToChar(daYunTg)}（${getWuXingText(tgWx)}）的影响（权重40%）")
        val tgScore = calcGanScore(data, daYunTg, tgWx, yongSet, jiSet, xiYongSet, tongGuanSet, logMessages)
        val tgContribution = tgScore * 0.4f
        score += tgContribution
        logMessages.add("【天干评分】天干得分=$tgScore，乘以权重40%后贡献=$tgContribution，当前总分=$score")

        // === 2. 地支评分（权重60%）===
        logMessages.add("【地支评分】开始计算地支${convertDizhiToChar(daYunDz)}（${getWuXingText(dzWx)}）的影响（权重60%）")
        val dzScore = calcZhiScore(data, daYunDz, dzWx, yongSet, jiSet, xiYongSet, tongGuanSet, logMessages)
        val dzContribution = dzScore * 0.6f
        score += dzContribution
        logMessages.add("【地支评分】地支得分=$dzScore，乘以权重60%后贡献=$dzContribution，当前总分=$score")

        // === 3. 干支关系修正 ===
        logMessages.add("【干支关系】开始分析天干${getWuXingText(tgWx)}与地支${getWuXingText(dzWx)}的关系")
        val relationAdj = calcGanZhiRelationAdjustment(tgWx, dzWx, yongSet, jiSet, logMessages)
        score += relationAdj
        logMessages.add("【干支关系】干支关系修正=$relationAdj，当前总分=$score")

        // === 4. 日主强弱修正 ===
        logMessages.add("【日主强弱】开始分析大运对日主${convertTianganToChar(data.dayTiangan)}强弱的影响")
        val dayMasterAdj = calcDayMasterAdjustment(data, tgWx, dzWx, logMessages)
        score += dayMasterAdj
        logMessages.add("【日主强弱】日主强弱修正=$dayMasterAdj，当前总分=$score")

        // === 5. 五行平衡修正 ===
        logMessages.add("【五行平衡】开始分析大运对五行平衡的影响")
        val balanceAdj = calcBalanceAdjustment(data, tgWx, dzWx, logMessages)
        score += balanceAdj
        logMessages.add("【五行平衡】五行平衡修正=$balanceAdj，当前总分=$score")

        // 最终分数限制在0-100之间
        val finalScore = score.coerceIn(0f, 100f)
        logMessages.add("【最终结果】综合评分=$finalScore（范围限定0-100）")

        return finalScore
    }

    /**
     * 天干评分 - 带日志
     */
    private fun calcGanScore(
        data: BaziData,
        gan: TianGan,
        wx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        xiYongSet: Set<ShiShen>,
        tongGuanSet: Set<ShiShen>,
        logMessages: MutableList<String>
    ): Float {
        var score = 0f
        val shiShen = wxToShiShen(wx)

        // 用神天干：+25~35分
        if (yongSet.contains(shiShen)) {
            score += 30f
            logMessages.add("  【天干】${convertTianganToChar(gan)}（${getWuXingText(wx)}）是用神，加30分")

            // 天干得月令加成
            val monthWx = getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score += 5f
                logMessages.add("  【天干】${convertTianganToChar(gan)}得月令${getWuXingText(monthWx)}加成，再加5分")
            }
        }
        // 喜神天干：+10~15分
        else if (xiYongSet.contains(shiShen)) {
            score += 12f
            logMessages.add("  【天干】${convertTianganToChar(gan)}（${getWuXingText(wx)}）是喜神，加12分")
        }
        // 忌神天干：-20~-30分
        else if (jiSet.contains(shiShen)) {
            score -= 25f
            logMessages.add("  【天干】${convertTianganToChar(gan)}（${getWuXingText(wx)}）是忌神，减25分")

            // 忌神得月令加重
            val monthWx = getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score -= 5f
                logMessages.add("  【天干】${convertTianganToChar(gan)}得月令${getWuXingText(monthWx)}加重，再减5分")
            }
        }
        // 闲神：轻微负面影响
        else {
            score -= 3f
            logMessages.add("  【天干】${convertTianganToChar(gan)}（${getWuXingText(wx)}）是闲神，减3分")
        }

        // 通关用神修正
        if (tongGuanSet.contains(shiShen)) {
            score += 8f
            logMessages.add("  【天干】${convertTianganToChar(gan)}是通关用神，加8分")
        }

        // 天干五合修正（大运天干与原局天干相合）
        val tianGanUtil = TianGanUtil()
        val heTargets = listOf(data.yearTiangan, data.monthTiangan, data.dayTiangan, data.hourTiangan)
        for ((index, target) in heTargets.withIndex()) {
            if (tianGanUtil.isTianGanHe(gan, target)) {
                score += 3f
                val pillarNames = listOf("年", "月", "日", "时")
                logMessages.add("  【天干】${convertTianganToChar(gan)}与${pillarNames[index]}干${convertTianganToChar(target)}相合，加3分")
            }
        }

        // 天干相克修正
        if (tianGanUtil.isTianGanKe(gan, data.dayTiangan)) {
            score -= 5f
            logMessages.add("  【天干】${convertTianganToChar(gan)}克日主${convertTianganToChar(data.dayTiangan)}，减5分")
        }
        if (tianGanUtil.isTianGanSheng(gan, data.dayTiangan)) {
            score += 5f
            logMessages.add("  【天干】${convertTianganToChar(gan)}生日主${convertTianganToChar(data.dayTiangan)}，加5分")
        }

        logMessages.add("  【天干】天干${convertTianganToChar(gan)}最终得分=$score")
        return score
    }

    /**
     * 地支评分 - 带日志（含藏干分析）
     */
    private fun calcZhiScore(
        data: BaziData,
        zhi: DiZhi,
        wx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        xiYongSet: Set<ShiShen>,
        tongGuanSet: Set<ShiShen>,
        logMessages: MutableList<String>
    ): Float {
        var score = 0f
        val shiShen = wxToShiShen(wx)

        // 用神地支：+30~45分（地支力量大于天干）
        if (yongSet.contains(shiShen)) {
            score += 38f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}（${getWuXingText(wx)}）是用神，加38分")

            // 地支得月令加成
            val monthWx = getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score += 7f
                logMessages.add("  【地支】${convertDizhiToChar(zhi)}得月令${getWuXingText(monthWx)}加成，再加7分")
            }
        }
        // 喜神地支：+12~18分
        else if (xiYongSet.contains(shiShen)) {
            score += 15f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}（${getWuXingText(wx)}）是喜神，加15分")
        }
        // 忌神地支：-25~-40分
        else if (jiSet.contains(shiShen)) {
            score -= 32f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}（${getWuXingText(wx)}）是忌神，减32分")

            // 忌神得月令加重
            val monthWx = getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score -= 8f
                logMessages.add("  【地支】${convertDizhiToChar(zhi)}得月令${getWuXingText(monthWx)}加重，再减8分")
            }
        }
        // 闲神：轻微负面影响
        else {
            score -= 5f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}（${getWuXingText(wx)}）是闲神，减5分")
        }

        // 通关用神修正
        if (tongGuanSet.contains(shiShen)) {
            score += 10f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}是通关用神，加10分")
        }

        // 地支刑冲合害修正
        val diZhiUtil = DiZhiUtil()

        // 地支相冲（大运地支冲日支或月支影响较大）
        if (diZhiUtil.isDiZhiChong(zhi, data.dayDizhi)) {
            score -= 8f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}冲日支${convertDizhiToChar(data.dayDizhi)}，根基动摇，减8分")
        }
        if (diZhiUtil.isDiZhiChong(zhi, data.monthDizhi)) {
            score -= 5f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}冲月支${convertDizhiToChar(data.monthDizhi)}，环境变动，减5分")
        }

        // 地支相合（大运地支与日支或月支相合有利）
        if (diZhiUtil.isDiZhiHai(zhi, data.dayDizhi)) {
            score += 4f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}与日支${convertDizhiToChar(data.dayDizhi)}相合，加4分")
        }
        if (diZhiUtil.isDiZhiHai(zhi, data.monthDizhi)) {
            score += 4f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}与月支${convertDizhiToChar(data.monthDizhi)}相合，加4分")
        }

        // 地支相刑
        if (diZhiUtil.isDiZhiXiangXing(zhi, data.dayDizhi)) {
            score -= 3f
            logMessages.add("  【地支】${convertDizhiToChar(zhi)}与日支${convertDizhiToChar(data.dayDizhi)}相刑，减3分")
        }

        // 地支藏干分析（看藏干中是否有用神/忌神）
        logMessages.add("  【地支藏干】分析${convertDizhiToChar(zhi)}的藏干")
        val cangGans = diZhiUtil.getCanggan(zhi)
        for ((index, cg) in cangGans.withIndex()) {
            val cgWx = getTianGanWuxing(cg)
            val cgShiShen = wxToShiShen(cgWx)
            val layerNames = listOf("本气", "中气", "余气")
            val layerName = if (index < layerNames.size) layerNames[index] else "藏干${index + 1}"

            if (yongSet.contains(cgShiShen)) {
                score += 3f
                logMessages.add("    【藏干】${layerName}${convertTianganToChar(cg)}（${getWuXingText(cgWx)}）是用神，加3分")
            } else if (jiSet.contains(cgShiShen)) {
                score -= 3f
                logMessages.add("    【藏干】${layerName}${convertTianganToChar(cg)}（${getWuXingText(cgWx)}）是忌神，减3分")
            } else if (xiYongSet.contains(cgShiShen)) {
                score += 1.5f
                logMessages.add("    【藏干】${layerName}${convertTianganToChar(cg)}（${getWuXingText(cgWx)}）是喜神，加1.5分")
            } else if (tongGuanSet.contains(cgShiShen)) {
                score += 2f
                logMessages.add("    【藏干】${layerName}${convertTianganToChar(cg)}（${getWuXingText(cgWx)}）是通关用神，加2分")
            } else {
                logMessages.add("    【藏干】${layerName}${convertTianganToChar(cg)}（${getWuXingText(cgWx)}）是闲神，无影响")
            }
        }

        logMessages.add("  【地支】地支${convertDizhiToChar(zhi)}最终得分=$score")
        return score
    }

    /**
     * 干支关系修正 - 带日志
     */
    private fun calcGanZhiRelationAdjustment(
        tgWx: WuXing,
        dzWx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        logMessages: MutableList<String>
    ): Float {
        var adjustment = 0f

        val tgIsYong = yongSet.contains(wxToShiShen(tgWx))
        val dzIsYong = yongSet.contains(wxToShiShen(dzWx))
        val tgIsJi = jiSet.contains(wxToShiShen(tgWx))
        val dzIsJi = jiSet.contains(wxToShiShen(dzWx))

        when {
            // 干支同为用神：强力加分
            tgIsYong && dzIsYong -> {
                adjustment += 10f
                logMessages.add("  【干支关系】天干${getWuXingText(tgWx)}和地支${getWuXingText(dzWx)}同为用神，强力加分10分")
            }
            // 干支同为忌神：强力减分
            tgIsJi && dzIsJi -> {
                adjustment -= 12f
                logMessages.add("  【干支关系】天干${getWuXingText(tgWx)}和地支${getWuXingText(dzWx)}同为忌神，强力减分12分")
            }
            // 天干用神、地支忌神：矛盾，略有负面影响
            tgIsYong && dzIsJi -> {
                adjustment -= 5f
                logMessages.add("  【干支关系】天干用神${getWuXingText(tgWx)}被地支忌神${getWuXingText(dzWx)}压制，减5分")
            }
            // 天干忌神、地支用神：矛盾，略有负面影响
            tgIsJi && dzIsYong -> {
                adjustment -= 5f
                logMessages.add("  【干支关系】地支用神${getWuXingText(dzWx)}被天干忌神${getWuXingText(tgWx)}压制，减5分")
            }
            // 天干用神、地支喜神：较好
            tgIsYong && !dzIsJi -> {
                adjustment += 3f
                logMessages.add("  【干支关系】天干用神${getWuXingText(tgWx)}与地支非忌神，加3分")
            }
            // 天干喜神、地支用神：较好
            !tgIsJi && dzIsYong -> {
                adjustment += 3f
                logMessages.add("  【干支关系】地支用神${getWuXingText(dzWx)}与天干非忌神，加3分")
            }
            else -> {
                logMessages.add("  【干支关系】天干${getWuXingText(tgWx)}与地支${getWuXingText(dzWx)}无明显冲突，无修正")
            }
        }

        // 干支五行关系：相生加分，相克减分
        if (isSheng(tgWx, dzWx)) {
            adjustment += 3f
            logMessages.add("  【干支关系】天干${getWuXingText(tgWx)}生地支${getWuXingText(dzWx)}，力量顺畅，加3分")
        } else if (isSheng(dzWx, tgWx)) {
            adjustment += 2f
            logMessages.add("  【干支关系】地支${getWuXingText(dzWx)}生天干${getWuXingText(tgWx)}，根基稳固，加2分")
        } else if (isKe(tgWx, dzWx)) {
            adjustment -= 2f
            logMessages.add("  【干支关系】天干${getWuXingText(tgWx)}克地支${getWuXingText(dzWx)}，外强中干，减2分")
        } else if (isKe(dzWx, tgWx)) {
            adjustment -= 3f
            logMessages.add("  【干支关系】地支${getWuXingText(dzWx)}克天干${getWuXingText(tgWx)}，根基受损，减3分")
        } else {
            logMessages.add("  【干支关系】天干${getWuXingText(tgWx)}与地支${getWuXingText(dzWx)}五行相同，无修正")
        }

        logMessages.add("  【干支关系】干支关系修正总计=$adjustment")
        return adjustment
    }

    /**
     * 日主强弱修正 - 带日志
     */
    private fun calcDayMasterAdjustment(
        data: BaziData,
        tgWx: WuXing,
        dzWx: WuXing,
        logMessages: MutableList<String>
    ): Float {
        var adjustment = 0f

        val dayMasterWx = getTianGanWuxing(data.dayTiangan)
        val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(data)

        logMessages.add("  【日主强弱】日主${convertTianganToChar(data.dayTiangan)}（${getWuXingText(dayMasterWx)}）强度等级=${dayMasterStrength.strengthLevel.name}")

        // 日主过强时，喜克泄耗
        if (dayMasterStrength.strengthLevel == StrengthLevel.VERY_STRONG ||
            dayMasterStrength.strengthLevel == StrengthLevel.STRONG
        ) {
            // 克日主的五行加分
            if (isKe(tgWx, dayMasterWx)) {
                adjustment += 5f
                logMessages.add("  【日主强弱】日主偏强，天干${getWuXingText(tgWx)}克日主，加5分")
            }
            if (isKe(dzWx, dayMasterWx)) {
                adjustment += 5f
                logMessages.add("  【日主强弱】日主偏强，地支${getWuXingText(dzWx)}克日主，加5分")
            }
            // 生日主的五行减分
            if (isSheng(dayMasterWx, tgWx)) {
                adjustment -= 3f
                logMessages.add("  【日主强弱】日主偏强，天干${getWuXingText(tgWx)}生日主，减3分")
            }
            if (isSheng(dayMasterWx, dzWx)) {
                adjustment -= 3f
                logMessages.add("  【日主强弱】日主偏强，地支${getWuXingText(dzWx)}生日主，减3分")
            }
        }

        // 日主过弱时，喜生扶
        if (dayMasterStrength.strengthLevel == StrengthLevel.VERY_WEAK ||
            dayMasterStrength.strengthLevel == StrengthLevel.WEAK
        ) {
            // 生日主的五行加分
            if (isSheng(dayMasterWx, tgWx)) {
                adjustment += 5f
                logMessages.add("  【日主强弱】日主偏弱，天干${getWuXingText(tgWx)}生日主，加5分")
            }
            if (isSheng(dayMasterWx, dzWx)) {
                adjustment += 5f
                logMessages.add("  【日主强弱】日主偏弱，地支${getWuXingText(dzWx)}生日主，加5分")
            }
            // 克日主的五行减分
            if (isKe(tgWx, dayMasterWx)) {
                adjustment -= 3f
                logMessages.add("  【日主强弱】日主偏弱，天干${getWuXingText(tgWx)}克日主，减3分")
            }
            if (isKe(dzWx, dayMasterWx)) {
                adjustment -= 3f
                logMessages.add("  【日主强弱】日主偏弱，地支${getWuXingText(dzWx)}克日主，减3分")
            }
        }

        // 日主中和时，不做修正
        if (dayMasterStrength.strengthLevel == StrengthLevel.MEDIUM) {
            logMessages.add("  【日主强弱】日主中和，无需修正")
        }

        logMessages.add("  【日主强弱】日主强弱修正总计=$adjustment")
        return adjustment
    }

    /**
     * 五行平衡修正 - 带日志
     */
    private fun calcBalanceAdjustment(
        data: BaziData,
        tgWx: WuXing,
        dzWx: WuXing,
        logMessages: MutableList<String>
    ): Float {
        var adjustment = 0f

        // 获取五行权重
        val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(data)
        val analyzer = WuXingBalanceAnalyzer()
        val balance = analyzer.analyzeBalance(weights)

        // 补充过弱的五行加分
        for (deficient in balance.deficientElements) {
            if (tgWx == deficient) {
                adjustment += 6f
                logMessages.add("  【五行平衡】天干${getWuXingText(tgWx)}补充弱势五行，加6分")
            }
            if (dzWx == deficient) {
                adjustment += 6f
                logMessages.add("  【五行平衡】地支${getWuXingText(dzWx)}补充弱势五行，加6分")
            }
        }

        // 加剧过强的五行减分
        for (excess in balance.excessElements) {
            if (tgWx == excess) {
                adjustment -= 6f
                logMessages.add("  【五行平衡】天干${getWuXingText(tgWx)}加剧强势五行，减6分")
            }
            if (dzWx == excess) {
                adjustment -= 6f
                logMessages.add("  【五行平衡】地支${getWuXingText(dzWx)}加剧强势五行，减6分")
            }
        }

        if (adjustment == 0f) {
            logMessages.add("  【五行平衡】大运五行未影响弱势或强势五行，无修正")
        }

        logMessages.add("  【五行平衡】五行平衡修正总计=$adjustment")
        return adjustment
    }

    /**
     * 构建5维评分明细 - 带日志
     */
    private fun buildScoreDetails(
        data: BaziData,
        tgdz: TianGanDiZhi,
        overallScore: Float,
        logMessages: MutableList<String>
    ): List<ScoreDetail> {
        val tgWx = getTianGanWuxing(tgdz.tg)
        val dzWx = getDiZhiMainElement(tgdz.dz)

        val yongSet = data.yongShenList.toSet()
        val jiSet = data.jiShenList.toSet()
        val xiSet = data.xiyongShenSet

        fun explain(dim: ScoreDimension): String {
            val sb = StringBuilder()
            sb.append("大运天干${convertTianganToChar(tgdz.tg)}${getWuXingText(tgWx)}；")

            val tgShiShen = ShiShenUtil().getShiShen(tgdz.tg, data.dayTiangan)

            when {
                yongSet.contains(tgShiShen) ->
                    sb.append("属于用神范畴，增强命局平衡，利于${dim.displayName}方面发展")

                jiSet.contains(tgShiShen) ->
                    sb.append("属于忌神范畴，加重五行失衡，需注意${dim.displayName}方面压力")

                xiSet.contains(tgShiShen) ->
                    sb.append("属于喜神范畴，间接生助用神，对${dim.displayName}有温和助益")

                else ->
                    sb.append("为闲神，对${dim.displayName}影响较中性")
            }
            sb.append("\n")

            sb.append("大运地支${convertDizhiToChar(tgdz.dz)}${getWuXingText(dzWx)}；")
            when {
                yongSet.contains(wxToShiShen(dzWx)) ->
                    sb.append("属于用神范畴，增强命局平衡，利于${dim.displayName}方面发展")

                jiSet.contains(wxToShiShen(dzWx)) ->
                    sb.append("属于忌神范畴，加重五行失衡，需注意${dim.displayName}方面压力")

                xiSet.contains(wxToShiShen(dzWx)) ->
                    sb.append("属于喜神范畴，间接生助用神，对${dim.displayName}有温和助益")

                else ->
                    sb.append("为闲神，对${dim.displayName}影响较中性")
            }

            return sb.toString()
        }

        val result = logMessages.joinToString(separator = "\n")

        return ScoreDimension.values().map { dim ->
            ScoreDetail(
                dimension = dim,
                score = overallScore,
                logicExplanation = result
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
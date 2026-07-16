package com.rick.bazi.util


import com.rick.bazi.data.*
import com.rick.bazi.util.BaziFormatter.getGanZhiByYear

/**
 * 流年评分系统
 * 基于大运评分，叠加流年干支影响
 */
object LiuNianScoringSystem {

    /**
     * 计算流年综合评分
     * @param data BaziData 八字数据
     * @param liuNianTg 流年天干
     * @param liuNianDz 流年地支
     * @param daYunScore 大运基础评分
     * @param logicDescriptions 存放计算逻辑描述的字符串列表
     * @return 流年最终评分（0-100）
     */
    fun calcLiuNianScore(
        data: BaziData,
        liuNianTg: TianGan,
        liuNianDz: DiZhi,
        daYunScore: Float,
        logicDescriptions: MutableList<String>
    ): Float {
        var score = daYunScore
        logicDescriptions.add("━━━ 流年评分计算 ━━━")
        logicDescriptions.add("大运基础评分：${String.format("%.1f", daYunScore)}分")

        val tgWx = WuXingWeightCalculator.getTianGanWuxing(liuNianTg)
        val dzWx = WuXingWeightCalculator.getDiZhiMainElement(liuNianDz)

        val yongSet = data.yongShenList.toSet()
        val jiSet = data.jiShenList.toSet()
        val xiYongSet = data.xiyongShenSet
        val tongGuanSet = data.tongguanShenList.toSet()

        // === 1. 流年天干评分（权重30%）===
        val tgScore = calcLiuNianGanScore(
            data, liuNianTg, tgWx, yongSet, jiSet, xiYongSet, tongGuanSet, logicDescriptions
        )
        val tgContribution = tgScore * 0.3f
        score += tgContribution
        logicDescriptions.add("天干评分：${String.format("%+.1f", tgScore)}分 × 30% = ${String.format("%+.1f", tgContribution)}分")

        // === 2. 流年地支评分（权重50%）===
        val dzScore = calcLiuNianZhiScore(
            data, liuNianDz, dzWx, yongSet, jiSet, xiYongSet, tongGuanSet, logicDescriptions
        )
        val dzContribution = dzScore * 0.5f
        score += dzContribution
        logicDescriptions.add("地支评分：${String.format("%+.1f", dzScore)}分 × 50% = ${String.format("%+.1f", dzContribution)}分")

        // === 3. 流年与大运互动评分（权重20%）===
        val interactScore = calcLiuNianDaYunInteraction(
            data, liuNianTg, liuNianDz, tgWx, dzWx, yongSet, jiSet, logicDescriptions
        )
        val interactContribution = interactScore * 0.2f
        score += interactContribution
        logicDescriptions.add("流年大运互动：${String.format("%+.1f", interactScore)}分 × 20% = ${String.format("%+.1f", interactContribution)}分")

        // === 4. 流年与日主关系修正 ===
        score += calcLiuNianDayMasterRelation(
            data, liuNianTg, liuNianDz, tgWx, dzWx, logicDescriptions
        )

        // === 5. 流年刑冲合害修正 ===
        score += calcLiuNianConflictAdjustment(
            data, liuNianDz, dzWx, logicDescriptions
        )

        // === 6. 流年桃花修正 ===
        score += calcLiuNianPeachBlossomAdjustment(
            data, liuNianDz, logicDescriptions
        )

        // 最终评分限定在0-100之间
        val finalScore = score.coerceIn(0f, 100f)
        logicDescriptions.add("━━━ 最终评分 ━━━")
        logicDescriptions.add("流年总评分：${String.format("%.1f", finalScore)}分")

        // 评分等级说明
        val level = when {
            finalScore >= 85f -> "极佳"
            finalScore >= 75f -> "优良"
            finalScore >= 65f -> "良好"
            finalScore >= 55f -> "一般"
            finalScore >= 45f -> "较差"
            else -> "不佳"
        }
        logicDescriptions.add("评分等级：${level}")

        return finalScore
    }

    /**
     * 流年天干评分
     */
    private fun calcLiuNianGanScore(
        data: BaziData,
        gan: TianGan,
        wx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        xiYongSet: Set<ShiShen>,
        tongGuanSet: Set<ShiShen>,
        logicDescriptions: MutableList<String>
    ): Float {
        var score = 0f
        val ganName = convertTianganToChar(gan)
        val wxName = getWuXingText(wx)

        logicDescriptions.add("【流年天干${ganName}（${wxName}）评分】")

        // 用神天干
        if (yongSet.contains(wxToShiShen(wx))) {
            score += 30f
            logicDescriptions.add("  ✓ 天干${ganName}为用神，+30分")
            // 得月令加成
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score += 5f
                logicDescriptions.add("  ✓ 得月令加持，+5分")
            }
        }
        // 喜神天干
        else if (xiYongSet.contains(wxToShiShen(wx))) {
            score += 15f
            logicDescriptions.add("  ✓ 天干${ganName}为喜神，+15分")
        }
        // 忌神天干
        else if (jiSet.contains(wxToShiShen(wx))) {
            score -= 25f
            logicDescriptions.add("  ✗ 天干${ganName}为忌神，-25分")
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score -= 5f
                logicDescriptions.add("  ✗ 忌神得月令，加重-5分")
            }
        }
        // 闲神
        else {
            score -= 3f
            logicDescriptions.add("  ○ 天干${ganName}为闲神，-3分")
        }

        // 通关用神修正
        if (tongGuanSet.contains(wxToShiShen(wx))) {
            score += 8f
            logicDescriptions.add("  ✓ 通关用神，+8分")
        }

        // 天干五合修正
        val tianGanUtil = TianGanUtil()
        if (tianGanUtil.isTianGanHe(gan, data.yearTiangan)) {
            score += 3f
            logicDescriptions.add("  ✓ 与年干相合，+3分")
        }
        if (tianGanUtil.isTianGanHe(gan, data.monthTiangan)) {
            score += 3f
            logicDescriptions.add("  ✓ 与月干相合，+3分")
        }
        if (tianGanUtil.isTianGanHe(gan, data.dayTiangan)) {
            score += 5f
            logicDescriptions.add("  ✓ 与日干相合（天地合），+5分")
        }
        if (tianGanUtil.isTianGanHe(gan, data.hourTiangan)) {
            score += 3f
            logicDescriptions.add("  ✓ 与时干相合，+3分")
        }

        // 天干相克修正
        if (tianGanUtil.isTianGanKe(gan, data.dayTiangan)) {
            score -= 5f
            logicDescriptions.add("  ✗ 克日主，-5分")
        }
        if (tianGanUtil.isTianGanSheng(gan, data.dayTiangan)) {
            score += 5f
            logicDescriptions.add("  ✓ 生日主，+5分")
        }

        return score
    }

    /**
     * 流年地支评分
     */
    private fun calcLiuNianZhiScore(
        data: BaziData,
        zhi: DiZhi,
        wx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        xiYongSet: Set<ShiShen>,
        tongGuanSet: Set<ShiShen>,
        logicDescriptions: MutableList<String>
    ): Float {
        var score = 0f
        val zhiName = convertDizhiToChar(zhi)
        val wxName = getWuXingText(wx)

        logicDescriptions.add("【流年地支${zhiName}（${wxName}）评分】")

        // 用神地支
        if (yongSet.contains(wxToShiShen(wx))) {
            score += 38f
            logicDescriptions.add("  ✓ 地支${zhiName}为用神，+38分")
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score += 7f
                logicDescriptions.add("  ✓ 得月令加持，+7分")
            }
        }
        // 喜神地支
        else if (xiYongSet.contains(wxToShiShen(wx))) {
            score += 18f
            logicDescriptions.add("  ✓ 地支${zhiName}为喜神，+18分")
        }
        // 忌神地支
        else if (jiSet.contains(wxToShiShen(wx))) {
            score -= 32f
            logicDescriptions.add("  ✗ 地支${zhiName}为忌神，-32分")
            val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)
            if (wx == monthWx) {
                score -= 8f
                logicDescriptions.add("  ✗ 忌神得月令，加重-8分")
            }
        }
        // 闲神
        else {
            score -= 5f
            logicDescriptions.add("  ○ 地支${zhiName}为闲神，-5分")
        }

        // 通关用神修正
        if (tongGuanSet.contains(wxToShiShen(wx))) {
            score += 10f
            logicDescriptions.add("  ✓ 通关用神，+10分")
        }

        // 地支藏干分析
        val diZhiUtil = DiZhiUtil()
        val cangGans = diZhiUtil.getCanggan(zhi)
        var cangGanBonus = 0f
        for (cg in cangGans) {
            val cgWx = WuXingWeightCalculator.getTianGanWuxing(cg)
            if (yongSet.contains(wxToShiShen(cgWx))) {
                cangGanBonus += 3f
            }
        }
        if (cangGanBonus > 0) {
            score += cangGanBonus
            logicDescriptions.add("  ✓ 藏干中有用神，+${String.format("%.0f", cangGanBonus)}分")
        }

        return score
    }

    /**
     * 流年与大运互动评分
     */
    private fun calcLiuNianDaYunInteraction(
        data: BaziData,
        liuNianTg: TianGan,
        liuNianDz: DiZhi,
        tgWx: WuXing,
        dzWx: WuXing,
        yongSet: Set<ShiShen>,
        jiSet: Set<ShiShen>,
        logicDescriptions: MutableList<String>
    ): Float {
        var score = 0f

        logicDescriptions.add("【流年与大运互动评分】")

        // 流年干支与大运干支的生克关系
        if (isSheng(tgWx, dzWx)) {
            score += 3f
            logicDescriptions.add("  ✓ 流年天干生地支，力量顺畅，+3分")
        }
        if (isSheng(dzWx, tgWx)) {
            score += 2f
            logicDescriptions.add("  ✓ 流年地支生天干，根基稳固，+2分")
        }
        if (isKe(tgWx, dzWx)) {
            score -= 2f
            logicDescriptions.add("  ✗ 流年天干克地支，外强中干，-2分")
        }
        if (isKe(dzWx, tgWx)) {
            score -= 3f
            logicDescriptions.add("  ✗ 流年地支克天干，根基受损，-3分")
        }

        // 流年与大运用神/忌神一致性
        val tgIsYong = yongSet.contains(wxToShiShen(tgWx))
        val dzIsYong = yongSet.contains(wxToShiShen(dzWx))
        val tgIsJi = jiSet.contains(wxToShiShen(tgWx))
        val dzIsJi = jiSet.contains(wxToShiShen(dzWx))

        when {
            tgIsYong && dzIsYong -> {
                score += 8f
                logicDescriptions.add("  ✓ 流年干支皆为用神，协同增效，+8分")
            }
            tgIsJi && dzIsJi -> {
                score -= 10f
                logicDescriptions.add("  ✗ 流年干支皆为忌神，雪上加霜，-10分")
            }
            tgIsYong && dzIsJi -> {
                score -= 3f
                logicDescriptions.add("  △ 天干用神地支忌神，吉凶参半，-3分")
            }
            tgIsJi && dzIsYong -> {
                score -= 3f
                logicDescriptions.add("  △ 天干忌神地支用神，吉凶参半，-3分")
            }
        }

        return score
    }

    /**
     * 流年与日主关系修正
     */
    private fun calcLiuNianDayMasterRelation(
        data: BaziData,
        liuNianTg: TianGan,
        liuNianDz: DiZhi,
        tgWx: WuXing,
        dzWx: WuXing,
        logicDescriptions: MutableList<String>
    ): Float {
        var adjustment = 0f
        val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(data.dayTiangan)
        val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(data)

        logicDescriptions.add("【流年与日主关系修正】")

        // 日主过强时，喜克泄耗
        if (dayMasterStrength.strengthLevel == StrengthLevel.VERY_STRONG ||
            dayMasterStrength.strengthLevel == StrengthLevel.STRONG
        ) {
            if (isKe(tgWx, dayMasterWx) || isKe(dzWx, dayMasterWx)) {
                adjustment += 5f
                logicDescriptions.add("  ✓ 日主偏强，流年克日主，平衡命局，+5分")
            }
            if (isSheng(dayMasterWx, tgWx) || isSheng(dayMasterWx, dzWx)) {
                adjustment -= 3f
                logicDescriptions.add("  ✗ 日主偏强，流年生日主，加剧失衡，-3分")
            }
        }

        // 日主过弱时，喜生扶
        if (dayMasterStrength.strengthLevel == StrengthLevel.VERY_WEAK ||
            dayMasterStrength.strengthLevel == StrengthLevel.WEAK
        ) {
            if (isSheng(dayMasterWx, tgWx) || isSheng(dayMasterWx, dzWx)) {
                adjustment += 5f
                logicDescriptions.add("  ✓ 日主偏弱，流年生日主，扶助日主，+5分")
            }
            if (isKe(tgWx, dayMasterWx) || isKe(dzWx, dayMasterWx)) {
                adjustment -= 3f
                logicDescriptions.add("  ✗ 日主偏弱，流年克日主，雪上加霜，-3分")
            }
        }

        // 日主中和时
        if (dayMasterStrength.strengthLevel == StrengthLevel.MEDIUM) {
            logicDescriptions.add("  ○ 日主中和，无特殊修正")
        }

        return adjustment
    }

    /**
     * 流年刑冲合害修正
     */
    private fun calcLiuNianConflictAdjustment(
        data: BaziData,
        liuNianDz: DiZhi,
        dzWx: WuXing,
        logicDescriptions: MutableList<String>
    ): Float {
        var adjustment = 0f
        val diZhiUtil = DiZhiUtil()

        logicDescriptions.add("【流年刑冲合害修正】")

        // 流年冲日支
        if (diZhiUtil.isDiZhiChong(liuNianDz, data.dayDizhi)) {
            adjustment -= 8f
            logicDescriptions.add("  ✗ 流年冲日支（夫妻宫），根基动摇，-8分")
        }

        // 流年冲月支
        if (diZhiUtil.isDiZhiChong(liuNianDz, data.monthDizhi)) {
            adjustment -= 5f
            logicDescriptions.add("  ✗ 流年冲月支，环境变动，-5分")
        }

        // 流年冲年支
        if (diZhiUtil.isDiZhiChong(liuNianDz, data.yearDizhi)) {
            adjustment -= 3f
            logicDescriptions.add("  ✗ 流年冲年支，祖业根基受影响，-3分")
        }

        // 流年合日支
        if (diZhiUtil.isDiZhiHai(liuNianDz, data.dayDizhi)) {
            adjustment += 5f
            logicDescriptions.add("  ✓ 流年合日支（夫妻宫），姻缘情感顺利，+5分")
        }

        // 流年合月支
        if (diZhiUtil.isDiZhiHai(liuNianDz, data.monthDizhi)) {
            adjustment += 3f
            logicDescriptions.add("  ✓ 流年合月支，事业环境有利，+3分")
        }

        // 流年刑日支
        if (diZhiUtil.isDiZhiXiangXing(liuNianDz, data.dayDizhi)) {
            adjustment -= 3f
            logicDescriptions.add("  ✗ 流年刑日支，人际关系紧张，-3分")
        }

        // 流年刑月支
        if (diZhiUtil.isDiZhiXiangXing(liuNianDz, data.monthDizhi)) {
            adjustment -= 2f
            logicDescriptions.add("  ✗ 流年刑月支，职场压力增大，-2分")
        }

        return adjustment
    }

    /**
     * 流年桃花修正
     */
    private fun calcLiuNianPeachBlossomAdjustment(
        data: BaziData,
        liuNianDz: DiZhi,
        logicDescriptions: MutableList<String>
    ): Float {
        var adjustment = 0f

        logicDescriptions.add("【流年桃花修正】")

        // 查桃花星
        val peachBlossom = getPeachBlossomBranch(data.dayDizhi)
        if (liuNianDz == peachBlossom) {
            adjustment += 3f
            logicDescriptions.add("  ✓ 流年为桃花星，异性缘增强，+3分")
        }

        return adjustment
    }

    // ===== 辅助函数 =====

    /**
     * 获取桃花星地支
     */
    private fun getPeachBlossomBranch(dayZhi: DiZhi): DiZhi = when (dayZhi) {
        DiZhi.DIZHI_YIN, DiZhi.DIZHI_WU, DiZhi.DIZHI_XU -> DiZhi.DIZHI_MOU
        DiZhi.DIZHI_HAI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI -> DiZhi.DIZHI_ZI
        DiZhi.DIZHI_SHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN -> DiZhi.DIZHI_YOU
        DiZhi.DIZHI_SI, DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU -> DiZhi.DIZHI_WU
        else -> dayZhi
    }

    private fun isSheng(a: WuXing, b: WuXing): Boolean = when (a) {
        WuXing.WUXING_MU -> b == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> b == WuXing.WUXING_TU
        WuXing.WUXING_TU -> b == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> b == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> b == WuXing.WUXING_MU
    }

    private fun isKe(a: WuXing, b: WuXing): Boolean = when (a) {
        WuXing.WUXING_MU -> b == WuXing.WUXING_TU
        WuXing.WUXING_TU -> b == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> b == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> b == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> b == WuXing.WUXING_MU
    }

    private fun getWuXingText(wx: WuXing): String = when (wx) {
        WuXing.WUXING_JIN -> "金"
        WuXing.WUXING_MU -> "木"
        WuXing.WUXING_SHUI -> "水"
        WuXing.WUXING_HUO -> "火"
        WuXing.WUXING_TU -> "土"
    }

    private fun wxToShiShen(wx: WuXing): ShiShen = when (wx) {
        WuXing.WUXING_MU -> ShiShen.SHISHEN_BI_JIAN
        WuXing.WUXING_HUO -> ShiShen.SHISHEN_SHI_SHEN
        WuXing.WUXING_TU -> ShiShen.SHISHEN_ZHENG_CAI
        WuXing.WUXING_JIN -> ShiShen.SHISHEN_ZHENG_GUAN
        WuXing.WUXING_SHUI -> ShiShen.SHISHEN_ZHENG_YIN
    }

    private fun convertTianganToChar(tg: TianGan): String = when (tg) {
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

    private fun convertDizhiToChar(dz: DiZhi): String = when (dz) {
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
}
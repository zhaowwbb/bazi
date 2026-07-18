package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.model.StrengthLevel
import com.rick.bazi.util.BaziFormatter.getDiZhiMainElement
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing

/**
 * 流月评分系统
 * 基于流年评分，根据流月干支对八字 / 大运 / 流年 的影响进行修正
 */
object LiuYueScoringSystem {

    /**
     * @param data           八字原局
     * @param daYunTg        大运天干
     * @param daYunDz        大运地支
     * @param liuNianTg      流年天干
     * @param liuNianDz      流年地支
     * @param liuYueTg       流月天干
     * @param liuYueDz       流月地支
     * @param liuNianScore   流年评分（作为流月修正的基准分）
     * @param logicDescriptions 用于收集评分调整逻辑的字符串列表
     * @return 修正后的流月评分（0~100）
     */
    fun calcLiuYueScore(
        data: BaziData,
        daYunTg: TianGan,
        daYunDz: DiZhi,
        liuNianTg: TianGan,
        liuNianDz: DiZhi,
        liuYueTg: TianGan,
        liuYueDz: DiZhi,
        liuNianScore: Float,
        logicDescriptions: MutableList<String>
    ): Float {
        var score = liuNianScore

        logicDescriptions.add("━━━ 流月评分修正（基于流年评分 ${"%.1f".format(liuNianScore)}）━━━")

        val yongSet = data.yongShenList.toSet()
        val jiSet = data.jiShenList.toSet()
        val xiSet = data.xiyongShenSet

        val dayMasterWx = getTianGanWuxing(data.dayTiangan)
        val dayMasterStrength = calculateDayMasterStrength(data)

        val liuNianTgWx = getTianGanWuxing(liuNianTg)
        val liuNianDzWx = getDiZhiMainElement(liuNianDz)
        val liuYueTgWx = getTianGanWuxing(liuYueTg)
        val liuYueDzWx = getDiZhiMainElement(liuYueDz)

        val tianGanUtil = TianGanUtil()
        val diZhiUtil = DiZhiUtil()

        /* ================= 1. 流月天干修正 ================= */
        logicDescriptions.add("【流月天干 ${tianGanToChar(liuYueTg)}（${getWuXingText(liuYueTgWx)}）】")

        // 用神天干
        if (yongSet.contains(wxToShiShen(liuYueTgWx))) {
            score += 18f
            logicDescriptions.add("  ✓ 流月天干为用神，+18分")
        }
        // 喜神天干
        else if (xiSet.contains(wxToShiShen(liuYueTgWx))) {
            score += 10f
            logicDescriptions.add("  ✓ 流月天干为喜神，+10分")
        }
        // 忌神天干
        else if (jiSet.contains(wxToShiShen(liuYueTgWx))) {
            score -= 15f
            logicDescriptions.add("  ✗ 流月天干为忌神，-15分")
        } else {
            score -= 3f
            logicDescriptions.add("  ○ 流月天干为闲神，-3分")
        }

        // 流月天干克日主
        if (tianGanUtil.isTianGanKe(liuYueTg, data.dayTiangan)) {
            score -= 5f
            logicDescriptions.add("  ✗ 流月天干克日主，-5分")
        }
        // 流月天干生日主
        if (tianGanUtil.isTianGanSheng(liuYueTg, data.dayTiangan)) {
            score += 5f
            logicDescriptions.add("  ✓ 流月天干生日主，+5分")
        }

        /* ================= 2. 流月地支修正 ================= */
        logicDescriptions.add("【流月地支 ${diZhiToChar(liuYueDz)}（${getWuXingText(liuYueDzWx)}）】")

        // 用神地支
        if (yongSet.contains(wxToShiShen(liuYueDzWx))) {
            score += 22f
            logicDescriptions.add("  ✓ 流月地支为用神，+22分")
        }
        // 喜神地支
        else if (xiSet.contains(wxToShiShen(liuYueDzWx))) {
            score += 12f
            logicDescriptions.add("  ✓ 流月地支为喜神，+12分")
        }
        // 忌神地支
        else if (jiSet.contains(wxToShiShen(liuYueDzWx))) {
            score -= 18f
            logicDescriptions.add("  ✗ 流月地支为忌神，-18分")
        } else {
            score -= 5f
            logicDescriptions.add("  ○ 流月地支为闲神，-5分")
        }

        // 流月冲日支（夫妻宫）
        if (diZhiUtil.isDiZhiChong(liuYueDz, data.dayDizhi)) {
            score -= 8f
            logicDescriptions.add("  ✗ 流月冲日支（夫妻宫），根基动摇，-8分")
        }
        // 流月合日支
        if (diZhiUtil.isDiZhiHai(liuYueDz, data.dayDizhi)) {
            score += 5f
            logicDescriptions.add("  ✓ 流月合日支（夫妻宫），情感人际趋稳，+5分")
        }
        // 流月冲月支
        if (diZhiUtil.isDiZhiChong(liuYueDz, data.monthDizhi)) {
            score -= 5f
            logicDescriptions.add("  ✗ 流月冲月支（父母/环境宫），-5分")
        }
        // 流月刑日支
        if (diZhiUtil.isDiZhiXiangXing(liuYueDz, data.dayDizhi)) {
            score -= 3f
            logicDescriptions.add("  ✗ 流月刑日支，人际/健康易有隐压，-3分")
        }

        /* ================= 3. 流月与大运关系 ================= */
        logicDescriptions.add("【流月 ↔ 大运】")

        if (isSheng(getTianGanWuxing(daYunTg), liuYueTgWx) ||
            isSheng(getDiZhiMainElement(daYunDz), liuYueDzWx)
        ) {
            score += 3f
            logicDescriptions.add("  ✓ 大运生流月，气场顺畅，+3分")
        }
        if (isKe(getTianGanWuxing(daYunTg), liuYueTgWx) ||
            isKe(getDiZhiMainElement(daYunDz), liuYueDzWx)
        ) {
            score -= 3f
            logicDescriptions.add("  ✗ 大运克流月，外部阻力增大，-3分")
        }

        // 大运与流月干支同为用神/忌神
        val daYunTgWx = getTianGanWuxing(daYunTg)
        val daYunDzWx = getDiZhiMainElement(daYunDz)

        if (yongSet.contains(wxToShiShen(daYunTgWx)) &&
            yongSet.contains(wxToShiShen(liuYueTgWx))
        ) {
            score += 4f
            logicDescriptions.add("  ✓ 大运天干与流月天干皆为用神，协同增效，+4分")
        }
        if (jiSet.contains(wxToShiShen(daYunTgWx)) &&
            jiSet.contains(wxToShiShen(liuYueTgWx))
        ) {
            score -= 4f
            logicDescriptions.add("  ✗ 大运天干与流月天干皆为忌神，雪上加霜，-4分")
        }

        /* ================= 4. 流月与流年关系 ================= */
        logicDescriptions.add("【流月 ↔ 流年】")

        if (isSheng(liuNianTgWx, liuYueTgWx) || isSheng(liuNianDzWx, liuYueDzWx)) {
            score += 3f
            logicDescriptions.add("  ✓ 流年生流月，年度基调对流月有利，+3分")
        }
        if (isKe(liuNianTgWx, liuYueTgWx) || isKe(liuNianDzWx, liuYueDzWx)) {
            score -= 3f
            logicDescriptions.add("  ✗ 流年克流月，流年吉力被流月削弱，-3分")
        }

        /* ================= 5. 日主强弱修正 ================= */
        logicDescriptions.add("【日主强弱修正】")

        if (dayMasterStrength.strengthLevel == StrengthLevel.STRONG ||
            dayMasterStrength.strengthLevel == StrengthLevel.VERY_STRONG
        ) {
            // 身强：喜克泄耗
            if (isKe(liuYueTgWx, dayMasterWx) || isKe(liuYueDzWx, dayMasterWx)) {
                score += 4f
                logicDescriptions.add("  ✓ 日主偏强，流月克日主，平衡命局，+4分")
            }
            if (isSheng(dayMasterWx, liuYueTgWx) || isSheng(dayMasterWx, liuYueDzWx)) {
                score -= 3f
                logicDescriptions.add("  ✗ 日主偏强，流月生扶日主，加剧失衡，-3分")
            }
        }

        if (dayMasterStrength.strengthLevel == StrengthLevel.WEAK ||
            dayMasterStrength.strengthLevel == StrengthLevel.VERY_WEAK
        ) {
            // 身弱：喜生扶
            if (isSheng(dayMasterWx, liuYueTgWx) || isSheng(dayMasterWx, liuYueDzWx)) {
                score += 4f
                logicDescriptions.add("  ✓ 日主偏弱，流月生扶日主，+4分")
            }
            if (isKe(liuYueTgWx, dayMasterWx) || isKe(liuYueDzWx, dayMasterWx)) {
                score -= 3f
                logicDescriptions.add("  ✗ 日主偏弱，流月克日主，-3分")
            }
        }

        /* ================= 6. 桃花 / 特殊地支微调 ================= */
        val peach = getPeachBlossomBranch(data.dayDizhi)
        if (liuYueDz == peach) {
            score += 2f
            logicDescriptions.add("  ✓ 流月为桃花星，人际/感情活跃，+2分")
        }

        score = score.coerceIn(0f, 100f)

        logicDescriptions.add("━━━ 流月最终评分 ━━━")
        logicDescriptions.add("流月修正后总分：${"%.1f".format(score)}分")

        return score
    }

    /* ================= 辅助函数 ================= */

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

    private fun tianGanToChar(tg: TianGan): String = when (tg) {
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

    private fun diZhiToChar(dz: DiZhi): String = when (dz) {
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

    private fun getPeachBlossomBranch(dayZhi: DiZhi): DiZhi = when (dayZhi) {
        DiZhi.DIZHI_YIN, DiZhi.DIZHI_WU, DiZhi.DIZHI_XU -> DiZhi.DIZHI_MOU
        DiZhi.DIZHI_HAI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI -> DiZhi.DIZHI_ZI
        DiZhi.DIZHI_SHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN -> DiZhi.DIZHI_YOU
        DiZhi.DIZHI_SI, DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU -> DiZhi.DIZHI_WU
        else -> dayZhi
    }
}